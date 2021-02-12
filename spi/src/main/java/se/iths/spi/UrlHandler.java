package se.iths.spi;

import se.iths.io.HttpResponse;

import java.net.Socket;

public interface UrlHandler {

    //Kan behöva en parameter - egen ny klass HTTP-request - för att kunna hantera POST-requests.
    HttpResponse handlerUrl();

    String getRoute();



}
