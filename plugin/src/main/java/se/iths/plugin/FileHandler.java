package se.iths.plugin;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.spi.UrlHandler;

public class FileHandler implements UrlHandler {

    private String route = "fileHandler";



    @Override
    public HttpResponse handlerUrl(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.printResponse(httpRequest.getUrl());
        return httpResponse;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
