package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.proto.holder.SubscriberGlobalV1;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import erfan.codes.bookshop.repositories.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepo extends ObjectRepo<UserEntity> {
    public UserRepo(EntityManager em) {
        super(em);
    }

    @Override
    @Transactional
    public <S extends UserEntity> S save(S entity) {
        return super.save(entity);
    }

    @Transactional
    public List<UserEntity> findbyUsername(String username) {
        this.criteria = new Criteria<>(UserEntity.class, this.getSession());
        this.criteria.cQuery().select(criteria.root()).
                where(criteria.cBuilder().equal(criteria.root().get(Fields.USERNAME.getValue()), username));
        return criteria.query().getResultList();
    }

    public SubscriberGlobalV1.Subscriber.Builder createUserDTO(UserEntity userEntity1) {

        SubscriberGlobalV1.Subscriber.Builder dto = SubscriberGlobalV1.Subscriber.newBuilder();
        dto.setId(userEntity1.getId());
        dto.setAddress(userEntity1.getAddress());
        dto.setFirstname(userEntity1.getFirstname());
        dto.setLastname(userEntity1.getLastname());
        dto.setEmail(userEntity1.getMailid());
        dto.setPassword(userEntity1.getPassword());
        dto.setUsername(userEntity1.getUsername());

        return dto;
    }

    public enum Fields {

        ID("id"),
        FIRSTNAME("firstname"),
        LASTNAME("lastname"),
        USERNAME("username"),
        PASSWORD("password"),
        ADDRESS("address"),
        PHONE("phone"),
        EMAIL("mailid"),
        USERTYPE("usertype");
        private String value;

        Fields(String value) {
            this.value = value;
        }

        public static UserRepo.Fields get(String value) {
            if (value == null)
                return null;
            UserRepo.Fields[] arr$ = values();
            for (UserRepo.Fields val : arr$) {
                if (val.value.equals(value.trim())) {
                    return val;
                }
            }

            return null;
        }

        public String getValue() {
            return value;
        }
    }
}
