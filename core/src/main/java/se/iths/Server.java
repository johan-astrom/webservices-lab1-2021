package se.iths;

import se.iths.io.HttpResponse;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.plugin.BooksHandler;
import se.iths.plugin.TitleHandler;
import se.iths.spi.UrlHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Server {


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();


        try {
            ServerSocket serverSocket = new ServerSocket(7050);

            while (true) {

                Socket socket = serverSocket.accept();

                executorService.execute(() -> handleConnection(socket));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void handleConnection(Socket socket) {

        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String headerLine = input.readLine();

            String[] header = headerLine.split(" ");

            boolean isHead = true;

            switch (header[0]) {

                case "GET":
                    isHead = false;
                    System.out.println("Hämta GET klass");
                    break;

                case "HEAD":
                    isHead = true;
                    System.out.println("Hämta HEAD metod");
                    break;

                case "POST":
                    System.out.println("Hämta POST metod");
                    postRequest(input);

                    break;

                default:
                    System.out.println("404");
            }


            Map<String, UrlHandler> route = new HashMap<>();

            route.put("/title", new TitleHandler(socket));
            route.put("/books", new BooksHandler(socket));

            String url = header[1];
            UrlHandler urlHandler = route.get(url);
            if (urlHandler != null) {
                urlHandler.handlerUrl();
            } else {
                HttpResponse.printResponse(socket, url, isHead);
            }

            System.out.println(header[0]);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void postRequest(BufferedReader input) throws IOException {
        String headerLine;
        while (true) {
            headerLine = input.readLine();

            if (headerLine.isEmpty()) {
                break;
            }
        }

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


}
