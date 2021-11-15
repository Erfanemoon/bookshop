package erfan.codes.bookshop.general.common.global;

import erfan.codes.bookshop.models.BaseInputModel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BaseInputModel inputModel = (BaseInputModel) target;
        inputModel.validate(errors);
    }
}
