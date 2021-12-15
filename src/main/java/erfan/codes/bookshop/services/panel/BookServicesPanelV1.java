package erfan.codes.bookshop.services.panel;

import erfan.codes.bookshop.controller.books.BooksPanelServiceImpl;
import erfan.codes.bookshop.controller.books.IBooksPanelService;
import erfan.codes.bookshop.general.common.global.RM;
import erfan.codes.bookshop.models.AddBookModel;
import erfan.codes.bookshop.models.ListBooksModel;
import erfan.codes.bookshop.models.UpdateBookModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/apipanel/v1/books/")
public class BookServicesPanelV1 {

    private final IBooksPanelService booksPanelService;

    @Autowired
    public BookServicesPanelV1(BooksPanelServiceImpl booksPanelService) {
        this.booksPanelService = booksPanelService;
    }

    @RM(
            isSessionValidationRequired = false,
            title = "apipanel_v1_books_add",
            protocolBufferReturn = BookGlobalV1.addBook.class,
            value = "/add/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void addBook(AddBookModel addBookModel) {
        BookGlobalV1.addBook.Builder builder = booksPanelService.addBook(addBookModel);
        addBookModel.getOutput().write(builder);
    }

    @RM(
            isSessionValidationRequired = true,
            title = "apipanel_v1_books_list",
            protocolBufferReturn = BookGlobalV1.BookList.class,
            value = "/list/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public void listBooks(ListBooksModel listBooksModel) {
        BookGlobalV1.BookList.Builder builder = this.booksPanelService.listBooks(listBooksModel);
        listBooksModel.getOutput().write(builder);
    }

    @RM(
            isSessionValidationRequired = true,
            title = "apipanel_v1_book_update",
            protocolBufferReturn = BookGlobalV1.GetBook.class,
            value = "/update/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )

    public void updateBook(UpdateBookModel updateBookModel) {
        BookGlobalV1.GetBook.Builder builder = this.booksPanelService.updateBook(updateBookModel);
        updateBookModel.getOutput().write(builder);
    }
    //TODO remove book
}
