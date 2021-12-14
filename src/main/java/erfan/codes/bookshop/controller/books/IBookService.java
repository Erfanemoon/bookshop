package erfan.codes.bookshop.controller.books;

import erfan.codes.bookshop.models.ListBooksModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;

public interface IBookService {

    BookGlobalV1.BookList.Builder bookList(ListBooksModel listBooksModel);
}
