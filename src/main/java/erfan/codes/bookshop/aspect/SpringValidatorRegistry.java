package erfan.codes.bookshop.aspect;

import erfan.codes.bookshop.models.BaseInputModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class SpringValidatorRegistry {

    private final List<Validator> validatorList = new ArrayList<>();

    public void addValidator(Validator validator) {
        this.validatorList.add(validator);
    }


    public List<Validator> getValidatorsForObject(Object object) {

        List<Validator> result = new ArrayList<>();
        for (Validator validator : validatorList) {
            if (validator.supports(object.getClass())) {
                result.add(validator);
            }
        }
        return result;
    }
}
