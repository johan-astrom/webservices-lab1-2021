package se.iths.plugin;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.spi.UrlHandler;

public class FileHandler implements UrlHandler {

    public FileHandler() {
    }

    @Override
    public HttpResponse handlerUrl(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.printResponse(httpRequest.getUrl());
        return null;
    }

    @Override
    public String getRoute() {
        return null;
    }
}
