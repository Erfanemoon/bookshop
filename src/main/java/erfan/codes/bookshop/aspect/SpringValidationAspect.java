package erfan.codes.bookshop.aspect;

import erfan.codes.bookshop.general.common.global.RM;
import erfan.codes.bookshop.models.BaseInputModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.List;

@Aspect
@Component
public class SpringValidationAspect {

    @Autowired
    private SpringValidatorRegistry springValidatorRegistry;

    @Around("@annotation(erfan.codes.bookshop.general.common.global.RM)")
    public Object inputValidation(ProceedingJoinPoint aJoinPoint) throws Throwable {
        MethodSignature mSignature = (MethodSignature) aJoinPoint.getSignature();
        RM rm = mSignature.getMethod().getDeclaredAnnotation(RM.class);
        if (rm != null) {
            validate(aJoinPoint);
            return aJoinPoint.proceed();
        }
        return null;
    }

    private void validate(JoinPoint aJoinPoint) {
        Object[] args = aJoinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof BaseInputModel) {
                    BaseInputModel inputModel = (BaseInputModel) arg;
                    List<Validator> validatorList = springValidatorRegistry.getValidatorsForObject(inputModel);
                    for (Validator validator : validatorList) {
                        BindingResult errs = new BeanPropertyBindingResult(inputModel, inputModel.getClass().getSimpleName());
                        validator.validate(inputModel, errs);
                        if (errs.hasErrors())
                            throw new SpringValidationException(errs);
                    }
                }
            }
        }
    }
}
