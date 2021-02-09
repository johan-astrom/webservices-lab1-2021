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

    public void handlerUrl() {


        BookDAO bdao = new BookDAOWithJPAImpl();

        List<Book> allBooks = bdao.getAllBooks();

        jsonSide(ConvertJson.convertToJson(allBooks));


    }



    private void jsonSide(String json ) {
        // skapa html med json mellen body tag
        String url="/json.html";
        IOhandler.FileWriter(url, json);
        HttpResponse.printResponse(socket, url, false);
    }
}
