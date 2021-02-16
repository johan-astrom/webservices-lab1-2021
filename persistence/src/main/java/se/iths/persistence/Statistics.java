package se.iths.persistence;

import javax.persistence.*;

@Entity
@Table(name="[Statistics]")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userAgent;
    private String url;

    public String getUrl() {
        return url;
    }

    public Statistics(String userAgent, String url) {

        this.userAgent = userAgent;
        this.url = url;
    }

    public Statistics() {
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
