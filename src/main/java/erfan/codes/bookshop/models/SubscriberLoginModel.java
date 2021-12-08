package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.General;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.validation.Errors;

import java.util.List;

public class SubscriberLoginModel extends BaseInputModel {

    private String username;
    private String password;

    @Override
    public void validate(Errors errors) {

        if (General.isEmpty(this.username) || General.isEmpty(this.password)) {
            this.return_status_code = Return_Status_Codes.LOGIN_CREDENTIAL_NOT_VALID;
            return;
        }

        UserRepo userRepo = SpringContext.getApplicationContext().getBean(UserRepo.class);
        List<UserEntity> userEntities = userRepo.findbyUsername(this.username);
        if (userEntities.size() == 0) {
            this.return_status_code = Return_Status_Codes.LOGIN_CREDENTIAL_NOT_VALID;
            return;
        }

        UserEntity user = userEntities.get(0);
        if (!user.getPassword().equals(this.password)) {
            this.return_status_code = Return_Status_Codes.LOGIN_CREDENTIAL_NOT_VALID;
            return;
        }

        this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
