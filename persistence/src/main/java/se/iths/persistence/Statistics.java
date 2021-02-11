package se.iths.persistence;

import javax.persistence.*;

@Entity
@Table(name="[Statistics]")
public class Statistics {

    public Statistics( String userAgent) {
        this.userAgent = userAgent;
    }

    public Statistics() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userAgent;
    public static int count=0;



    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
