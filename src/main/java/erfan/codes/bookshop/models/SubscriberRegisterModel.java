package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.General;
import erfan.codes.bookshop.general.common.global.SpringContext;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.validation.Errors;

import java.util.List;

public class SubscriberRegisterModel extends BaseInputModel {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String address;
    private String phone;
    private String mailId;

    @Override
    public void validate(Errors errors) {

        if (General.isEmpty(firstName) || General.isEmpty(lastName) || General.isEmpty(userName) || General.isEmpty(password) ||
                General.isEmpty(address) || General.isEmpty(phone) || General.isEmpty(mailId)) {
            this.return_status_code = Return_Status_Codes.INVALID_SUBSCRIBER_INFORMATION;
            return;
        }
        UserRepo userRepo = SpringContext.getApplicationContext().getBean(UserRepo.class);
        List<UserEntity> userEntities = userRepo.findbyUsername(this.userName);
        if (userEntities.size() > 0) {
            this.return_status_code = Return_Status_Codes.INVALID_SUBSCRIBER_INFORMATION;
            return;
        }
        this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }
}
