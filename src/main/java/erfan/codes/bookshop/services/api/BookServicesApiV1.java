package erfan.codes.bookshop.services.api;

import erfan.codes.bookshop.controller.books.IBookService;
import erfan.codes.bookshop.general.common.global.RM;
import erfan.codes.bookshop.models.BookGetModel;
import erfan.codes.bookshop.models.ListBooksModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/v1/books/")
public class BookServicesApiV1 {

    IBookService iBookService;

    @Autowired
    public BookServicesApiV1(IBookService iBookService) {
        this.iBookService = iBookService;
    }

    @RM(
            isSessionValidationRequired = false,
            title = "api_v1_books_list",
            protocolBufferReturn = BookGlobalV1.BookList.class,
            value = "/list/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void listBooks(ListBooksModel listBooksModel) {
        BookGlobalV1.BookList.Builder builder = this.iBookService.bookList(listBooksModel);
        listBooksModel.getOutput().write(builder);
    }

    @RM(
            isSessionValidationRequired = false,
            title = "api_v1_book_get",
            protocolBufferReturn = BookGlobalV1.GetBook.class,
            value = "/get/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void getBook(BookGetModel bookGetModel) {

        BookGlobalV1.GetBook.Builder builder = this.iBookService.getBook(bookGetModel);
    }

}
