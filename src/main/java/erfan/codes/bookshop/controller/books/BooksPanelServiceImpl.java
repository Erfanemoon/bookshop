package erfan.codes.bookshop.controller.books;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.models.AddBookModel;
import erfan.codes.bookshop.models.DeleteBookModel;
import erfan.codes.bookshop.models.ListBooksModel;
import erfan.codes.bookshop.models.UpdateBookModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class BooksPanelServiceImpl implements IBooksPanelService {

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
        return addBookModel.getOutput().returnResponseObject(ret, addBookModel.getReturn_status_code());
    }

    @Override
    public BookGlobalV1.BookList.Builder listBooks(ListBooksModel listBooksModel) {

        BookGlobalV1.BookList.Builder ret = BookGlobalV1.BookList.newBuilder();
        if (listBooksModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {

            return listBooksModel.getOutput().returnResponseObject(ret, listBooksModel.getReturn_status_code());
        }

        List<BookEntity> bookEntities = this.bookRepo.booksList();
        ret = this.bookRepo.createBookList(bookEntities);

        return listBooksModel.getOutput().returnResponseObject(ret, listBooksModel.getReturn_status_code());
    }

    @Override
    public BookGlobalV1.GetBook.Builder updateBook(UpdateBookModel updateBookModel) {

        BookGlobalV1.GetBook.Builder ret = BookGlobalV1.GetBook.newBuilder();
        if (updateBookModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {
            return updateBookModel.getOutput().returnResponseObject(ret, updateBookModel.getReturn_status_code());
        }

        BookEntity bookEntity = this.bookRepo.updateBookInfo(updateBookModel);
        BookGlobalV1.Book.Builder book = this.bookRepo.createBook(bookEntity);
        ret.setBook(book);

        return updateBookModel.getOutput().returnResponseObject(ret, updateBookModel.getReturn_status_code());
    }

    @Override
    public BookGlobalV1.GetBook.Builder deleteBook(DeleteBookModel deleteBookModel) {

        BookGlobalV1.GetBook.Builder ret = BookGlobalV1.GetBook.newBuilder();
        if (deleteBookModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {
            return deleteBookModel.getOutput().returnResponseObject(ret, deleteBookModel.getReturn_status_code());
        }

        Optional<BookEntity> optionalBook = this.bookRepo.findById(Long.valueOf(deleteBookModel.getBookId()));
        BookEntity bookEntity = optionalBook.get();
        Set<UserEntity> bookOwners = bookEntity.getBookOwners();
        if (bookOwners.size() > 0) {
            return deleteBookModel.getOutput().returnResponseObject(ret, Return_Status_Codes.SUBSCRIBER_BOOK_FORBIDDEN_DELETE);
        }

        this.bookRepo.deleteById(Long.valueOf(deleteBookModel.getBookId()));
        BookGlobalV1.Book.Builder book = this.bookRepo.createBook(bookEntity);
        ret.setBook(book);

        return deleteBookModel.getOutput().returnResponseObject(ret, Return_Status_Codes.SC_OK_RESPONSE);
    }
}
