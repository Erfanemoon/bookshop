package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.General;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.repositories.BookRepo;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.validation.Errors;

import java.util.Optional;
import java.util.Set;

public class SubscriberBooksModel extends BaseInputModel {

    private int subscriberId;
    //TODO later add pagination

    @Override
    public void validate(Errors errors) {

        UserRepo userRepo = SpringContext.getApplicationContext().getBean(UserRepo.class);

        if (General.isEmpty(String.valueOf(this.subscriberId))) {
            this.return_status_code = Return_Status_Codes.UNDEFINED;
            return;
        }

        Optional<UserEntity> optionalUser = userRepo.findById((long) this.subscriberId);
        if (!optionalUser.isPresent()) {
            this.return_status_code = Return_Status_Codes.INVALID_SUBSCRIBER_INFORMATION;
            return;
        }

        this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }
}
