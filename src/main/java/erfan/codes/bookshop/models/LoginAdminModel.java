package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.general.common.global.UserType;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.validation.Errors;

import java.util.Optional;

public class LoginAdminModel extends BaseInputModel {

    private String userId;

    @Override
    public void validate(Errors errors) {

        UserRepo userRepo = SpringContext.getApplicationContext().getBean(UserRepo.class);
        Optional<UserEntity> byId = userRepo.findById(Long.valueOf(this.userId));
        if (!byId.isPresent()) {
            return_status_code = Return_Status_Codes.INVALID_ADMIN_INFORMATION;
            return;
        }
        UserEntity user = byId.get();
        if (user.getUsertype().equals(UserType.Subscribers.getValue())) {
            this.return_status_code = Return_Status_Codes.SC_FORBIDDEN;
            return;
        }

        return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
