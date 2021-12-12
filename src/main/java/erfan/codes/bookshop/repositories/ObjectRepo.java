package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.repositories.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;

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
