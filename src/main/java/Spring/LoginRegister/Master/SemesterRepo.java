package Spring.LoginRegister.Master;

import Spring.LoginRegister.Entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepo extends JpaRepository<Semester,Integer> {
}
