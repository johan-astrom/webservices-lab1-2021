package se.iths.plugin;


import se.iths.io.HttpRequest;
import se.iths.io.HttpResponse;
import se.iths.persistence.Book;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.spi.PluginType;
import se.iths.spi.UrlHandler;

import java.util.List;

@PluginType(route = "/books")
public class BooksHandler implements UrlHandler {


    @Override

    public HttpResponse handlerUrl(HttpRequest httpRequest, HttpResponse httpResponse) {


        BookDAO bdao = new BookDAOWithJPAImpl();

        List<Book> allBooks = bdao.getAllBooks();

        //objektet konverteras till json och skrivs ut i metoden printJsonResponse
        httpResponse.printJsonResponse(ConvertJson.convertToJson(allBooks));

        return httpResponse;
    }


}
