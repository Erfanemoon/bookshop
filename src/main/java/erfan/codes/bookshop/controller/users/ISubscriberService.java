package erfan.codes.bookshop.controller.users;

import erfan.codes.bookshop.models.SubscriberBooksModel;
import erfan.codes.bookshop.models.SubscriberLoginModel;
import erfan.codes.bookshop.models.SubscriberRegisterModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.proto.holder.SubscriberGlobalV1;

public interface ISubscriberService {

    SubscriberGlobalV1.addSubscriber.Builder subscriberRegister(SubscriberRegisterModel subscriberRegisterModel);

    SubscriberGlobalV1.loginSubscriber.Builder subscriberLogin(SubscriberLoginModel subscriberLoginModel);

    BookGlobalV1.userBooks.Builder subscriberBooklist(SubscriberBooksModel subscriberBooksModel);
}
