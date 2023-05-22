package Spring.LoginRegister.Admin;

import Spring.LoginRegister.Dto.AdminDto;
import Spring.LoginRegister.Entity.Admin;

import Spring.LoginRegister.Entity.RolesConstant;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Admin RegisterAdmin(AdminDto adminDto) {
        Admin admin = new Admin(adminDto.getName(), adminDto.getEmail(), passwordEncoder.encode(adminDto.getPassword()), RolesConstant.ROLE_ADMIN);

        return adminRepository.save(admin);
    }
}
