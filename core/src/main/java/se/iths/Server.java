package se.iths;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.persistence.StatisticsDAOWithJPAImpl;
import se.iths.spi.StatisticType;
import se.iths.spi.StatisticsHandler;
import se.iths.spi.UrlHandler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.*;
import java.util.logging.FileHandler;

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
            // TODO: 2021-02-12 Ändra .getRoute() till metod som söker efter värdet hos annotation, ange annotations för alla UrlHandler-klasser
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


            //Kod för URL-parametrar
            try {
                URL parameter = new URL("http://localhost:7050" + url);
                System.out.println(parameter.getQuery() + "--------------------------->");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            switch (header[0]) {

                case "GET":
                    isHead = false;
                    readHeaderLines(input, false, url);
                    httpResponse = findRoute(url);
                    break;

                case "HEAD":
                    isHead = true;
                    readHeaderLines(input, false, url);
                    httpResponse = findRoute(url);
                    break;

                case "POST":
                    postRequest(input, url);
                    httpResponse = findRoute(url);
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
        //Gör en ny URL-handler-klass som hanterar filer, anropas ifall urlHandler = null (den ligger m.a.o. inte i map:en route.

        UrlHandler urlHandler = route.get(url);
        HttpResponse httpResponse;
        HttpRequest httpRequest = new HttpRequest(url);

        // TODO: 2021-02-12 Klassen FileHandler ska ligga i detta paket för att kunna nås ----->// (testade ett annat alternativ som kanske kan vara något? se rad 152).


        if(urlHandler == null) {

            urlHandler = route.get("fileHandler");


        }


            if (urlHandler==null){
          /*  try {

                //Fungerar ej..
              //  urlHandler = new FileHandler();

            } catch (IOException e) {
                e.printStackTrace();
            }*/
            //Ersätt med ny klass i detta package som implementerar URLHandler, anropa dess handlerUrl-metod.
            //urlHandler = new VåranNyaKlassSomImplementerarURLHandler
        }

        httpResponse = urlHandler.handlerUrl(httpRequest);


        return httpResponse;

    }

    // TODO: 2021-02-12 Ändra från BufferedReader till BufferedInputStream(socket.getInputStream) + flush()
    private static void postRequest(BufferedReader input, String url) throws IOException {
        //Plocka ut content-length/-type
        readHeaderLines(input, true, url);

        String bodyLine = input.readLine();

        String[] body = bodyLine.split("&");

        //System.out.println(bodyLine);

        long isbn13 = Long.parseLong(body[0].substring(body[0].indexOf("=") + 1));
        String title = body[1].substring(body[1].indexOf("=") + 1);
        String genre = body[2].substring(body[2].indexOf("=") + 1);
        double price = Double.parseDouble(body[3].substring(body[3].indexOf("=") + 1));

        //System.out.println(isbn13 + " " + title + " " + genre + " " + price);
        // TODO: 2021-02-12 Lägg i egen klass.
        BookDAO book = new BookDAOWithJPAImpl();

        book.create(isbn13, title, genre, price);
    }

    // TODO: 2021-02-12 Spara varje rad i en StringBuilder, låt metoden returnera hela. Anropa ev. writeUserToDB från annan metod.
    private static void readHeaderLines(BufferedReader input, boolean isPost, String url) throws IOException {
        //Plocka ut content-length/-type

        String headerLine;

        while (true) {
            headerLine = input.readLine();
            System.out.println(headerLine);

            if (headerLine.startsWith("User-Agent") && !isPost) {

                writeUserToDB(headerLine, url);


            }

            if (headerLine.isEmpty()) {
                break;
            }
        }


    }

    private static void writeUserToDB(String headerLine, String url) {
        // TODO: 2021-02-12 Lägg i egen klass.
                StatisticsDAOWithJPAImpl statisticsDAOWithJPA = new StatisticsDAOWithJPAImpl();
                statisticsDAOWithJPA.create(headerLine, url);
            }

        }


  /*  private static void writeUserToDB(String headerLine, String url) {
        // egen klass

        for (var handler : route.values()) {
            if (handler.getClass().getAnnotation(StatisticType.class).type().equals("/Viewers"));
            {

                StatisticsDAOWithJPAImpl statisticsDAOWithJPA = new StatisticsDAOWithJPAImpl();
                statisticsDAOWithJPA.create(headerLine, url);
            }

        }
    }
   */

