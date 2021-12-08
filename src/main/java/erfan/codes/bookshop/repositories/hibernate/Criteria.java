package erfan.codes.bookshop.repositories.hibernate;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class Criteria<T> {

    private final CriteriaBuilder cb;
    private final CriteriaQuery<T> cq;
    private final Root<T> root;
    private final Query<T> query;


    public CriteriaQuery<T> createQuery() {
        return cq.select(root);
    }

    public Criteria(Class<T> returnType, Session session) {

        this.cb = session.getCriteriaBuilder();
        this.cq = cb.createQuery(returnType);
        root = cq.from(returnType);
        cq.select(root);
        this.query = session.createQuery(cq);
    }

    public CriteriaBuilder cBuilder() {
        return cb;
    }

    public CriteriaQuery<T> cQuery() {
        return cq;
    }

    public Root<T> root() {
        return root;
    }

    public Query<T> query() {
        return query;
    }
}
