package se.iths.plugin;


import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.io.IOhandler;
import se.iths.persistence.Book;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.spi.UrlHandler;

import java.net.Socket;
import java.util.List;


public class BooksHandler implements UrlHandler {

    private String route = "/books";


    @Override
    public String getRoute() {
        return route;
    }

    @Override

    public HttpResponse handlerUrl(HttpRequest httpRequest) {

        HttpResponse httpResponse = new HttpResponse();

        BookDAO bdao = new BookDAOWithJPAImpl();

        List<Book> allBooks = bdao.getAllBooks();

        //objektet konverteras till json och skrivs ut i metoden printJsonResponse
        httpResponse.printJsonResponse(ConvertJson.convertToJson(allBooks));

        return httpResponse;
    }


}
