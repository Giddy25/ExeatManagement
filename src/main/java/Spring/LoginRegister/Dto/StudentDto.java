package Spring.LoginRegister.Dto;

import Spring.LoginRegister.Entity.House;
import jakarta.persistence.*;

public class StudentDto {

    @Id

    private int StudentID;
    private String StudentNumber;
    private String StudentName;
    private String StudentClass;
    private String ParentContact;


    private String sex;
    @MapsId("HouseID")
    @ManyToOne
    @JoinColumn(name = "HouseID")
    private House house;

    public StudentDto(String studentNumber, String studentName, String studentClass, String parentContact, String sex, House house) {
        StudentNumber = studentNumber;
        StudentName = studentName;
        StudentClass = studentClass;
        ParentContact = parentContact;
        this.sex = sex;
        this.house = house;
    }

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int studentID) {
        StudentID = studentID;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentClass() {
        return StudentClass;
    }

    public void setStudentClass(String studentClass) {
        StudentClass = studentClass;
    }

    public String getParentContact() {
        return ParentContact;
    }

    public void setParentContact(String parentContact) {
        ParentContact = parentContact;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
