package art.oceanpresent.www.chatjavaee.ejb;

import art.oceanpresent.www.chatjavaee.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Stateless
public class UserEJB extends AbstractEJB<Integer, User> {
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public List<User> findByKeyword(String keyword) {
        return stream().filter(p -> p.getUsername().contains(keyword))
                .collect(toList());
    }

    public long countByKeyword(String keyword) {
        return stream().filter(p -> p.getUsername().contains(keyword))
                .count();
    }

    @Override
    protected EntityManager entityManager() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        return em;
    }
}
