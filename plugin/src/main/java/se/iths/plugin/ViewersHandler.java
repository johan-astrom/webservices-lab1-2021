package se.iths.plugin;

import se.iths.io.HttpResponse;
import se.iths.persistence.Statistics;
import se.iths.spi.StatisticsHandler;

public class ViewersHandler implements StatisticsHandler {


    @Override
    public int countClients() {
        return Statistics.count;
    }


}
