package Spring.LoginRegister.Exeats;

import Spring.LoginRegister.Entity.Exeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExeatRepository extends JpaRepository<Exeat, Integer> {


public Exeat findExeatBygeneratedCode(String generatedCode);


    @Query(value = "Select count(student_number)   from Exeat e  where  e.student_number = :studentNumber", nativeQuery = true)

    public  long  CountStudentNumber(String studentNumber);
    @Query(value = "Select date_returned from Exeat e  where  e.student_number = :studentNumber order by e.exeatid desc limit 1 ", nativeQuery = true)

    public  String  lastReturned(String studentNumber);








}
