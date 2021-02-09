package se.iths;

import se.iths.IO.HttpResponse;
import se.iths.IO.IOhandler;
import se.iths.jpa.Book;
import se.iths.jpa.BookDAO;
import se.iths.jpa.BookDAOWithJPAImpl;

import java.net.Socket;
import java.util.List;

public class TitleHandler implements UrlHandler {
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


        HttpResponse.printResponse(socket, url);
    }

}
