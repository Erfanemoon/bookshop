package erfan.codes.bookshop.aspect;

import org.springframework.validation.BindingResult;

public class SpringValidationException extends RuntimeException {

    private BindingResult bindingResult;

    public SpringValidationException(final BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
