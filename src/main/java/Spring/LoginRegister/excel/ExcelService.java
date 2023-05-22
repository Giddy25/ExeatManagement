/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.excel;

import java.util.List;

import Spring.LoginRegister.Entity.House;
import Spring.LoginRegister.Entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author siRe
 */
@Service
public interface ExcelService  {
    
    
    public void saveStudent(MultipartFile file);

        
        List<Student> getStudents();

    public House findHouse(int houseID);
public  House findHouseByName(String houseName);
    
}
