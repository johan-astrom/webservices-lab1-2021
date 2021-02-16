package se.iths.spi;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;

public interface UrlHandler {

    //Kan behöva en parameter - egen ny klass HTTP-request - för att kunna hantera POST-requests.
    HttpResponse handlerUrl(HttpRequest httpRequest, HttpResponse httpResponse);





}
