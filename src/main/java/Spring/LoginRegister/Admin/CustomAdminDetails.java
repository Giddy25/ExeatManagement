package Spring.LoginRegister.Admin;

import Spring.LoginRegister.Entity.Admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
//@Data
//@Builder
//@RequiredArgsConstructor

public class CustomAdminDetails implements UserDetails {

    private Admin admin;



    public CustomAdminDetails(Admin admin) {
        this.admin = admin;
    }





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authority = new ArrayList<>();
//        authority.add(new SimpleGrantedAuthority(admin.getRoles().toString()));
        return null;
    }


    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getEmail();
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
