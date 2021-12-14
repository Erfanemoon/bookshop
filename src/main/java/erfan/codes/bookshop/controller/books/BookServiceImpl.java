package erfan.codes.bookshop.controller.books;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.models.ListBooksModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookServiceImpl implements IBookService {

    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookGlobalV1.BookList.Builder bookList(ListBooksModel listBooksModel) {


        BookGlobalV1.BookList.Builder ret = BookGlobalV1.BookList.newBuilder();

        if (listBooksModel.getReturn_status_code() != Return_Status_Codes.OK_VALID_FORM) {
            return listBooksModel.getOutput().returnResponseObject(ret, listBooksModel.getReturn_status_code());
        }

        List<BookEntity> bookEntities = this.bookRepo.booksList();
        ret = this.bookRepo.createBookList(bookEntities);
        return listBooksModel.getOutput().returnResponseObject(ret, listBooksModel.getReturn_status_code());
    }
}
