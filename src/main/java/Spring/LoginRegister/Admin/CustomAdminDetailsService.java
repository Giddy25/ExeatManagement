package Spring.LoginRegister.Admin;

import Spring.LoginRegister.Entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAdminDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    public CustomAdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByEmail(username);

        if (admin == null) {
            throw new UsernameNotFoundException("User not found");

        }
        return new CustomAdminDetails(admin);
    }
    }

