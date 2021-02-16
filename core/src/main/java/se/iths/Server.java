package se.iths;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.persistence.*;
import se.iths.spi.PluginType;
import se.iths.spi.UrlHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Server {

    private static HttpResponse httpResponse;
    private static HttpRequest httpRequest;
    private static Service serviceBooks;
    private static BookDAO bookDAO;
    private static Service serviceStats;
    private static StatisticsDAO statisticsDAO;

    private static Map<String, UrlHandler> route;

    public static void main(String[] args) {

        bookDAO = new BookDAOWithJPAImpl();
        serviceBooks = new Service(bookDAO);
        statisticsDAO = new StatisticsDAOWithJPAImpl();
        serviceStats = new Service(statisticsDAO);
        httpResponse = new HttpResponse();

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {

            ServerSocket serverSocket = new ServerSocket(9050);

            createMap();

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> handleConnection(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createMap() {

        route = new HashMap<>();
        var loader = PluginLoader.findUrlHandlers();

        for (var handler : loader) {
            route.put(handler.getClass().getAnnotation(PluginType.class).route(), handler);
        }
    }

    private static void handleConnection(Socket socket) {

        try (BufferedInputStream input = new BufferedInputStream(socket.getInputStream())) {
            String headerLine = readLine(input);

            String[] header = headerLine.split(" ");

            boolean isHead = true;

            String url = header[1];

            switch (header[0]) {

                case "GET":
                    isHead = false;
                    readHeaderLines(input, url);
                    httpResponse = findRoute(url);
                    break;

                case "HEAD":
                    isHead = true;
                    readHeaderLines(input, url);
                    httpResponse = findRoute(url);
                    break;

                case "POST":
                    postRequest(input, url);
                    httpResponse.redirect("/postConfirmation.html");
                    break;
            }

            sendHttp(socket, isHead, httpResponse);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendHttp(Socket socket, boolean isHead, HttpResponse httpResponse) throws IOException {
        PrintWriter output = new PrintWriter(socket.getOutputStream());
        output.print(httpResponse.getHeader());
        output.flush();

        if (!isHead) {
            var dataOutput = new BufferedOutputStream(socket.getOutputStream());
            dataOutput.write(httpResponse.getBody());
            dataOutput.flush();
        }
    }

    private static HttpResponse findRoute(String url) {

        String routeUrl = url;
        if (url.indexOf("?") != -1) {

            routeUrl = url.split("\\?")[0];
        }

        UrlHandler urlHandler = route.get(routeUrl);

        httpRequest = new HttpRequest(url);

        if (urlHandler == null) {
            urlHandler = route.get("fileHandler");
        }

        httpResponse = urlHandler.handlerUrl(httpRequest, httpResponse);

        return httpResponse;
    }

    private static void postRequest(BufferedInputStream input, String url) throws IOException {

        int contentLength = readHeaderLines(input, url);

        String bodyLine = new String(input.readNBytes(contentLength));

        String[] body = bodyLine.split("&");

        long isbn13 = Long.parseLong(body[0].substring(body[0].indexOf("=") + 1));
        String title = body[1].substring(body[1].indexOf("=") + 1);
        String genre = body[2].substring(body[2].indexOf("=") + 1);
        double price = Double.parseDouble(body[3].substring(body[3].indexOf("=") + 1));

        serviceBooks.getBook().create(isbn13, title, genre, price);

    }

    private static int readHeaderLines(BufferedInputStream input, String url) throws IOException {

        String headerLine;
        int contentLength = 0;

        while (true) {
            headerLine = readLine(input);
            System.out.println(headerLine);

            if (headerLine.startsWith("Content-Length")) {
                contentLength = Integer.parseInt(headerLine.split(" ")[1]);
            }

            if (headerLine.startsWith("User-Agent")) {
                writeUserToDB(headerLine, url);
            }

            if (headerLine.isEmpty()) {
                break;
            }
        }

        return contentLength;
    }

    public static String readLine(BufferedInputStream inputStream) throws IOException {
        final int MAX_READ = 4096;
        byte[] buffer = new byte[MAX_READ];
        int bytesRead = 0;
        while (bytesRead < MAX_READ) {
            buffer[bytesRead++] = (byte) inputStream.read();
            if (buffer[bytesRead - 1] == '\r') {
                buffer[bytesRead++] = (byte) inputStream.read();
                if (buffer[bytesRead - 1] == '\n')
                    break;
            }
        }
        return new String(buffer, 0, bytesRead - 2, StandardCharsets.UTF_8);
    }

    private static void writeUserToDB(String headerLine, String url) {

        serviceStats.getStatistics().create(headerLine, url);
    }
}


