import se.iths.plugin.BooksHandler;

import se.iths.plugin.TitleHandler;

module plugin {
    exports se.iths.plugin;
    requires se.iths.spi;
    requires se.iths.persistence;
    requires se.iths.io;
    requires com.google.gson;
    provides se.iths.spi.UrlHandler with BooksHandler, TitleHandler;
}