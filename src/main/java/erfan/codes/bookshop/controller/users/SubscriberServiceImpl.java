package erfan.codes.bookshop.controller.users;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SessionType;
import erfan.codes.bookshop.general.common.global.SessionUtil;
import erfan.codes.bookshop.general.common.global.UserType;
import erfan.codes.bookshop.models.*;
import erfan.codes.bookshop.proto.holder.Global;
import erfan.codes.bookshop.proto.holder.SubscriberGlobalV1;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SubscriberServiceImpl implements ISubscriberService {

    UserRepo userRepo;
    BookRepo bookRepo;

    @Autowired
    public SubscriberServiceImpl(UserRepo userRepo, BookRepo bookRepo) {
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public SubscriberGlobalV1.addSubscriber.Builder subscriberRegister(SubscriberRegisterModel subscriberRegisterModel) {

        SubscriberGlobalV1.addSubscriber.Builder ret = SubscriberGlobalV1.addSubscriber.newBuilder();
        if (subscriberRegisterModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {
            return subscriberRegisterModel.getOutput().returnResponseObject(ret, subscriberRegisterModel.getReturn_status_code());
        }

        //TODO create user method like admin in userRepo

        UserEntity userEntity = new UserEntity(subscriberRegisterModel.getUserName(), subscriberRegisterModel.getPassword(),
                subscriberRegisterModel.getFirstName(), subscriberRegisterModel.getLastName(), subscriberRegisterModel.getAddress()
                , subscriberRegisterModel.getPhone(), subscriberRegisterModel.getMailId(), UserType.Subscribers.getValue());

        userRepo.save(userEntity);
        UserEntity userEntity1 = userRepo.findbyUsername(userEntity.getUsername()).get(0);
        SessionUtil.saveUserInfo(userEntity.getId(), SessionType.Subscribers.getValue());
        SubscriberGlobalV1.Subscriber.Builder dto = userRepo.createUserDTO(userEntity1);

        ret.setSubscriber(dto);
        return subscriberRegisterModel.getOutput().returnResponseObject(ret, subscriberRegisterModel.getReturn_status_code());
    }

    @Override
    public SubscriberGlobalV1.loginSubscriber.Builder subscriberLogin(SubscriberLoginModel subscriberLoginModel) {

        SubscriberGlobalV1.loginSubscriber.Builder ret = SubscriberGlobalV1.loginSubscriber.newBuilder();
        if (subscriberLoginModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {
            return subscriberLoginModel.getOutput().returnResponseObject(ret, subscriberLoginModel.getReturn_status_code());
        }

        UserEntity entity = userRepo.findbyUsername(subscriberLoginModel.getUsername()).get(0);
        SubscriberGlobalV1.Subscriber.Builder userDTO = userRepo.createUserDTO(entity);
        ret.setSubscriber(userDTO);

        Global.SessionModel sessionModel = SessionUtil.getAndValidateSession(subscriberLoginModel.getSessionId());

        if (sessionModel == null)
            return subscriberLoginModel.getOutput().returnResponseObject(ret, Return_Status_Codes.SC_FORBIDDEN);

        long validUntil = sessionModel.getValidUntil();
        Date now = new Date();
        long l = now.getTime() / 1000;
        if (l > validUntil)
            SessionUtil.updateSessionExpireDate(sessionModel);

        return subscriberLoginModel.getOutput().returnResponseObject(ret, subscriberLoginModel.getReturn_status_code());
    }

    @Override
    public SubscriberGlobalV1.SubscriberInfo.Builder subscriberBooklist(SubscriberBooksModel subscriberBooksModel) {

        SubscriberGlobalV1.SubscriberInfo.Builder ret = SubscriberGlobalV1.SubscriberInfo.newBuilder();
        if (subscriberBooksModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {
            return subscriberBooksModel.getOutput().returnResponseObject(ret, subscriberBooksModel.getReturn_status_code());
        }

        SubscriberGlobalV1.Subscriber.Builder subscriber = this.userRepo.getUserBooks((long) subscriberBooksModel.getSubscriberId());
        ret.setSubscriber(subscriber);
//        optionalUser.ifPresent(userEntity -> {
//            for (int i = 0; i < userEntity.getSubscriberBooks().size(); i++) {
//
//                BookEntity book = userEntity.getSubscriberBooks().get(i);
//                ret.addUserBooks(bookRepo.createBook(book));
//            }
//
//            ret.setUserId(userEntity.getId());
//        });

        return subscriberBooksModel.getOutput().returnResponseObject(ret, subscriberBooksModel.getReturn_status_code());
    }

    @Override
    public SubscriberGlobalV1.SubscriberAddBook.Builder subscriberAddBook(SubscriberAddBookModel subscriberAddBookModel) {

        SubscriberGlobalV1.SubscriberAddBook.Builder ret = SubscriberGlobalV1.SubscriberAddBook.newBuilder();
        if (subscriberAddBookModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {
            return subscriberAddBookModel.getOutput().returnResponseObject(ret, subscriberAddBookModel.getReturn_status_code());
        }

        Optional<UserEntity> user = this.userRepo.findById(Long.valueOf(subscriberAddBookModel.getUserId()));
        UserEntity userEntity = user.get();

        List<BookEntity> books = new ArrayList<>();
        for (String bookId : subscriberAddBookModel.getBookIds()) {
            Optional<BookEntity> book = this.bookRepo.findById(Long.valueOf(bookId));
            BookEntity bookEntity = book.get();
            books.add(bookEntity);
        }

        this.userRepo.addUserBooks(userEntity, books);

        SubscriberGlobalV1.Subscriber.Builder userBooksDTO = this.userRepo.createUserBooksDTO(userEntity, userEntity.getBooks());
        ret.setSubscriber(userBooksDTO);
        return subscriberAddBookModel.getOutput().returnResponseObject(ret, subscriberAddBookModel.getReturn_status_code());
    }

    @Override
    public SubscriberGlobalV1.SubscriberInfo.Builder subscriberDeleteBook(SubscriberDeleteBookModel subscriberDeleteBookModel) {

        SubscriberGlobalV1.SubscriberInfo.Builder ret = SubscriberGlobalV1.SubscriberInfo.newBuilder();
        if (subscriberDeleteBookModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {
            return subscriberDeleteBookModel.getOutput().returnResponseObject(ret, subscriberDeleteBookModel.getReturn_status_code());
        }

        SubscriberGlobalV1.Subscriber.Builder subscriber = this.userRepo.
                deleteSubscriberBook(Long.parseLong(subscriberDeleteBookModel.getSubscriberId()),
                        Long.parseLong(subscriberDeleteBookModel.getBookId()));

        ret.setSubscriber(subscriber);
        return subscriberDeleteBookModel.getOutput().returnResponseObject(ret, subscriberDeleteBookModel.getReturn_status_code());
    }
}
