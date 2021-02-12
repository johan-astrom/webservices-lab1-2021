package se.iths.io;

public class HttpRequest {

    String url;

    public HttpRequest() {
    }

    public HttpRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
