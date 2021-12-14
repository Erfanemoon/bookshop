package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.validation.Errors;

import java.util.Optional;
import java.util.Set;

public class SubscriberDeleteBookModel extends BaseInputModel {

    private String subscriberId;
    private String bookId;

    @Override
    public void validate(Errors errors) {

        if (this.subscriberId == null || this.subscriberId.isEmpty()) {

            this.return_status_code = Return_Status_Codes.INVALID_SUBSCRIBER_INFORMATION;
            return;
        }

        if (this.bookId == null || this.bookId.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        UserRepo userRepo = SpringContext.getApplicationContext().getBean(UserRepo.class);
        Optional<UserEntity> optionalUser = userRepo.findById(Long.valueOf(this.subscriberId));
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            Set<BookEntity> books = userEntity.getBooks();
            if (books == null || books.isEmpty()) {
                this.return_status_code = Return_Status_Codes.NO_BOOK_FOUND_FOR_SUBSCRIBER;
                return;
            }
        } else {
            this.return_status_code = Return_Status_Codes.INVALID_SUBSCRIBER_INFORMATION;
            return;
        }

        this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
