package Spring.LoginRegister.DutyRoster;

import Spring.LoginRegister.Entity.Teacher;
import Spring.LoginRegister.Teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class dutyRosterServiceImpl implements dutyRosterService{
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
}
