package Spring.LoginRegister.Master;

import Spring.LoginRegister.Entity.Master;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomMasterDetails implements UserDetails {
   private Master master;


    public CustomMasterDetails(Master master) {
        this.master = master;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authority = new ArrayList<>();
//        authority.add(new SimpleGrantedAuthority(master.getRoles().toString()));
        return null;
    }

    @Override
    public String getPassword() {
        return master.getPassword();
    }

    @Override
    public String getUsername() {
        return master.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
