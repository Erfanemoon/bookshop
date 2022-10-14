package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.repositories.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ObjectRepo<T> {

    protected EntityManager entityManager;
    protected Criteria<T> criteria;

    @Autowired
    public ObjectRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Session getSession() {
        return this.entityManager.unwrap(Session.class);
    }
}
