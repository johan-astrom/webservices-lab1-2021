package se.iths.plugin;


import se.iths.io.HttpResponse;
import se.iths.io.IOhandler;

import se.iths.persistence.Book;
import se.iths.persistence.BookDAO;
import se.iths.persistence.BookDAOWithJPAImpl;
import se.iths.spi.UrlHandler;

import java.net.Socket;
import java.util.List;

public class TitleHandler implements UrlHandler {

    public TitleHandler() {
    }

    private Socket socket;

    public TitleHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void handlerUrl() {
        BookDAO bdao = new BookDAOWithJPAImpl();
        List<Book> books = bdao.getAllBooks();

        String url="/Title.txt";

        String result="";

        for (Book book: books) {
            result += book.getTitle()+"\r\n";

        }

        IOhandler.FileWriter(url, result);


        HttpResponse.printResponse(socket, url, false);
    }

}
