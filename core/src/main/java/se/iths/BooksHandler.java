package se.iths;

import se.iths.jpa.Book;
import se.iths.jpa.BookDAO;
import se.iths.jpa.BookDAOWithJPAImpl;
import com.google.gson.Gson;
public class BooksHandler implements UrlHandler {
    @Override
    public void handlerUrl() {


        BookDAO bdao = new BookDAOWithJPAImpl();

        Gson gson = new Gson();
        String json = gson.toJson(bdao.getAllBooks());
        System.out.println(json);


    }
}
