package se.iths;

import se.iths.io.HttpResponse;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.persistence.StatisticsDAOWithJPAImpl;
import se.iths.spi.StatisticsHandler;
import se.iths.spi.UrlHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.*;

public class Server {

    //Deklarerat en map för att hantera flera förfrågningar,
    // som vi ska kunna nå senare i koden. Sparas i par key och value från UrlHandler, sparas som routes.
    private static Map<String, UrlHandler> route;


    public static void main(String[] args) {

        //Trådar skapas
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            //Servern startar
            ServerSocket serverSocket = new ServerSocket(7050);

            createMap();

            while (true) {
                //Klienten ansluter till servern, skickar socket till handleConnection();
                Socket socket = serverSocket.accept();
                executorService.execute(() -> handleConnection(socket));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createMap() {
        //Initieras en hash map för våra routes
        route = new HashMap<>();

        //Klassen PluginLoader anropar findUrlHandlers som söker plugins, sparas i
        //en serviceloader
        var loader = PluginLoader.findUrlHandlers();

        //For-each loopen stegar igenom serviceloadern och lägger in nyckel/värde i mappen
        for (var handler : loader) {
            route.put(handler.getRoute(), handler);

            // Utrskift på vår Map både key och vart den går
            //System.out.printf("Url "+ handler.getRoute().toString() +  " class "+ handler+"\r\n");
        }


    }

    private static void handleConnection(Socket socket) {

        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String headerLine = input.readLine();

            String[] header = headerLine.split(" ");

            boolean isHead = true;
            String url = header[1];
            HttpResponse httpResponse = null;



            switch (header[0]) {

                case "GET":
                    isHead = false;
                    System.out.println("Hämta GET klass");
                    readHeaderLines(input, true, url);
                    httpResponse = findRoute(url);
                    break;

                case "HEAD":
                    isHead = true;
                    System.out.println("Hämta HEAD metod");
                    readHeaderLines(input, true, url);
                    httpResponse = findRoute(url);
                    break;

                case "POST":
                    System.out.println("Hämta POST metod");
                    postRequest(input, url);
                    httpResponse = findRoute(url);
                    break;

            }


            sendHttp(socket, isHead, httpResponse);


            System.out.println(header[0]);

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
        //hämtar en url handler från vår Map
        //Här bestäms vilken klass som vi hämtar (bookhandler eller titlehandler)

        UrlHandler urlHandler = route.get(url);
        HttpResponse httpResponse;
        if (urlHandler != null) {
            httpResponse = urlHandler.handlerUrl();
        } else {
            //HttpResponse.printResponse(socket, url, isHead);
            httpResponse = new HttpResponse();
            httpResponse.printResponse(url);
            //skapar en instans som skickar urln till metoden printResponse();
        }
        return httpResponse;
    }

    private static void postRequest(BufferedReader input, String url) throws IOException {
        readHeaderLines(input, false, url);

        String bodyLine = input.readLine();

        String[] body = bodyLine.split("&");

        System.out.println(bodyLine);

        long isbn13 = Long.parseLong(body[0].substring(body[0].indexOf("=") + 1));
        String title = body[1].substring(body[1].indexOf("=") + 1);
        String genre = body[2].substring(body[2].indexOf("=") + 1);
        double price = Double.parseDouble(body[3].substring(body[3].indexOf("=") + 1));

        System.out.println(isbn13 + " " + title + " " + genre + " " + price);

        BookDAO book = new BookDAOWithJPAImpl();

        book.create(isbn13, title, genre, price);
    }

    private static void readHeaderLines(BufferedReader input, boolean isUniqueUser, String url) throws IOException {
        String headerLine;

        while (true) {
            headerLine = input.readLine();
            System.out.println(headerLine);

            if (headerLine.startsWith("User-Agent") && isUniqueUser) {

                writeUserToDB(headerLine, url);


            }

            if (headerLine.isEmpty()) {
                break;
            }
        }


    }

    private static void writeUserToDB(String headerLine, String url) {
        // egen klass
        for (var handler : route.values()) {
            if (handler.getClass().getSimpleName().equals("ViewersHandler")) {

                StatisticsDAOWithJPAImpl statisticsDAOWithJPA = new StatisticsDAOWithJPAImpl();
                statisticsDAOWithJPA.create(headerLine, url);
            }

        }
    }
/*
    private static void writeUserToDB(String headerLine, String url) {
        // egen klass

        ServiceLoader<>
        for (var handler : route.values()) {
            if (handler.getClass().getAnnotation(StatisticType.class).value().equals("/Viewers"));
            {

                StatisticsDAOWithJPAImpl statisticsDAOWithJPA = new StatisticsDAOWithJPAImpl();
                statisticsDAOWithJPA.create(headerLine, url);
            }

        }
    }
    
 */
}