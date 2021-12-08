package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.General;
import org.springframework.validation.Errors;

public class SubscriberBooksModel extends BaseInputModel {

    private int subscriberId;
    //TODO later add pagination

    @Override
    public void validate(Errors errors) {
        if (General.isEmpty(String.valueOf(this.subscriberId))) {
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
