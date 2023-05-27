package art.oceanpresent.www.chatjavaee.jpa;

import art.oceanpresent.www.chatjavaee.entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Stateless
public class CommentJPA extends AbstractJPA<Integer, Comment> {
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public List<Comment> findByUser(Integer userId) {
        return stream().filter(p -> p.getUserId() == userId)
                .collect(toList());
    }

    public List<Comment> findByKeyword(String keyword) {
        return stream().filter(p -> p.getContent().contains(keyword))
                .collect(toList());
    }

    public List<Comment> findByChat(Integer chatId) {
        return stream().filter(p -> p.getChat().getId() == chatId)
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

