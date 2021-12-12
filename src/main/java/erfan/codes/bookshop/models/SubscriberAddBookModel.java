package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

public class SubscriberAddBookModel extends BaseInputModel {

    private String userId;
    private List<String> bookIds;

    @Override
    public void validate(Errors errors) {

        UserRepo userRepo = SpringContext.getApplicationContext().getBean(UserRepo.class);
        BookRepo bookRepo = SpringContext.getApplicationContext().getBean(BookRepo.class);

        if (this.userId == null || this.userId.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_SUBSCRIBER_INFORMATION;
            return;
        }
        if (this.bookIds == null || this.bookIds.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        Optional<UserEntity> user = userRepo.findById(Long.valueOf(this.userId));
        if (!user.isPresent()) {
            this.return_status_code = Return_Status_Codes.INVALID_SUBSCRIBER_INFORMATION;
            return;
        }

        for (String bookId : this.bookIds) {
            Optional<BookEntity> book = bookRepo.findById(Long.valueOf(bookId));
            if (!book.isPresent()) {
                this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
                return;
            }
        }


        this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }
}
