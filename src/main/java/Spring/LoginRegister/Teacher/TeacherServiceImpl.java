package Spring.LoginRegister.Teacher;

import Spring.LoginRegister.Entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class TeacherServiceImpl implements TeacherService{
@Autowired
TeacherRepository teacherRepository;
    @Override
    public Teacher Save(Teacher teacher) {
        teacher=new Teacher(teacher.getName());

        return teacherRepository.save(teacher);
    }
}
