package erfan.codes.bookshop.models;

import erfan.codes.bookshop.enums.IReturn_Status_Codes;
import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.Output;
import erfan.codes.bookshop.repositories.ObjectRepo;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class BaseInputModel {


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

        this.output = new Output(this.request, this.response);
    }

    public abstract void validate(Errors errors);

    public IReturn_Status_Codes getReturn_status_code() {
        return return_status_code;
    }

    public Output getOutput() {
        return output;
    }
}
