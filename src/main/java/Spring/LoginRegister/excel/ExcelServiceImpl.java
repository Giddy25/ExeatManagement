/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.excel;

import java.util.ArrayList;
import java.util.List;

import Spring.LoginRegister.Entity.House;
import Spring.LoginRegister.Entity.Student;
import Spring.LoginRegister.House.HouseRepository;
import Spring.LoginRegister.Master.MasterRepository;
import Spring.LoginRegister.Student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author siRe
 */
@Component
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    StudentRepository repo;
    private String studentName;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    MasterRepository masterRepository;

//    @Override
//    public void saveStudent(MultipartFile file, int houseID) {
//        //            List<Student> students = ExcelHelper.convertExcelToListOfStudent(file.getInputStream());
////            System.out.println("serviceImpl2" + students);
//
//
////            repo.saveAll(students);students
//    }

    @Override
    public void saveStudent(MultipartFile file) {

    }

    @Override
    public List<Student> getStudents() {
        List<Student> list = new ArrayList<>();

        Streamable.of(repo.findAll()).forEach(list::add);

        return list;
    }

    @Override
    public House findHouse(int houseID) {
        return houseRepository.findById(houseID).get();

    }

    @Override
    public House findHouseByName(String houseName) {
        return houseRepository.findByhouseName(houseName);
    }


}
