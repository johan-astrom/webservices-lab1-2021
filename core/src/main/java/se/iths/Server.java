package se.iths;

import se.iths.IO.HttpResponse;
import se.iths.IO.IOhandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static java.nio.file.Files.probeContentType;

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

            switch (header[0]) {

                case "GET":
                    System.out.println("Hämta GET klass");
                    break;

                case "HEAD":
                    System.out.println("Hämta HEAD metod");
                    break;

                case "POST":
                    System.out.println("Hämta POST metod");
                    break;

                default:
                    System.out.println("404");
            }


            Map<String, UrlHandler> route = new HashMap<>();

            route.put("/author.html", new AuthorHandler());
            route.put("/title.html", new TitleHandler());

            String url = header[1];
            UrlHandler urlHandler = route.get(url);
            if (urlHandler != null) {
                System.out.println(urlHandler.handlerUrl());
            }
            else if (url.equals("/")){
                url = "/index.html";
                HttpResponse.printHeader(socket, url); 
            }
            else {
                HttpResponse.printHeader(socket, url);
            }

            System.out.println(header[0]);


          /*  while (true) {
                headerLine = input.readLine();

                if (headerLine.isEmpty()) {
                    break;
                }
*/

           // }


            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
