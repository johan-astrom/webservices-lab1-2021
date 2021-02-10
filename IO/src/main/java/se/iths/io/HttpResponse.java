package se.iths.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class HttpResponse {

    private String header;
    private byte[] body;

    public String getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }

    public void printResponse(String url) {

        try {
            System.out.println("Url =" + url);

            //Urln sätts till /index.html om den är /
            if (url.equals("/")) {
                url = "/index.html";
            }

            //Skapar objektet file
            File file = new File(".." + File.separator + "web" + url);
            //File file = new File("core" + File.separator + "web" + url);

            //Finns inte filen anropas 404
            if (!file.exists()) {
                this.header = printPageNotFound();
                return;
            }

            //existerar filen läses bodyn in
            //bodyn = bytearrayen som vi läst av filen som vi  angett urln till
            this.body = IOhandler.readFromFile(file);

            //probecontenttype hittar vilken typ av fil och returnerar den
            String type = Files.probeContentType(file.toPath());
            //sätter headern till den typen
            this.header = printHeaderLines(type);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Metod för utskrift json
    public void printJsonResponse(String json){
        this.header = printHeaderLines("application/json");
        this.body=json.getBytes(StandardCharsets.UTF_8);

    }

    //sb bygger ihop en sträng och returnerar den för utskrift
    private String printHeaderLines(String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Length:" + this.body.length + "\r\n");
        sb.append("Content-Type:" + type+ "\r\n");
        sb.append("\r\n");

        return sb.toString();

    }


    private String printPageNotFound() {
       this.body = IOhandler.readFromFile(new File(".." + File.separator + "web" + File.separator + "404.html"));
        //byte[] page = IOhandler.readFromFile(new File("core" + File.separator + "web" + File.separator + "404.html"));
        StringBuilder sb = new StringBuilder();

        sb.append("HTTP/1.1 404 Not Found\r\n");
        sb.append("Content-Length:" + this.body.length + "\r\n");
        sb.append("Content-Type: text/html\r\n");
        sb.append("\r\n");

        return sb.toString();

    }
}
