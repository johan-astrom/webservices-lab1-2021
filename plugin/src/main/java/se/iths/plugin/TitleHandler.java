package se.iths.plugin;


import se.iths.io.HttpResponse;
import se.iths.io.IOhandler;

import se.iths.persistence.Book;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.spi.UrlHandler;
import java.util.List;

public class TitleHandler implements UrlHandler {

    private String route = "/title";

    @Override
    public HttpResponse handlerUrl() {
        BookDAO bdao = new BookDAOWithJPAImpl();
        List<Book> books = bdao.getAllBooks();

        String url = "/title";

        String result = "";

        //loopar igenom boklistan, lägger till titlarna efter varandra på /title
        for (Book book : books) {
            result += book.getTitle() + "\r\n";

        }

        //skickar url och titlarna till fileWriter
        IOhandler.FileWriter(url, result);

        HttpResponse httpResponse = new HttpResponse();

        httpResponse.printResponse("/title.txt");
        return httpResponse;
    }

    @Override
    public String getRoute() {
        return route;
    }

}
