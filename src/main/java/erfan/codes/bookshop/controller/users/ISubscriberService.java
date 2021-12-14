package erfan.codes.bookshop.controller.users;

import erfan.codes.bookshop.models.*;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.proto.holder.SubscriberGlobalV1;

public interface ISubscriberService {

    SubscriberGlobalV1.addSubscriber.Builder subscriberRegister(SubscriberRegisterModel subscriberRegisterModel);

    SubscriberGlobalV1.loginSubscriber.Builder subscriberLogin(SubscriberLoginModel subscriberLoginModel);

    SubscriberGlobalV1.SubscriberInfo.Builder subscriberBooklist(SubscriberBooksModel subscriberBooksModel);

    SubscriberGlobalV1.SubscriberAddBook.Builder subscriberAddBook(SubscriberAddBookModel subscriberAddBookModel);

    SubscriberGlobalV1.SubscriberInfo.Builder subscriberDeleteBook(SubscriberDeleteBookModel subscriberDeleteBook);
}
