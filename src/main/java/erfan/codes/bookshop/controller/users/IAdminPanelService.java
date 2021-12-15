package erfan.codes.bookshop.controller.users;

import erfan.codes.bookshop.models.RegisterAdminModel;
import erfan.codes.bookshop.proto.holder.AdminGlobalV1;

public interface IAdminPanelService {

    AdminGlobalV1.registerAdmin.Builder registerAdmin(RegisterAdminModel registerAdminModel);
}
