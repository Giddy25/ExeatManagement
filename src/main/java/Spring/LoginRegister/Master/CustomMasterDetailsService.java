package Spring.LoginRegister.Master;


import Spring.LoginRegister.Entity.Master;
import Spring.LoginRegister.Master.CustomMasterDetails;
import Spring.LoginRegister.Master.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomMasterDetailsService implements UserDetailsService {
    @Autowired
    private MasterRepository masterRepository;

    public CustomMasterDetailsService(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Master master = masterRepository.findMasterByEmail(username);

        if (master == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomMasterDetails(master);
    }
    }

