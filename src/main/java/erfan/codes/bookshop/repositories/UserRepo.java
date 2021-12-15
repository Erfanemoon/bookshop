package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.general.common.global.UserType;
import erfan.codes.bookshop.models.RegisterAdminModel;
import erfan.codes.bookshop.proto.holder.AdminGlobalV1;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.proto.holder.SubscriberGlobalV1;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import erfan.codes.bookshop.repositories.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepo extends ObjectRepo<UserEntity> {

    @Autowired
    BookRepo bookRepo;
    @Autowired
    IUserRepo iUserRepo;

    public UserRepo(EntityManager em) {
        super(em);
    }

    public <S extends UserEntity> S save(S entity) {
        return iUserRepo.save(entity);
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

    public SubscriberGlobalV1.Subscriber.Builder createUserBooksDTO(UserEntity userEntity, Set<BookEntity> books) {

        SubscriberGlobalV1.Subscriber.Builder subscriber = SubscriberGlobalV1.Subscriber.newBuilder();

        subscriber = createUserDTO(userEntity);

        for (BookEntity book : books) {
            SubscriberGlobalV1.Book.Builder bookDTO = SubscriberGlobalV1.Book.newBuilder();

            bookDTO.setName(book.getName());
            bookDTO.setId(book.getId());
            bookDTO.setPrice(book.getPrice());
            bookDTO.setQuantity(book.getQuantity());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setBarcode(book.getBarcode());
            subscriber.addBooks(bookDTO);
        }

        return subscriber;
    }

    public Optional<UserEntity> findById(Long id) {

        return this.iUserRepo.findById(id);
    }

    @Transactional
    public void addUserBooks(UserEntity userEntity, List<BookEntity> books) {
        books.stream().map(book -> {

            userEntity.addBook(book);
            return userEntity;
        }).collect(Collectors.toSet());
    }

    @Transactional
    public SubscriberGlobalV1.Subscriber.Builder getUserBooks(Long subscriberId) {

        SubscriberGlobalV1.Subscriber.Builder subscriberDTO;
        Optional<UserEntity> optionalUser = this.iUserRepo.findById(subscriberId);
        UserEntity userEntity = optionalUser.get();
        Set<BookEntity> books = userEntity.getBooks();
        subscriberDTO = this.createUserBooksDTO(userEntity, books);
        return subscriberDTO;
    }

    public SubscriberGlobalV1.Subscriber.Builder deleteSubscriberBook(long userId, long bookId) {

        SubscriberGlobalV1.Subscriber.Builder subscriberDTO;
        Optional<UserEntity> optionalUser = this.findById(userId);
        UserEntity userEntity = optionalUser.get();
        Optional<BookEntity> optionalBook = this.bookRepo.findById(bookId);
        BookEntity bookEntity = optionalBook.get();
        userEntity.getBooks().remove(bookEntity);

        this.save(userEntity);

        subscriberDTO = this.createUserBooksDTO(userEntity, userEntity.getBooks());
        return subscriberDTO;
    }

    public UserEntity createUserEntityTypeAdmin(RegisterAdminModel registerAdminModel) {

        UserEntity admin = new UserEntity();
        admin.setAddress(registerAdminModel.getAddress());
        admin.setFirstname(registerAdminModel.getFirstName());
        admin.setLastname(registerAdminModel.getLastName());
        admin.setUsername(registerAdminModel.getUserName());
        admin.setPassword(registerAdminModel.getPassword());
        admin.setPhone(registerAdminModel.getPhone());
        admin.setMailid(registerAdminModel.getEmail());
        admin.setUsertype(UserType.ADMIN.getValue());

        return admin;
    }

    public AdminGlobalV1.Admin.Builder createAdminDTO(UserEntity user) {

        AdminGlobalV1.Admin.Builder adminDTO = AdminGlobalV1.Admin.newBuilder();
        adminDTO.setAddress(user.getAddress());
        adminDTO.setEmail(user.getMailid());
        adminDTO.setFirstname(user.getFirstname());
        adminDTO.setLastname(user.getLastname());
        adminDTO.setPassword(user.getPassword());
        adminDTO.setPhone(user.getPhone());
        adminDTO.setId(user.getId());

        return adminDTO;
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
