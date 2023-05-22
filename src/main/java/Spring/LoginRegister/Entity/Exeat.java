package Spring.LoginRegister.Entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table
    public class Exeat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long exeatID;
    @Column
    
    private String studentNumber;
    private String StudentName;

    private String studentClass;
    private String purpose;
    private String destination;
    private LocalDateTime dateToReturn;

    private String generatedCode;

    @Column(nullable = false)
    private String dateOUT;

    @Column(name = "date_returned")
    private String dateReturned;

    private String issuedBy;
    @Column(name = "has_returned")
    private boolean hasReturned;
    private String parentContact;
    private String email;
    private String masterPhoneNumber;

    private String updateDuration;
    @Column
    private Duration TimeRemain;


    @PrePersist
    @PreUpdate
    public void  calculateDuration(){
        LocalDateTime currentDateTime = LocalDateTime.now();
       if(dateToReturn != null){
           TimeRemain = Duration.between( currentDateTime, dateToReturn);

       }else {
           TimeRemain=null;
       }

    }



    public Exeat(String studentNumber, String studentName, String studentClass, String purpose, String destination, LocalDateTime dateToReturn, String generatedCode, String dateOUT, String issuedBy, boolean hasReturned, String parentContact, String masterPhoneNumber, String email) {
        this.studentNumber = studentNumber;
        StudentName = studentName;
        this.studentClass = studentClass;
        this.purpose = purpose;
        this.destination = destination;
        this.dateToReturn = dateToReturn;
        this.generatedCode = generatedCode;
        this.dateOUT = dateOUT;
        this.issuedBy = issuedBy;
        this.hasReturned = hasReturned;
        this.parentContact = parentContact;
        this.masterPhoneNumber = masterPhoneNumber;
        this.email = email;
    }

    public Exeat(String dateReturned, boolean hasReturned) {
        this.dateReturned = dateReturned;
        this.hasReturned = hasReturned;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMasterPhoneNumber() {
        return masterPhoneNumber;
    }

    public void setMasterPhoneNumber(String masterPhoneNumber) {
        this.masterPhoneNumber = masterPhoneNumber;
    }

    public long getExeatID() {
        return exeatID;
    }

    public void setExeatID(long exeatID) {
        this.exeatID = exeatID;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDateToReturn() {
        return dateToReturn;
    }

    public void setDateToReturn(LocalDateTime dateToReturn) {
        this.dateToReturn = dateToReturn;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public String getDateOUT() {
        return dateOUT;
    }

    public void setDateOUT(String dateOUT) {
        this.dateOUT = dateOUT;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public boolean isHasReturned() {
        return hasReturned;
    }

    public void setHasReturned(boolean hasReturned) {
        this.hasReturned = hasReturned;
    }

    public String getParentContact() {
        return parentContact;
    }


    public void setParentContact(String parentContact) {
        this.parentContact = parentContact;
    }



    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    @JoinTable(name = "Exeat_Master",
            joinColumns = @JoinColumn(name = "exeatID"),
            inverseJoinColumns = @JoinColumn(name = "masterID")

    )
    private Set<Master> masters=new HashSet<>();



}
