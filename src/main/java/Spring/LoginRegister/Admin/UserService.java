package Spring.LoginRegister.Admin;

import Spring.LoginRegister.Dto.AdminDto;
import Spring.LoginRegister.Entity.Admin;


public interface UserService {
    Admin RegisterAdmin(AdminDto adminDto);
}
