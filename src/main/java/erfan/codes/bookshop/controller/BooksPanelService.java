package erfan.codes.bookshop.controller;

import erfan.codes.bookshop.models.AddBookModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;

public interface BooksPanelService {

    BookGlobalV1.addBook.Builder addBook(AddBookModel addBookModel);
}
