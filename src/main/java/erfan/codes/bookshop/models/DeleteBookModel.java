package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import org.springframework.validation.Errors;

import java.util.Optional;

public class DeleteBookModel extends BaseInputModel {

    private String bookId;

    @Override
    public void validate(Errors errors) {

        if (this.bookId == null || this.bookId.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        BookRepo bookRepo = SpringContext.getApplicationContext().getBean(BookRepo.class);
        Optional<BookEntity> optionalBook = bookRepo.findById(Long.valueOf(this.bookId));
        if (!optionalBook.isPresent()) {
            this.return_status_code = Return_Status_Codes.NO_BOOK_FOUND;
            return;
        }

        this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
