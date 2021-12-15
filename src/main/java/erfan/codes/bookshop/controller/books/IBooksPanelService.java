package erfan.codes.bookshop.controller.books;

import erfan.codes.bookshop.models.AddBookModel;
import erfan.codes.bookshop.models.ListBooksModel;
import erfan.codes.bookshop.models.UpdateBookModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;

public interface IBooksPanelService {

    BookGlobalV1.addBook.Builder addBook(AddBookModel addBookModel);

    BookGlobalV1.BookList.Builder listBooks(ListBooksModel listBooksModel);

    BookGlobalV1.GetBook.Builder updateBook(UpdateBookModel updateBookModel);
}
