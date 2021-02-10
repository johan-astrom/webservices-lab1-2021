package se.iths.spi;

import se.iths.io.HttpResponse;

import java.net.Socket;

public interface UrlHandler {

    HttpResponse handlerUrl();

    String getRoute();



}
