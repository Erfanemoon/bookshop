package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import org.springframework.validation.Errors;

public class RegisterAdminModel extends BaseInputModel {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String address;
    private String phone;
    private String email;


    @Override
    public void validate(Errors errors) {

        if (this.firstName == null || this.firstName.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_ADMIN_INFORMATION;
            return;
        }

        if (this.lastName == null || this.lastName.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_ADMIN_INFORMATION;
            return;
        }

        if (this.userName == null || this.userName.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_ADMIN_INFORMATION;
            return;
        }

        if (this.password == null || this.password.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_ADMIN_INFORMATION;
            return;
        }

        if (this.address == null || this.address.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_ADMIN_INFORMATION;
            return;
        }

        if (this.phone == null || this.phone.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_ADMIN_INFORMATION;
            return;
        }

        if (this.email == null || this.email.isEmpty()) {
            this.return_status_code = Return_Status_Codes.INVALID_ADMIN_INFORMATION;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
