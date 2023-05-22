/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.Master;

import Spring.LoginRegister.Dto.MasterDto;
import Spring.LoginRegister.Entity.Master;

import java.util.Set;


import Spring.LoginRegister.Entity.RolesConstant;
import Spring.LoginRegister.Entity.Student;
import Spring.LoginRegister.Student.StudentRepository;
import Spring.LoginRegister.Student.StudentService;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 *
 * @author siRe
 */
@Service
public class MasterServiceImpl implements MasterService {

@Autowired
    private MasterRepository masterRepo;
@Autowired
    StudentService studentService;
@Autowired
    StudentRepository studentRepos;
@Autowired
private PasswordEncoder passwordEncoder2;


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();


    @Override
    public Master registerMaster(MasterDto masterDetails) {

        Master master = new Master(masterDetails.getFirstName(), masterDetails.getLastName(), masterDetails.getMasterPhoneNumber(),masterDetails.getEmail(), passwordEncoder2.encode(masterDetails.getPassword()),RolesConstant.ROLE_HOUSEMASTER,masterDetails.getHouse());

        return masterRepo.save(master);


        //masterDetails comes from object retrieved from registrtaion page Object through controller @ModelAttribute


    }

    @Override
    public Set<Master> getAllMasters() {
        return null;
    }

    @Override
    public void changeClass() {
        String newClass = null;

        for (Student s : studentService.getAllStudents()) {

            char charAt = s.getStudentClass().charAt(0);
            System.out.println("class:" + s.getStudentClass());
            String substring = s.getStudentClass().substring(1);

            System.out.println("Ist char is:" + charAt);

            if (String.valueOf(charAt).equals("3")) {
                newClass = "Completed";
                s.setStudentClass(newClass);
                studentRepos.save(s);

            } else {
                if (!String.valueOf(charAt).equals("C")) {
                    int i = Integer.parseInt(String.valueOf(charAt));
                    int increasedValue = ++i;

                    newClass = String.valueOf(increasedValue).concat(substring);
                    System.out.println("new Class is:" + newClass);

                    s.setStudentClass(newClass);
                    studentRepos.save(s);

                }
            }
        }
    }

    @Override
    public int getMasterById(String username) {
        return masterRepo.findMasterIDByEmail(username);
    }

}