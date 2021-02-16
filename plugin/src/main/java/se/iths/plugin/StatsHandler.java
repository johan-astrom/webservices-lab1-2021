package se.iths.plugin;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.persistence.*;
import se.iths.spi.PluginType;
import se.iths.spi.UrlHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@PluginType(route = "/stats")
public class StatsHandler implements UrlHandler {


    @Override
    public HttpResponse handlerUrl(HttpRequest httpRequest, HttpResponse httpResponse) {

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

        if(httpRequest.getUrl().contains("?")) {
            parameterReader(httpRequest, cat, index, books, stats, httpResponse);

        }else {

            httpResponse.printJsonResponse(ConvertJson.convertToJson(statistics) +
                    "\r\nTotal number of page views = " + statistics.size() +
                    "\r\ncat viewed: " + cat + " times." +
                    "\r\nindex viewed: " + index + " times." +
                    "\r\nbooks viewed: " + books + " times." +
                    "\r\nstats viewed: " + stats + " times.");
        }
        return httpResponse;
    }

    private void parameterReader(HttpRequest httpRequest, int cat, int index, int books, int stats, HttpResponse httpResponse) {
        String type;
        try {
            URL parameter = new URL("http://localhost:7050" + httpRequest.getUrl());

            type = parameter.getQuery().split("=")[1];
            System.out.println(type);

            switch (type){
                case "cat":
                    httpResponse.printJsonResponse("cat viewed: " + cat + " times.");
                    break;
                case "index":
                    httpResponse.printJsonResponse("index viewed: " + index + " times.");
                    break;
                case "books":
                    httpResponse.printJsonResponse("books viewed: " + books + " times.");
                    break;
                case "stats":
                    httpResponse.printJsonResponse("stats viewed: " + stats + " times.");
                    break;

            }
            System.out.println(parameter.getQuery() + "--------------------------->");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
