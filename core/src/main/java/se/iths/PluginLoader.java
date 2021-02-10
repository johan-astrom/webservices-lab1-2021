package se.iths;

import se.iths.spi.UrlHandler;

import java.util.ServiceLoader;

public class PluginLoader {

    public static ServiceLoader<UrlHandler> findUrlHandlers(){

        //laddar in alla plugins
        ServiceLoader<UrlHandler> loader = ServiceLoader.load(UrlHandler.class);

        return loader;

    }

}
