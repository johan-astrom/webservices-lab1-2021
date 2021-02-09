package se.iths.jpa;

public class main {

    public static void main(String[] args) {

        BookDAO bdao = new BookDAOWithJPAImpl();

        System.out.println(bdao.getAllBooks());

       // bdao.create(1234567890123L, "På spåret", "Novel", 159.90);

        long isbn = 1234567890124L;
        String title = "Babblarna";
        String genre = "Kidsbooks";
        double price = 99.00;

        bdao.create(isbn, title, genre, price);

        System.out.println(bdao.getAllBooks());




    }





}
