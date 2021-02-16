package se.iths.persistence;

public class Service {

    private BookDAO book;
    private StatisticsDAO statistics;

    public Service(BookDAO book) {
        this.book = book;
    }

    public BookDAO getBook() {
        return book;
    }

    public Service(StatisticsDAO statistics) {
        this.statistics = statistics;
    }

    public StatisticsDAO getStatistics() {
        return statistics;
    }
}
