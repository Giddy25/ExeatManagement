package Spring.LoginRegister.Exeats;

import Spring.LoginRegister.Entity.Exeat;
import Spring.LoginRegister.Entity.Master;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service

public class ExeatServiceImpl implements ExeatService {
    @Autowired
    ExeatRepository exeatRepository;


    @Override
    public Exeat IssueExeat(Exeat exeat) throws IOException {
        String msg = "";

        Exeat exeat1 = new Exeat(exeat.getStudentNumber(), exeat.getStudentName(), exeat.getStudentClass(), exeat.getPurpose(), exeat.getDestination(),exeat.getDateToReturn(), exeat.getGeneratedCode(),  exeat.getDateOUT(),exeat.getIssuedBy(),exeat.isHasReturned(), exeat.getParentContact(),exeat.getMasterPhoneNumber(), exeat.getEmail());
        Master master = new Master();
        exeat.getMasters().add(master);
        master.getExeat().add(exeat);
        exeatRepository.save(exeat1);
        if (exeatRepository.save(exeat1) != null) {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String contact = exeat.getParentContact();
            String contact2 = exeat.getMasterPhoneNumber();
            String studentName = exeat.getStudentName();
            String DateOut = exeat.getDateOUT();
            String MasterName= exeat.getIssuedBy();
            String code = exeat.getGeneratedCode();
            String purpose = exeat.getPurpose();
            String dest = exeat.getDestination();

            Request request = new Request.Builder()
                    .get()
                    .url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=eEJkSHptdGtTSmlDeFpzWk5hYU4&to=".concat(contact.trim()).concat("&from=SidGidTech&sms=Hello dear guardian, your ward ").concat(studentName).concat(" just signed an exeat at St jerome SHS.Purpose is: ").concat( purpose).concat(" destination : ").concat(dest).concat(" at exactly  ").concat(DateOut).concat("").concat("  exeat token is: ").concat(code))


                    .build();
            OkHttpClient client1 = new OkHttpClient().newBuilder().build();
            Request request1 = new Request.Builder()
             .get().url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=eEJkSHptdGtTSmlDeFpzWk5hYU4&to=".concat(contact2.trim()).concat("&from=SidGidTech&sms=Hello Mr/Mrs ").concat(MasterName ).concat("  you have issued exeat to ").concat(studentName).concat(" Purpose  ").concat( purpose ).concat(" destination : ").concat(dest).concat(" at exactly ").concat(DateOut).concat(" exeat token is: ").concat(code))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                Response response1= client1.newCall(request1).execute();

                if (!response.isSuccessful() ||!!response1.isSuccessful()) {

                    msg = "sms failed try again";

                    System.out.println("SMS RESPONSE:" + msg);

//                throw new IOException("Unexpected code " + response);
                }
            }

        }

        return exeatRepository.save(exeat1);

    }


    @Override
    public Exeat findExeatById(int exeatid) {
        return exeatRepository.findById(exeatid).get();
    }

    public long findExeatByStudentNumber(String studentNumber) {
        return exeatRepository.CountStudentNumber(studentNumber);
    }

    @Override
    public String CheckLastIssue(String studentNumber) {
        return exeatRepository.lastReturned(studentNumber);
    }

    @Override
    public List<Exeat> findBystudentNameContainingIgnoreCase(String keyword) {
        return null;
    }


    public Exeat findExeatBygeneratedCode(String generatedCode) {
        return exeatRepository.findExeatBygeneratedCode(generatedCode);
    }



    @Override
    public void updateColumn(String newValue) {
        List<Exeat> exeats = exeatRepository.findAll();
        for (Exeat exeat: exeats){
            exeat.setUpdateDuration(newValue);
        }
        exeatRepository.saveAll(exeats);
    }


       public Page<Exeat> findSortedPage(int pageNumber, int pageSize){
        Sort sort = Sort.by(Sort.Direction.ASC,"hasReturned").and(Sort.by(Sort.Direction.ASC, "dateToReturn"));
           PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,sort);
           return exeatRepository.findAll(pageRequest);
    }
    public Exeat updateExeat(int exeatID, Exeat exeatRequest) throws IOException {
        String msg = "";
        Exeat existingExeat = exeatRepository.findById(exeatID).get();
        existingExeat.setHasReturned(exeatRequest.isHasReturned());
        existingExeat.setDateReturned(exeatRequest.getDateReturned());
        exeatRepository.save(existingExeat);
        if (exeatRepository.save(existingExeat) != null) {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String contact = existingExeat.getParentContact();
            String contact2 = existingExeat.getMasterPhoneNumber();
           String  MasterName = existingExeat.getIssuedBy();
            String studentName = existingExeat.getStudentName();
            String code = existingExeat.getGeneratedCode();
            String DateReturned = existingExeat.getDateReturned();
            String dest = existingExeat.getDestination();

            Request request = new Request.Builder()
                    .get()
                    .url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=eEJkSHptdGtTSmlDeFpzWk5hYU4&to=".concat(contact.trim()).concat("&from=SidGidTech&sms=Hello dear guardian, we will like to inform you that your ward ").concat(studentName).concat(" has safely returned to St jerome SHS ").concat(" from  ").concat(dest).concat(" at exactly  ").concat(DateReturned).concat(". exeat token is: ").concat(code) )

                    .build();
            OkHttpClient client1 = new OkHttpClient().newBuilder().build();
            Request request1 = new Request.Builder()
                    .get()
                    .url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=eEJkSHptdGtTSmlDeFpzWk5hYU4&to=".concat(contact2.trim()).concat("&from=SidGidTech&sms=Hello Mr/Mrs  ").concat( MasterName).concat(" we will like to inform you that ").concat(studentName).concat(" has safely returned to St jerome SHS ").concat(" from  ").concat( dest ).concat(" at exactly  ").concat(DateReturned).concat(". exeat token is: ").concat(code))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                Response response1= client1.newCall(request1).execute();

                if (!response.isSuccessful() ||!!response1.isSuccessful()) {
                    msg = "sms failed try again";

                    System.out.println("SMS RESPONSE:" + msg);

//                throw new IOException("Unexpected code " + response);
                }
            }
        }
       return exeatRepository.save(existingExeat);
    }
public String SendSms(Exeat exeat) throws IOException {
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    String contact = exeat.getParentContact();
    String contact2 = exeat.getMasterPhoneNumber();
    String studentName = exeat.getStudentName();
    String DateOut = exeat.getDateOUT();
    String MasterName= exeat.getIssuedBy();
    String code = exeat.getGeneratedCode();
    String purpose = exeat.getPurpose();
    String dest = exeat.getDestination();

    String msg = "";

    Request request = new Request.Builder()
            .get()
            .url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=eEJkSHptdGtTSmlDeFpzWk5hYU4&to=".concat(contact.trim()).concat("&from=SidGidTech&sms=Hello dear guardian, your ward ").concat(studentName).concat(" just signed an exeat at St jerome SHS.Purpose is: ").concat( purpose).concat(" destination : ").concat(dest).concat(" at exactly  ").concat(DateOut).concat("").concat("  exeat token is: ").concat(code))


            .build();
    OkHttpClient client1 = new OkHttpClient().newBuilder().build();
    Request request1 = new Request.Builder()
            .get().url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=eEJkSHptdGtTSmlDeFpzWk5hYU4&to=".concat(contact2.trim()).concat("&from=SidGidTech&sms=Hello Mr/Mrs ").concat(MasterName ).concat(" you have issued exeat to ").concat(studentName ).concat(" SHS.Purpose  ").concat(purpose).concat(" destination : ").concat(dest).concat(" at exactly ").concat(DateOut).concat(". exeat token is: ").concat(code))
            .build();
    try (Response response = client.newCall(request).execute()) {
        Response response1= client1.newCall(request1).execute();

        if (!response.isSuccessful() ||!!response1.isSuccessful()) {

            msg = "sms failed try again";

            System.out.println("SMS RESPONSE:" + msg);

//                throw new IOException("Unexpected code " + response);
        }
    }
return SendSms(exeat);
}

}

