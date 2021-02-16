package se.iths.plugin;


import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.io.IOhandler;

import se.iths.persistence.Book;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.spi.PluginType;
import se.iths.spi.UrlHandler;
import java.util.List;

@PluginType(route = "/title")
public class TitleHandler implements UrlHandler {

    @Override
    public HttpResponse handlerUrl(HttpRequest httpRequest, HttpResponse httpResponse) {
        BookDAO bdao = new BookDAOWithJPAImpl();
        List<Book> books = bdao.getAllBooks();

        String url = "/title";

        String result = "";

        for (Book book : books) {
            result += book.getTitle() + "\r\n";
        }

        IOhandler.FileWriter(url, result);

        httpResponse.printResponse("/title");

        return httpResponse;
    }


}
