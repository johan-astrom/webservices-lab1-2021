package se.iths.IO;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;

public class HttpResponse {

    public static void printResponse(Socket socket, String url) {
        try {
            System.out.println("Url =" + url);

            if (url.equals("/")) {
                url = "/index.html";
            }

            File file = new File("core" + File.separator + "web" + url);
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            if (!file.exists()) {
                printPageNotFound(output);
                return;
            }

            byte[] page = IOhandler.readFromFile(file);

            printHeaderLines(output, file, page);

            printBody(socket, page);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printHeaderLines(PrintWriter output, File file, byte[] page) throws IOException {
        String type = Files.probeContentType(file.toPath());

        output.println("HTTP/1.1 200 OK");
        output.println("Content-Length:" + page.length);
        output.println("Content-Type:" + type);
        output.println("");

        output.flush();
    }

    private static void printBody(Socket socket, byte[] page) throws IOException {
        var dataOut = new BufferedOutputStream(socket.getOutputStream());
        dataOut.write(page);
        dataOut.flush();
    }

    private static void printPageNotFound(PrintWriter output) {
        byte[] page = IOhandler.readFromFile(new File("core" + File.separator + "web" + File.separator + "404.html"));
        output.println("HTTP/1.1 404 Not Found");
        output.println("Content-Length:" + page.length);
        output.println("Content-Type: text/html");
        output.println("");
        output.println(new String(page));

        output.flush();
    }
}
