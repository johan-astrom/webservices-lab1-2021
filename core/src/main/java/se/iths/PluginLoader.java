package se.iths;

import se.iths.spi.UrlHandler;

import java.util.ServiceLoader;

public class PluginLoader {

    public static ServiceLoader<UrlHandler> findUrlHandlers(){

        //ServiceLoader går in i klassen UrlHandler och söker efter
        // alla våra plugins och laddar in alla plugins. Returnerar dem

        return ServiceLoader.load(UrlHandler.class);

    }


}
