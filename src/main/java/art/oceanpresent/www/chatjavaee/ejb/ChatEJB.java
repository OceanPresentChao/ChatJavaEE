package art.oceanpresent.www.chatjavaee.ejb;

import art.oceanpresent.www.chatjavaee.entity.Chat;
import art.oceanpresent.www.chatjavaee.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Stateless
public class ChatEJB extends AbstractEJB<Integer, Chat> {
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public List<Chat> findByTitle(String keyword) {
        return stream().filter(p -> p.getTitle().contains(keyword))
                .collect(toList());
    }

    public List<Chat> findByUser(Integer userId) {
        return stream().filter(p -> p.getUser().getId() == userId)
                .collect(toList());
    }

    @Override
    protected EntityManager entityManager() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        return em;
    }
}