package Spring.LoginRegister.Admin;

import Spring.LoginRegister.Entity.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository <Admin, Integer>{

    public Admin findAdminByEmail(String username);


}
