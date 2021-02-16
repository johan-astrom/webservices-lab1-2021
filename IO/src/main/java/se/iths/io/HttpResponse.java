package se.iths.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class HttpResponse {

    private String header;
    private byte[] body;

    private void setHeader(String header) {
        this.header = header;
    }

    private void setBody(byte[] body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }

    public void printResponse(String url) {

        try {
            File file = createFile(url);

            if (!file.exists()) {
                setHeader(printPageNotFound());
                return;
            }

            setBody(IOhandler.readFromFile(file));

            String type = Files.probeContentType(file.toPath());

            setHeader(printHeaderLines(type));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createFile(String url) {
        if (url.equals("/")) {
            url = "/index.html";
        }
        return new File(".." + File.separator + "web" + url);
    }

    public void printJsonResponse(String json) {
        setBody(json.getBytes(StandardCharsets.UTF_8));
        setHeader(printHeaderLines("application/json"));
    }

    private String printHeaderLines(String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Length:" + this.body.length + "\r\n");
        sb.append("Content-Type:" + type + "\r\n");
        sb.append("\r\n");

        return sb.toString();
    }


    private String printPageNotFound() {
        setBody(IOhandler.readFromFile(new File(".." + File.separator + "web" + File.separator + "404.html")));
        StringBuilder sb = new StringBuilder();

        sb.append("HTTP/1.1 404 Not Found\r\n");
        sb.append("Content-Length:" + this.body.length + "\r\n");
        sb.append("Content-Type: text/html\r\n");
        sb.append("\r\n");

        return sb.toString();
    }

    public void redirect(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 303 See Other\r\n");
        sb.append("Location: "+ url);
        sb.append("\r\n");

        this.header = sb.toString();
    }
}
