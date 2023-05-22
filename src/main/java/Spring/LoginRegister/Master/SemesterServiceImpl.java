/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.Master;

import Spring.LoginRegister.Entity.Semester;
import Spring.LoginRegister.Master.MasterService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author siRe
 */
@Service
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    SemesterRepo repo;
    @Autowired
    MasterService switchService;

    @Override

    public void changeSemester() {
        Semester sem = new Semester();
if(repo.findAll().isEmpty()){
        repo.save(new Semester("1"));
}else{
        for (Semester s : repo.findAll()) {
            if (s.getSemester().equals("1")) {
                System.out.println("sems:" + s.getSemester());

                s.setSemester("2");
                repo.save(s);
            } else if(s.getSemester().equals("2")) {
                                s.setSemester("1");

                switchService.changeClass();
                repo.save(s);
                System.out.println(" Ist sems:" + s.getSemester());


            }
        }
}

    }

}
