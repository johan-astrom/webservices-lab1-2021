package se.iths.persistence;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "Books")
public class Book {

    @Id
    private long isbn13;
    private String title;
    private String genre;
    private double price;

    public Book(){}

    public Book(long isbn13, String title, String genre, double price) {
        this.isbn13 = isbn13;
        this.title = title;
        this.genre = genre;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn13=" + isbn13 +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
