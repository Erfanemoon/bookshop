package erfan.codes.bookshop.services.panel;

import erfan.codes.bookshop.controller.users.IAdminPanelService;
import erfan.codes.bookshop.general.common.global.RM;
import erfan.codes.bookshop.models.LoginAdminModel;
import erfan.codes.bookshop.models.RegisterAdminModel;
import erfan.codes.bookshop.proto.holder.AdminGlobalV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/apipanel/v1/admins/")
public class AdminServicesPanelV1 {

    private final IAdminPanelService iAdminPanelService;

    @Autowired
    public AdminServicesPanelV1(IAdminPanelService iAdminPanelService) {
        this.iAdminPanelService = iAdminPanelService;
    }

    @RM(
            isSessionValidationRequired = false,
            title = "apipanel_v1_admin_register",
            protocolBufferReturn = AdminGlobalV1.registerAdmin.class,
            value = "/register/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void registerAdmin(RegisterAdminModel registerAdminModel) {
        AdminGlobalV1.registerAdmin.Builder builder = this.iAdminPanelService.registerAdmin(registerAdminModel);
        registerAdminModel.getOutput().write(builder);
    }

    @RM(
            isSessionValidationRequired = true,
            title = "apipanel_v1_admin_login",
            protocolBufferReturn = AdminGlobalV1.registerAdmin.class,
            value = "/login/",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void loginAdmin(LoginAdminModel loginAdminModel) {
        AdminGlobalV1.loginAdmin.Builder builder = this.iAdminPanelService.loginAdmin(loginAdminModel);
        loginAdminModel.getOutput().write(builder);
    }
}
