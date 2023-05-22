/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.Dto;


import Spring.LoginRegister.Entity.House;
import Spring.LoginRegister.Entity.RolesConstant;
import jakarta.persistence.*;

/**
 *
 * @author siRe
 */
public class MasterDto {

    private int masterID;
    private String firstName;
    private String lastName;

    private String email;
    private String password;
    private String MasterPhoneNumber;
    @MapsId("HouseID")
    @ManyToOne
    @JoinColumn(name = "HouseID")
    private House house;


    public MasterDto(String firstName, String lastName, String MasterPhoneNumber,String email, String password, House house, RolesConstant roles) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.MasterPhoneNumber = MasterPhoneNumber;
        this.email = email;
        this.password = password;
        this.house = house;
        this.roles = roles;
    }



    @Enumerated(EnumType.STRING)
    private RolesConstant roles;

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public MasterDto() {
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
}
