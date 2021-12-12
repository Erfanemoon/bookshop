package erfan.codes.bookshop.services.api;

import erfan.codes.bookshop.controller.users.ISubscriberService;
import erfan.codes.bookshop.general.common.global.RM;
import erfan.codes.bookshop.models.SubscriberAddBookModel;
import erfan.codes.bookshop.models.SubscriberBooksModel;
import erfan.codes.bookshop.models.SubscriberLoginModel;
import erfan.codes.bookshop.models.SubscriberRegisterModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.proto.holder.SubscriberGlobalV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/v1/subscribers/")
public class SubscriberServicesApiV1 {

    ISubscriberService subscriberService;

    @Autowired
    public SubscriberServicesApiV1(ISubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @RM(
            isSessionValidationRequired = false,
            title = "api_v1_subscribers_register",
            protocolBufferReturn = SubscriberGlobalV1.addSubscriber.class,
            value = "/add/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void subscriberAdd(SubscriberRegisterModel subscriberRegisterModel) {
        SubscriberGlobalV1.addSubscriber.Builder builder = subscriberService.subscriberRegister(subscriberRegisterModel);
        subscriberRegisterModel.getOutput().write(builder);
    }

    @RM(
            isSessionValidationRequired = true,
            title = "api_v1_subscribers_login",
            protocolBufferReturn = SubscriberGlobalV1.loginSubscriber.class,
            value = "/login/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void subscriberLogin(SubscriberLoginModel subscriberLoginModel) {
        SubscriberGlobalV1.loginSubscriber.Builder builder = subscriberService.subscriberLogin(subscriberLoginModel);
        subscriberLoginModel.getOutput().write(builder);
    }

    @RM(
            isSessionValidationRequired = true,
            title = "api_v1_subscriber_booklist",
            protocolBufferReturn = SubscriberGlobalV1.SubscriberInfo.class,
            value = "/books/list/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void subscriberBooklist(SubscriberBooksModel subscriberBooksModel) {
        SubscriberGlobalV1.SubscriberInfo.Builder builder = subscriberService.subscriberBooklist(subscriberBooksModel);
        subscriberBooksModel.getOutput().write(builder);
    }


    @RM(
            isSessionValidationRequired = true,
            title = "api_v1_subscriber_addbook",
            protocolBufferReturn = SubscriberGlobalV1.SubscriberAddBook.class,
            value = "/books/add/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void subscriberAddBook(SubscriberAddBookModel subscriberAddBookModel) {
        SubscriberGlobalV1.SubscriberAddBook.Builder builder = subscriberService.subscriberAddBook(subscriberAddBookModel);
        subscriberAddBookModel.getOutput().write(builder);
    }
}
