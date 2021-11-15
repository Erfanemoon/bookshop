package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.repositories.entities.UserEntity;
import erfan.codes.bookshop.repositories.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepo extends ObjectRepo<UserEntity> {
    public UserRepo(EntityManager em) {
        super(em);
        this.criteria = new Criteria<>(UserEntity.class, this.getSession());
    }
}
