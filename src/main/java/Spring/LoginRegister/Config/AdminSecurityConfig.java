package Spring.LoginRegister.Config;

import Spring.LoginRegister.Admin.AdminRepository;
import Spring.LoginRegister.Admin.CustomAdminDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(2)
public class AdminSecurityConfig {
    private final AdminRepository adminRepository;

    public AdminSecurityConfig(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Bean
   @Primary
    BCryptPasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService2() {
        return new CustomAdminDetailsService(adminRepository);
    }

@Bean
public DaoAuthenticationProvider authenticationProvider2(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService2());
        authProvider.setPasswordEncoder(passwordEncoder2());
        return authProvider;
}
    @Bean

    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
        .authenticationProvider(authenticationProvider2());
        http.securityMatcher("/admin/**").authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/login").usernameParameter("username")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/AdminDashBoard")
                .permitAll()
                .and().logout()
                .logoutUrl("/admin/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/");



        return http.build();
    }
}