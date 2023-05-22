package Spring.LoginRegister.Dto;

import Spring.LoginRegister.Entity.RolesConstant;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class AdminDto {
    private int id;
    private String name;

    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RolesConstant roles;

    public AdminDto(String name, String email, String password, RolesConstant roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolesConstant getRoles() {
        return roles;
    }

    public void setRoles(RolesConstant roles) {
        this.roles = roles;
    }
}
