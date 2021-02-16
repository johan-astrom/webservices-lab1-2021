package se.iths.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class BookDAOWithJPAImpl implements BookDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo");

    @Override
    public List<Book> getAllBooks() {

        EntityManager em = emf.createEntityManager();
        List<Book> books;
        em.getTransaction().begin();

        books = em.createQuery("from Book b", Book.class).getResultList();
        em.getTransaction().commit();

        return books;
    }


    @Override
    public void create(long isbn13, String title, String genre, double price) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Book book = new Book(isbn13, title, genre, price);
        System.out.println("Book created");
        em.persist(book);
        em.getTransaction().commit();

    }


}
