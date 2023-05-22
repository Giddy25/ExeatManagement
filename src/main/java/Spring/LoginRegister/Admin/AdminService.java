package Spring.LoginRegister.Admin;

import Spring.LoginRegister.Dto.AdminDto;
import Spring.LoginRegister.Entity.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    Admin RegisterAdmin(AdminDto adminDto);
}
