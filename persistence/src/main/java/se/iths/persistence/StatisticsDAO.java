package se.iths.persistence;

import java.util.List;

public interface StatisticsDAO {

    List<Statistics> getAllStatistics();

    void create(String userAgent, String url);

}
