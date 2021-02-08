package se.iths.IO;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;

public class HttpResponse {

    public static void printHeader(Socket socket, String url) {
        PrintWriter output = null;
        try {
            output = new PrintWriter(socket.getOutputStream());

            System.out.println(url);

            File file = new File("core" + File.separator + "web" + url);

           if (!file.exists()) {
               byte[] page = IOhandler.readFromFile(new File("core" + File.separator + "web" + File.separator + "404.html"));
                output.println("HTTP/1.1 404 Not Found");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type: text/html");
                output.println("");
                output.println(new String(page));

                output.flush();

                return;
            }

            byte[] page = IOhandler.readFromFile(file);

            String type = Files.probeContentType(file.toPath());

            output.println("HTTP/1.1 200 OK");
            output.println("Content-Length:" + page.length);
            output.println("Content-Type:" + type);
            output.println("");

            //output.print(page);
            output.flush();

            var dataOut = new BufferedOutputStream(socket.getOutputStream());
            dataOut.write(page);
            dataOut.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
