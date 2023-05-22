package Spring.LoginRegister.Config;

import Spring.LoginRegister.Master.MasterRepository;
import Spring.LoginRegister.Master.CustomMasterDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@Order(1)
public class MasterSecConfig {
    private final MasterRepository masterRepository;

    public MasterSecConfig(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Bean
    public UserDetailsService getuserDetailsService(){
        return new CustomMasterDetailsService(masterRepository);

    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder1(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider1(){
        DaoAuthenticationProvider authProvider1 = new DaoAuthenticationProvider();
        authProvider1.setUserDetailsService(getuserDetailsService());
        authProvider1.setPasswordEncoder(passwordEncoder1());
        return authProvider1;
    }
@Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception{
        http.authenticationProvider(authenticationProvider1());
        http.authorizeHttpRequests().requestMatchers("/").permitAll();
        http.securityMatcher("/master/**").authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/master/login")
                .usernameParameter("username")
                .loginProcessingUrl("/master/login")
                .defaultSuccessUrl("/master/MasterDashBoard").permitAll()
                .and()
                .logout()
                .logoutUrl("/master/logout")

                .logoutSuccessUrl("/");


                return http.build();
}

}