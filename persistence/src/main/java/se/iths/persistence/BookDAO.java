package se.iths.persistence;

import java.util.List;

public interface BookDAO {

    List<Book> getAllBooks();

    void create(long isbn13, String title, String genre, double price);

}
