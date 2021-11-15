package erfan.codes.bookshop.controller;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.models.AddBookModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooksPanelServiceImpl implements BooksPanelService {

    private final BookRepo bookRepo;

    @Autowired
    public BooksPanelServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookGlobalV1.addBook.Builder addBook(AddBookModel addBookModel) {
        BookGlobalV1.addBook.Builder ret = BookGlobalV1.addBook.newBuilder();
        if (addBookModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus())
            return addBookModel.getOutput().returnResponseObject(ret, addBookModel.getReturn_status_code());
        //TODO implement hibernate codes here
        BookEntity book = new BookEntity(addBookModel.getBarcode(), addBookModel.getName(), addBookModel.getAuthor(),
                addBookModel.getPrice(), addBookModel.getQuantity());
        try {
            BookEntity entity = this.bookRepo.save(book);
            BookEntity newEntity = this.bookRepo.findByBarcode(entity.getBarcode()).get(0);
            BookGlobalV1.Book.Builder dto = this.bookRepo.createBook(newEntity);
            ret.setBook(dto);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
