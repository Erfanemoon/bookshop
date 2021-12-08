package erfan.codes.bookshop.aspect;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.General;
import erfan.codes.bookshop.general.common.global.RM;
import erfan.codes.bookshop.general.common.global.SessionUtil;
import erfan.codes.bookshop.general.common.global.config.ConfigReader;
import erfan.codes.bookshop.models.BaseInputModel;
import erfan.codes.bookshop.proto.holder.Global;
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
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class SpringValidationAspect {

    @Autowired
    private SpringValidatorRegistry springValidatorRegistry;

    @Around("@annotation(erfan.codes.bookshop.general.common.global.RM)")
    public Object inputValidation(ProceedingJoinPoint aJoinPoint) throws Throwable {
        String uri = "";
        Map pathVariables = null;
        HttpServletRequest request = SessionUtil.getRequest();

        if (request != null) {
            pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            uri = request.getRequestURI();
        }

        MethodSignature mSignature = (MethodSignature) aJoinPoint.getSignature();
        RM rm = mSignature.getMethod().getDeclaredAnnotation(RM.class);

        if (rm != null) {
            if (rm.isSessionValidationRequired()) {
                if (uri.contains("/apipanel/")) {
                    //TODO implement role session management for apipanel later on
                    System.out.println("TODO session management ...");

                } else if (uri.contains("/api/")) {

                    String sessionId = SessionUtil.getSessionId(request);
                    if (sessionId == null || sessionId.isEmpty()) {
                        General.writeErrorOutput(Return_Status_Codes.SC_FORBIDDEN);
                    } else {
                        Global.SessionModel sessionModel = SessionUtil.getAndValidateSession(sessionId);
                        if (sessionModel == null)
                            General.writeErrorOutput(Return_Status_Codes.SC_FORBIDDEN);
                        else {
                            long validUntil = sessionModel.getValidUntil();
                            Date now = new Date();
                            long l = now.getTime() / 1000;
                            if (l > validUntil) {
                                if (uri.contains("/login/")) {
                                    SessionUtil.updateSessionExpireDate(sessionModel);
                                }
                                General.writeErrorOutput(Return_Status_Codes.SESSION_NO_LONGER_VALID);
                            }
                        }
                    }
                }
            }
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
