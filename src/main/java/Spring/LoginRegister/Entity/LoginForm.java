package Spring.LoginRegister.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginForm {
    public String getAccountname(){
        return accountname;
    }
    String accountname;
    String password;
}
