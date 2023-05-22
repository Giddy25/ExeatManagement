/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.Student;

import java.util.List;

import Spring.LoginRegister.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author siRe
 */

@Repository
public interface StudentRepository extends JpaRepository
        <Student, Integer> {

    List<Student> findByStudentClassStartingWith(String Studentclass);
    @Query(value ="select *  from Student s where s.student_number = :studentNumber" , nativeQuery = true)
    Student findBystudentNumber(@Param("studentNumber")String studentNumber);

    List<Student> findAll();

    @Query(value ="select  s.student_number from Student s where s.student_number = :studentNumber" , nativeQuery = true)
    public String findStudentByStudentNumber(@Param("studentNumber")String studentNumber);
    @Query(value ="select  distinct s.* from Student s join House h on  h.house_name = s.house_name join Master m on h.house_name= m.house_name where m.masterid = :masterid",nativeQuery = true)
    public List<Student> findMasterStudentsMatchesHouse(@Param("masterid") int masterid);
    @Query(value = "Select s.house_name from Student s where  s.student_number = :studentNumber", nativeQuery = true)
    public String findStudentHouse(@Param("studentNumber") String studentNumber);
    @Query(value = "Select s.house_name from Student s where  s.studentid = :studentID", nativeQuery = true)
    public String findStudentHouseBYID(@Param("studentID") int studentID);
    @Query(value = "Select m.house_name from Master m where  m.masterID = :masterID", nativeQuery = true)
    public String findStudentHouseByMaster(@Param("masterID") int masterID);
    @Query(value = "Select student.parent_contact from Student student where  student.student_name = :studentName", nativeQuery = true)
    public List<Object> getParentContact(@Param("studentName") String StudentName);

    @Query(value = "Select student from Student student where  student.student_id = :studentID", nativeQuery = true)
    public List<Object> getParentStudentDetails(@Param("studentID") int StudentID);
@Query(value = "select  distinct s.* from Student s "+"join House h on  h.house_name = s.house_name"+" join Master m on h.house_name= m.house_name"+" where m.masterid = :masterid and  lower(s.student_name) like lower(concat('%' ,:keyword, '%'))",nativeQuery = true)
    List<Student> findByStudentNameContainingIgnoreCase(@Param("masterid") int masterid,@Param("keyword") String keyword);

    @Query(value="Select student.parent_contact from Student student ",nativeQuery = true)
    public List<Object>getAllContacts();
}
