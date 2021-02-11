package se.iths;

import se.iths.persistence.Statistics;
import se.iths.spi.StatisticsHandler;
import se.iths.spi.UrlHandler;

import java.util.ServiceLoader;

public class PluginLoader {

    public static ServiceLoader<UrlHandler> findUrlHandlers(){

        //ServiceLoader går in i klassen UrlHandler och söker efter
        // alla våra plugins och laddar in alla plugins. Returnerar dem
        ServiceLoader<UrlHandler> loader = ServiceLoader.load(UrlHandler.class);

        return loader;

    }
    public static ServiceLoader<StatisticsHandler> findStatisticsHandler(){

        //ServiceLoader går in i klassen UrlHandler och söker efter
        // alla våra plugins och laddar in alla plugins. Returnerar dem
        ServiceLoader<StatisticsHandler> loader = ServiceLoader.load(StatisticsHandler.class);

        return loader;

    }

}