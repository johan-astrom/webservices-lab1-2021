package se.iths.plugin;

import se.iths.io.HttpResponse;
import se.iths.spi.StatisticsHandler;

public class ViewersHandler implements StatisticsHandler {


    @Override
    public int countClients() {
        return 0;
    }

    @Override
    public HttpResponse handlerUrl() {


        return null;
    }

    @Override
    public String getRoute() {
        return "/viewers";
    }
}
