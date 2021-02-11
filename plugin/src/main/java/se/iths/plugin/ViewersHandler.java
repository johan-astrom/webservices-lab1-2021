package se.iths.plugin;

import se.iths.io.HttpResponse;
import se.iths.io.IOhandler;
import se.iths.persistence.*;
import se.iths.spi.StatisticsHandler;

import java.util.List;

@StatisticType(type = "/Viewers")
public class ViewersHandler implements StatisticsHandler {



    @Override
    public int countStats() {
        return new StatisticsDAOWithJPAImpl().getAllStatistics().size();
    }


    @Override
    public HttpResponse handlerUrl() {

        StatisticsDAO sdao = new StatisticsDAOWithJPAImpl();

        List<Statistics> statistics = sdao.getAllStatistics();
        System.out.println(statistics);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.printJsonResponse(ConvertJson.convertToJson(statistics));

        return httpResponse;
    }

    @Override
    public String getRoute() {
        return "/stats";
    }
}
