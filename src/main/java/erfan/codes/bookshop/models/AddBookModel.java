package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class AddBookModel extends BaseInputModel {

    private String barcode;
    private String name;
    private String author;
    private int price;
    private int quantity;

    @Override
    public void validate(Errors errors) {

        BookRepo repo = SpringContext.getApplicationContext().getBean(BookRepo.class);
        List<BookEntity> books = repo.findByBarcode(this.barcode);

        if (books.size() > 1) {

            this.return_status_code = Return_Status_Codes.BARCODE_CHECK_ERROR;

        } else if (books.size() == 1) {

            this.return_status_code = Return_Status_Codes.OK_VALID_FORM;

        } else {
            this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
        }

    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
