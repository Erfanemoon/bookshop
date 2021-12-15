package erfan.codes.bookshop.controller.users;

import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.models.RegisterAdminModel;
import erfan.codes.bookshop.proto.holder.AdminGlobalV1;
import erfan.codes.bookshop.repositories.UserRepo;
import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        return registerAdminModel.getOutput().returnResponseObject(ret, Return_Status_Codes.OK_VALID_FORM);
    }
}
