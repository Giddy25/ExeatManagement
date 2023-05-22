package Spring.LoginRegister.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table

@Entity

public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int masterID;
    private String firstName;
    private String lastName;
    private String MasterPhoneNumber;

    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RolesConstant roles;



@ManyToOne
@JoinColumn(name = "House_name", referencedColumnName = "houseName")
private House house;

    public Master(String firstName, String lastName, String MasterPhoneNumber, String email, String password, RolesConstant roles, House house) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.MasterPhoneNumber = MasterPhoneNumber;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.house = house;
    }

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getMasterPhoneNumber() {
        return MasterPhoneNumber;
    }

    public void setMasterPhoneNumber(String masterPhoneNumber) {
        MasterPhoneNumber = masterPhoneNumber;
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

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @ManyToMany
    @JoinTable(name = "Student_Master",
            joinColumns = @JoinColumn(name = "MasterID"),
            inverseJoinColumns = @JoinColumn(name = "Student_id")

    )
    private Set<Student>students = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "masters")

    private Set<Exeat> exeat = new HashSet<>();
    public String getFullName(){
        firstName = firstName!=null?firstName:"";
        lastName = lastName!=null?lastName:"";
        return (firstName +" "+lastName).trim();
    }
}
