package Spring.LoginRegister.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor

//
//@Builder
//@Data
@Entity
@Table
@NoArgsConstructor

public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int StudentID;

    private String StudentNumber;
    private String StudentName;
    @Column(name = "student_class")
    private String studentClass;
    private String ParentContact;
    private  String sex;
    private String imagePath;


    public int getStudentID() {
        return StudentID;
    }
    @ManyToOne
    @JoinColumn(name = "House_name", referencedColumnName = "houseName")
    private House house;

    public Student(String studentNumber, String studentName, String studentClass, String parentContact, String sex, String imagePath, House house) {
        StudentNumber = studentNumber;
        StudentName = studentName;
        this.studentClass = studentClass;
        ParentContact = parentContact;
        this.sex = sex;
        this.imagePath = imagePath;
        this.house = house;
    }

    public Student(String imagePath) {
        this.imagePath = imagePath;
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
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
