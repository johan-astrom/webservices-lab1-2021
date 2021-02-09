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

    public long getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(long isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
