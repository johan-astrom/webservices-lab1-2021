package se.iths.plugin;

import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.persistence.*;
import se.iths.spi.PluginType;
import se.iths.spi.StatisticsHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Byt ut fältet mot annotation.
@PluginType(route = "/stats")
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
        String type = null;



        if(httpRequest.getUrl().indexOf("?")!=-1) {
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
                    default:
                        type = null;

                }
                System.out.println(parameter.getQuery() + "--------------------------->");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

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

    @Override
    public String getRoute() {
        return route;
    }
}
