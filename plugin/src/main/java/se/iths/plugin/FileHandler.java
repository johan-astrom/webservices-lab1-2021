package se.iths.plugin;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.spi.PluginType;
import se.iths.spi.UrlHandler;

@PluginType(route = "fileHandler")
public class FileHandler implements UrlHandler {

    @Override
    public HttpResponse handlerUrl(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.printResponse(httpRequest.getUrl());
        return httpResponse;
    }


}
