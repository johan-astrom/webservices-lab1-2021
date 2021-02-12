package se.iths.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class StatisticsDAOWithJPAImpl implements StatisticsDAO{
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo");


    @Override
    public List<Statistics> getAllStatistics() {

        EntityManager em = emf.createEntityManager();
        List<Statistics> statistics;

        statistics = em.createQuery("from Statistics s", Statistics.class).getResultList();

        return statistics;
    }

    @Override
    public void create(String userAgent, String url) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Statistics statistics = new Statistics(userAgent, url);
        em.persist(statistics);
        em.getTransaction().commit();

    }
}
