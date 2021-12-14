package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import org.springframework.validation.Errors;

public class ListBooksModel extends BaseInputModel {

    //TODO add sorting and pagination later
    @Override
    public void validate(Errors errors) {

        this.return_status_code = Return_Status_Codes.OK_VALID_FORM;
    }
}
