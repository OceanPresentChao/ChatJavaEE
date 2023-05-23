package art.oceanpresent.www.chatjavaee.ejb;



import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import art.oceanpresent.www.chatjavaee.entity.AbstractEntity;


public abstract class AbstractEJB<ID, T extends AbstractEntity<ID>> {

    protected abstract EntityManager entityManager();

    private Class<T> entityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[1];
    }

    public List<T> findAll() {
        EntityManager em =this.entityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> q = cb.createQuery(entityClass());
        Root<T> c = q.from(entityClass());

        return em.createQuery(q).getResultList();
    }

    public T create(T entity) {
        EntityManager em =this.entityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(entity);
        transaction.commit();
        em.close();
        return entity;
    }

    public T update(T entity) {
        if(entity.getId() == null || this.findById(entity.getId()) == null) {
            return null;
        }
        EntityManager em =this.entityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(entity);
        transaction.commit();
        em.close();
        return entity;
    }

    public T findById(ID id) {
        return entityManager().find(entityClass(), id);
    }

    public void delete(T entity) {
        EntityManager em =this.entityManager();
        EntityTransaction transaction = em.getTransaction();
        T el = this.findById(entity.getId());
        entityManager().remove(el);
        transaction.commit();
        em.close();
    }

    public Stream<T> stream() {
        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(entityClass());
        Root<T> c = q.from(entityClass());

        return entityManager().createQuery(q).getResultStream();
    }

    public Optional<T> findOptionalById(ID id) {
        return Optional.ofNullable(findById(id));
    }

}