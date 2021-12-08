package erfan.codes.bookshop.controller.books;

import erfan.codes.bookshop.models.AddBookModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;

public interface IBooksPanelService {

    BookGlobalV1.addBook.Builder addBook(AddBookModel addBookModel);
}
