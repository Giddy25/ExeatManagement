package Spring.LoginRegister.Student;

import Spring.LoginRegister.Entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    public String findStudentHouse(String studentNumber);
    public void deleteUnlinkedImages() throws IOException;
    public String findStudentHouseBYID(int studentID);
    List<Student> findByStudentNameContainingIgnoreCase(int masterid,String keyword);
    List<Student> findMasterStudentsMatchesHouse(int masterid);
    public String findStudentNumber(String studentNumber);
    Student saveStudent(Student student);
    Student getStudentById(int studentid);
    Student updateStudent(Student student);
    void  deleteStudentById(int studentid);
    Student findByStudentNumber(String studentNumber);
    List<Student> findByStatusLike(String status);
    public void FullStudentInfo(int studentID, Student ExistingStudent) throws IOException;
    public void updateImageFull(int studentID, MultipartFile image)throws IOException;
    List<Object> getContacts();
//    public List<Object> getParentContact(String studentName);
public void updateImage(String studentNumber, MultipartFile image) throws IOException;

}
