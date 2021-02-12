package se.iths.plugin;

import se.iths.io.HttpResponse;
import se.iths.persistence.*;
import se.iths.spi.StatisticType;
import se.iths.spi.StatisticsHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@StatisticType(type = "/Viewers")
public class ViewersHandler implements StatisticsHandler {

    private String route = "/stats";

    @Override
    public int countStats() {
        return new StatisticsDAOWithJPAImpl().getAllStatistics().size();
    }


    @Override
    public HttpResponse handlerUrl() {

        StatisticsDAO sdao = new StatisticsDAOWithJPAImpl();

        List<Statistics> statistics = sdao.getAllStatistics();
        System.out.println(statistics);

        int countC = 0;
        for (Statistics stat : statistics) {
            if (stat.getUrl().equals("/cat.jpg")){
                countC++;
            }
        }

  /*      List<String> statsCount = new ArrayList<String>();
        for (Statistics statis: statistics){
            statsCount.add(statis.getUrl());
        }
        int cat = Collections.frequency(statsCount, "/cat.jpg");
        int index = Collections.frequency(statsCount, "/index.html");
        int books = Collections.frequency(statsCount, "/books");
        int stats = Collections.frequency(statsCount, "/stats");
*/



        HttpResponse httpResponse = new HttpResponse();
        httpResponse.printJsonResponse(ConvertJson.convertToJson(statistics) +
                "\r\nTotal number of page views = " + statistics.size() +
                "\r\nCat viewed: " + countC + " times.");

        return httpResponse;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
