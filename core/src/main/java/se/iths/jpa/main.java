package se.iths.jpa;

public class main {

    public static void main(String[] args) {

        BookDAO bdao = new BookDAOWithJPAImpl();

        System.out.println(bdao.getAllBooks());






    }





}
