package erfan.codes.bookshop.controller.users;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SessionUtil;
import erfan.codes.bookshop.models.SubscriberBooksModel;
import erfan.codes.bookshop.models.SubscriberLoginModel;
import erfan.codes.bookshop.models.SubscriberRegisterModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.proto.holder.Global;
import erfan.codes.bookshop.proto.holder.SubscriberGlobalV1;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

        UserEntity userEntity = new UserEntity(subscriberRegisterModel.getUserName(), subscriberRegisterModel.getPassword(),
                subscriberRegisterModel.getFirstName(), subscriberRegisterModel.getLastName(), subscriberRegisterModel.getAddress()
                , subscriberRegisterModel.getPhone(), subscriberRegisterModel.getMailId());

        userRepo.save(userEntity);
        UserEntity userEntity1 = userRepo.findbyUsername(userEntity.getUsername()).get(0);
        SessionUtil.saveSubscriberInfo(userEntity.getId());
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
        return ret;
    }

    @Override
    public BookGlobalV1.userBooks.Builder subscriberBooklist(SubscriberBooksModel subscriberBooksModel) {

        BookGlobalV1.userBooks.Builder ret = BookGlobalV1.userBooks.newBuilder();
        if (subscriberBooksModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {
            return subscriberBooksModel.getOutput().returnResponseObject(ret, subscriberBooksModel.getReturn_status_code());
        }

        Optional<UserEntity> optionalUser = this.userRepo.findById((long) subscriberBooksModel.getSubscriberId());
        optionalUser.ifPresent(userEntity -> {
            for (int i = 0; i < userEntity.getSubscriberBooks().size(); i++) {

                BookEntity book = userEntity.getSubscriberBooks().get(i);
                ret.addUserBooks(bookRepo.createBook(book));
            }

            ret.setUserId(userEntity.getId());
        });

        return ret;
    }


}
