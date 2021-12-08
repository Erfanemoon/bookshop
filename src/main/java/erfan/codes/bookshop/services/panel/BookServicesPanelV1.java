package erfan.codes.bookshop.services.panel;

import erfan.codes.bookshop.controller.books.BooksPanelServiceImpl;
import erfan.codes.bookshop.controller.books.IBooksPanelService;
import erfan.codes.bookshop.general.common.global.RM;
import erfan.codes.bookshop.models.AddBookModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/apipanel/v1/books")
public class BookServicesPanelV1 {

    private final IBooksPanelService booksPanelService;

    @Autowired
    public BookServicesPanelV1(BooksPanelServiceImpl booksPanelService) {
        this.booksPanelService = booksPanelService;
    }

    //TODO add book
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
    //TODO list books
    //TODO update book
    //TODO remove book
    //TODO Admin login
    //TODO Admin logout
    //TODO Admin search , users , one user
}
