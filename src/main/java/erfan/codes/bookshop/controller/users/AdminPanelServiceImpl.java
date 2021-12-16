package erfan.codes.bookshop.controller.users;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SessionType;
import erfan.codes.bookshop.general.common.global.SessionUtil;
import erfan.codes.bookshop.models.LoginAdminModel;
import erfan.codes.bookshop.models.RegisterAdminModel;
import erfan.codes.bookshop.proto.holder.AdminGlobalV1;
import erfan.codes.bookshop.proto.holder.Global;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class AdminPanelServiceImpl implements IAdminPanelService {

    private final UserRepo userRepo;

    @Autowired
    public AdminPanelServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public AdminGlobalV1.registerAdmin.Builder registerAdmin(RegisterAdminModel registerAdminModel) {

        AdminGlobalV1.registerAdmin.Builder ret = AdminGlobalV1.registerAdmin.newBuilder();
        if (registerAdminModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {

            return registerAdminModel.getOutput().returnResponseObject(ret, registerAdminModel.return_status_code);
        }

        UserEntity admin = this.userRepo.createUserEntityTypeAdmin(registerAdminModel);
        UserEntity user = this.userRepo.save(admin);

        AdminGlobalV1.Admin.Builder adminDTO = this.userRepo.createAdminDTO(user);
        ret.setAdmin(adminDTO);
        SessionUtil.saveUserInfo(user.getId(), SessionType.ADMINS.getValue());
        return registerAdminModel.getOutput().returnResponseObject(ret, Return_Status_Codes.OK_VALID_FORM);
    }

    @Override
    public AdminGlobalV1.loginAdmin.Builder loginAdmin(LoginAdminModel loginAdminModel) {
        AdminGlobalV1.loginAdmin.Builder ret = AdminGlobalV1.loginAdmin.newBuilder();
        if (loginAdminModel.getReturn_status_code().getStatus() != Return_Status_Codes.OK_VALID_FORM.getStatus()) {

            return loginAdminModel.getOutput().returnResponseObject(ret, loginAdminModel.getReturn_status_code());
        }

        Optional<UserEntity> optionalUserAdmin = this.userRepo.findById(Long.valueOf(loginAdminModel.getUserId()));
        UserEntity adminUser = optionalUserAdmin.get();
        AdminGlobalV1.Admin.Builder adminDTO = this.userRepo.createAdminDTO(adminUser);
        ret.setAdmin(adminDTO);

        Global.SessionModel sessionModel = SessionUtil.getAndValidateSession(loginAdminModel.getSessionId());

        if (sessionModel == null)
            return loginAdminModel.getOutput().returnResponseObject(ret, Return_Status_Codes.SC_FORBIDDEN);

        long validUntil = sessionModel.getValidUntil();
        Date now = new Date();
        long l = now.getTime() / 1000;
        if (l > validUntil)
            SessionUtil.updateSessionExpireDate(sessionModel);

        return loginAdminModel.getOutput().returnResponseObject(ret, loginAdminModel.getReturn_status_code());
    }
}
