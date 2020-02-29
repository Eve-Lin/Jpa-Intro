import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaDemoMain {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("school");
        EntityManager em = entityManagerFactory.createEntityManager();
        Student student = new Student("Jane Doe");
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Student st = em.find(Student.class, 1);
        System.out.printf("The student is %s", st);
        em.getTransaction().commit();

        em.getTransaction().begin();
                em.createQuery("SELECT st FROM Student st", Student.class)
                        .setFirstResult(0)
                        .getResultList().stream().forEach(System.out::println);
        em.getTransaction().commit();

        em.getTransaction().begin();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery();
        Root<Student> r = criteria.from(Student.class);
        criteria.select(r).where(builder.like(r.get("name"),"I%"));

                em.createQuery(criteria).setMaxResults(3).getResultList().stream().forEach(System.out::println);


        em.getTransaction().commit();
    }
}

