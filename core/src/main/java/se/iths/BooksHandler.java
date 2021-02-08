package se.iths;

import se.iths.jpa.Book;
import se.iths.jpa.BookDAO;
import se.iths.jpa.BookDAOWithJPAImpl;
import com.google.gson.Gson;

import java.util.List;

public class BooksHandler implements UrlHandler {
    @Override
    public void handlerUrl() {


        BookDAO bdao = new BookDAOWithJPAImpl();
        List<Book> allBooks = bdao.getAllBooks();

        jsonSide(ConvertJson.convertTojson(allBooks));


    }



    private void jsonSide(String json) {
        // skapa html med json mellen body tag
        System.out.println(json);
    }


}
