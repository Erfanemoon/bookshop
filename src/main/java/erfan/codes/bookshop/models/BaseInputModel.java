package erfan.codes.bookshop.models;

import com.google.gson.annotations.Expose;
import erfan.codes.bookshop.enums.IReturn_Status_Codes;
import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.Output;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

public abstract class BaseInputModel {

    @Expose
    private String sessionId;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Output output;
    public IReturn_Status_Codes return_status_code = Return_Status_Codes.UNDEFINED;

    public BaseInputModel() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            this.request = ((ServletRequestAttributes) requestAttributes).getRequest();
            this.response = ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        if (this.request != null) {
            Enumeration<String> headerNames = this.request.getHeaderNames();
            ArrayList<String> headers = Collections.list(headerNames);
            if (headers.contains("sessionId") || headers.contains("sessionid"))
                this.sessionId = request.getHeader("sessionId");
        }

        this.output = new Output(this.request, this.response);
    }

    public abstract void validate(Errors errors);

    public IReturn_Status_Codes getReturn_status_code() {
        return return_status_code;
    }

    public Output getOutput() {
        return output;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
