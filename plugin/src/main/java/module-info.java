import se.iths.plugin.BooksHandler;

import se.iths.plugin.FileHandler;
import se.iths.plugin.TitleHandler;
import se.iths.plugin.StatsHandler;

module plugin {
    exports se.iths.plugin;
    requires se.iths.spi;
    requires se.iths.persistence;
    requires se.iths.io;
    requires com.google.gson;
    provides se.iths.spi.UrlHandler with BooksHandler, TitleHandler, StatsHandler, FileHandler;
}