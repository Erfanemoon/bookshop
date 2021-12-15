package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

public class UpdateBookModel extends BaseInputModel {

    private String id;
    private String barcode;
    private String author;
    private String name;
    private Integer price;
    private Integer quantity;

    @Override
    public void validate(Errors errors) {

        if (this.id == null || this.id.isEmpty()) {
            return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        if (this.barcode == null || this.barcode.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        BookRepo bookRepo = SpringContext.getApplicationContext().getBean(BookRepo.class);
        Optional<BookEntity> optionalBook = bookRepo.findById(Long.valueOf(this.id));
        if (!optionalBook.isPresent()) {
            this.return_status_code = Return_Status_Codes.NO_BOOK_FOUND;
            return;
        }

        if (this.author == null || this.author.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        if (this.name == null || this.name.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        if (this.price == null || this.price == 0) {
            this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        if (this.quantity == null || this.quantity == 0) {
            this.return_status_code = Return_Status_Codes.INVALID_BOOK_INFORMATION;
            return;
        }

        this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
