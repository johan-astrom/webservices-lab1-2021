package se.iths.plugin;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.persistence.*;
import se.iths.spi.StatisticType;
import se.iths.spi.StatisticsHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Byt ut fältet mot annotation.
@StatisticType(route = "/stats")
public class ViewersHandler implements StatisticsHandler {

    private String route = "/stats";

    @Override
    public int countStats() {
        return new StatisticsDAOWithJPAImpl().getAllStatistics().size();
    }


    @Override
    public HttpResponse handlerUrl(HttpRequest httpRequest) {

        StatisticsDAO sdao = new StatisticsDAOWithJPAImpl();

        List<Statistics> statistics = sdao.getAllStatistics();
        System.out.println(statistics);

        List<String> statsCount = new ArrayList<String>();
        for (Statistics statis : statistics) {
            statsCount.add(statis.getUrl());
        }
        int cat = Collections.frequency(statsCount, "/cat.jpg");
        int index = Collections.frequency(statsCount, "/index.html");
        int books = Collections.frequency(statsCount, "/books");
        int stats = Collections.frequency(statsCount, "/stats");


        HttpResponse httpResponse = new HttpResponse();
        httpResponse.printJsonResponse(ConvertJson.convertToJson(statistics) +
                "\r\nTotal number of page views = " + statistics.size() +
                "\r\ncat viewed: " + cat + " times." +
                "\r\nindex viewed: " + index + " times." +
                "\r\nbooks viewed: " + books + " times." +
                "\r\nstats viewed: " + stats + " times.");

        return httpResponse;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
