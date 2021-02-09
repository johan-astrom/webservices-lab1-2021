package se.iths;

import se.iths.IO.HttpResponse;
import se.iths.IO.IOhandler;
import se.iths.jpa.Book;
import se.iths.jpa.BookDAO;
import se.iths.jpa.BookDAOWithJPAImpl;

import java.io.FileWriter;
import java.net.Socket;
import java.util.List;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

public class BooksHandler implements UrlHandler {
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
        HttpResponse.printResponse(socket, url);
    }
}
