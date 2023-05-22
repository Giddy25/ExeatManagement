package Spring.LoginRegister.Teacher;

import Spring.LoginRegister.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
}
