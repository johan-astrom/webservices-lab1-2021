package se.iths.plugin;


import se.iths.io.HttpResponse;
import se.iths.io.IOhandler;
import se.iths.persistence.Book;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.spi.UrlHandler;

import java.net.Socket;
import java.util.List;


public class BooksHandler implements UrlHandler {
    public BooksHandler() {
    }

    private Socket socket;

    public BooksHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String getRoute() {
        return "/books";
    }

    @Override

    public HttpResponse handlerUrl() {

        HttpResponse httpResponse = new HttpResponse();

        BookDAO bdao = new BookDAOWithJPAImpl();

        List<Book> allBooks = bdao.getAllBooks();

        httpResponse.printJsonResponse(ConvertJson.convertToJson(allBooks));

        return httpResponse;
    }


}
