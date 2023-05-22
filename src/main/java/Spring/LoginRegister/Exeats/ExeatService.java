package Spring.LoginRegister.Exeats;

import Spring.LoginRegister.Entity.Exeat;
import org.springframework.data.domain.Page;


import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface ExeatService {
    Exeat IssueExeat(Exeat exeat) throws IOException;
//    int updateHasReturned(int exeatID);

    Exeat updateExeat(int exeatid,Exeat exeatRequest) throws IOException;
    public String SendSms(Exeat exeat) throws IOException;

    public Exeat findExeatBygeneratedCode(String generatedCode);

   public void updateColumn(String newValue);
    Exeat findExeatById(int exeatid);
    long findExeatByStudentNumber(String StudentNumber);
    String CheckLastIssue(String studentNumber);
    List<Exeat> findBystudentNameContainingIgnoreCase(String keyword);
    public Page<Exeat> findSortedPage(int pageNumber, int pageSize);

//    Page<Exeat> getAllExeats();
}

