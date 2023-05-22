package Spring.LoginRegister.Controller;

import Spring.LoginRegister.Dto.AdminDto;
import Spring.LoginRegister.Dto.MasterDto;
import Spring.LoginRegister.Entity.*;

import Spring.LoginRegister.Exeats.ExeatRepository;
import Spring.LoginRegister.Exeats.ExeatService;
import Spring.LoginRegister.House.HouseService;
import Spring.LoginRegister.Master.MasterService;
import Spring.LoginRegister.Master.MasterServiceImpl;
import Spring.LoginRegister.Admin.AdminRepository;

import Spring.LoginRegister.Master.MasterRepository;
import Spring.LoginRegister.Student.StudentRepository;

//import Spring.LoginRegister.excel.ExcelService;
import Spring.LoginRegister.Admin.*;
import Spring.LoginRegister.Student.StudentService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
public class HomeController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private ExeatService exeatService;


    @Autowired
    private MasterService masterservice;


    public HomeController(StudentService studentService, AdminService adminService, HouseService houseService, MasterService masterservice) {
        this.studentService = studentService;
        this.adminService = adminService;
        this.houseService = houseService;
        this.masterservice = masterservice;
//        this.service = service;
    }

    @Autowired
    AdminRepository adminRepository;
//    @Autowired
//    ExcelService service;

    @Autowired
    MasterRepository masterRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExeatRepository exeatRepository;

    @ModelAttribute
    private void AdminDetails(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            Admin adminDetails = adminRepository.findAdminByEmail(username);
            model.addAttribute("admin", adminDetails);
        }
    }




    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/m/register")
    public String showMasterReg(Model model) {

        model.addAttribute("houses", houseService.getAllHouses());
        return "masterregister";
    }

    @PostMapping("/masterregister")
    public String registerMaster(@ModelAttribute("masterDetails") MasterDto masterDetails) {



        masterservice.registerMaster(masterDetails);

        return "redirect:/?success";
    }

    @GetMapping("/register")
    public String RegistrationForm(Model model) {

        model.addAttribute("admin", new Admin());
        return "signup_form";
    }

    @GetMapping("/admin/login")
    public String LoginForm(){
        return "Adminlogin";
    }

    

    @GetMapping("/Admin/StudentRegister")
    public String showStudentRegistrationpage() {

        return "home";
    }



    @PostMapping("/process_register")
    public String processRegister(@ModelAttribute("AdminDto") AdminDto adminDto) {

        adminService.RegisterAdmin(adminDto);
        return "login";
    }

    @GetMapping("/admin/AdminDashBoard")
    public String AdminDashBoard(Model model) {
        List<Student> students;

        model.addAttribute("students", studentService.getAllStudents() );
        model.addAttribute("countUsers", adminRepository.count());
        return "admindash";
    }
    @GetMapping("/admin/MasterDashBoard")
    public String MasterDashBoard(@RequestParam(required = false,defaultValue = "B") String newValue,@RequestParam(defaultValue = "0")int page,Principal principal, Model model,Exeat exeat) throws IOException {
//       this is just to make the exeat table always updated
        newValue = RandomStringUtils.randomAlphabetic(4);
    exeatService.updateColumn(newValue);
        System.out.println(newValue);


        Page<Exeat> exeats= exeatService.findSortedPage(page,10);



        String username = principal.getName();
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
        model.addAttribute("exeats",exeats );
        model.addAttribute("currentPage",page );

        model.addAttribute("countUsers", masterRepository.count());
        return "masterDash";
    }
    public String getUsername(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication==null)
            return null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        else {
            return principal.toString();
        }
    }
    @GetMapping("/admin/ShowExeatCard/{studentNumber}")
    public String ShowExeatCard(@PathVariable String studentNumber, Model model, Principal principal) {
        String username = principal.getName();
        DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy: hh:mm a");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("dateOUT",dateString);
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
        model.addAttribute("student", studentService.findByStudentNumber(studentNumber) );
        model.addAttribute("studentHouse", studentService.findStudentHouse(studentNumber) );
        System.out.println(studentService.findStudentHouse(studentNumber));


        return "card2";
    }
    @GetMapping("/admin/StudentRegister")
    public String StudentRegisterForm(Model model, Principal principal, Master master) {
        return "StudentRegister";
    }

    @GetMapping("/admin/AlreadyIssuedExeat/{exeatID}")
    public String AlreadyIssuedExeatCard(@PathVariable int exeatID, Model model, Principal principal) {
        String username = principal.getName();
        DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy: hh:mm a");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("returnedDate",dateString);
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
        model.addAttribute("exeat", exeatService.findExeatById(exeatID) );

        return "card";
    }
//@GetMapping("/search")
//public  String Search(@RequestParam("masterid")int masterid,@RequestParam("keyword") String keyword,Master master,Model model){
//model.addAttribute("students",studentService.findByStudentNameContainingIgnoreCase(masterid,keyword));
//    return "StudentTable";
//}
    @GetMapping("/admin/StudentTable")
    public String StudentTable(Model model, Principal principal, Master master) {



        String username = principal.getName();


        DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy: hh:mm a");
        String dateString = dateFormat.format(new Date());

        model.addAttribute("returnedDate",dateString);
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
//        model.addAttribute("students", studentService.findMasterStudentsMatchesHouse(master.getMasterID()));


        return "StudentTable";
    }
    @PostMapping("/admin/IssueExeat")
    public String IssueExeat (@ModelAttribute("exeat") Exeat exeat, Student student, Model model,
                              @RequestParam("dateToReturn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateToReturn) throws IOException {
//        change dateToReturn to dateTime instead of String before submitting
exeat.setDateToReturn(dateToReturn);

        Exeat ExeatAlreadyExist = exeatService.findExeatBygeneratedCode(exeat.getGeneratedCode());
    //Calculating dateOUt and comparing to Date Return to ensure students are given at least 30 Minutes
        String dateOut = (exeat.getDateOUT());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy: hh:mm a");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime date1 = LocalDateTime.parse(dateOut, formatter);

        Duration duration = Duration.between(date1, dateToReturn);
        long Minutes = duration.toMinutes();
        System.out.println("Duration " + duration);
        System.out.println("Minutes " + Minutes);
        System.out.println("dateOut" +date1);

//        Checking if Student reported the last time Exeat was issued by checking if DateReturned column is null or not
        String CheckLastReturn = exeatService.CheckLastIssue(exeat.getStudentNumber());
        System.out.println("Last Return" +CheckLastReturn);



//Counting the number of times Exeats has been issued to a particular Student. Includes Emergency Exeats
        long Invalid = exeatService.findExeatByStudentNumber(exeat.getStudentNumber());

        if (Minutes <30) {
            model.addAttribute("TimeIssuse", "Duration should be 30 minutes or more!! , Kindly select a valid date or time");

            return "Card2";

        } else if (Minutes >= 30 && ExeatAlreadyExist != null) {
            model.addAttribute("msg3", "Exeat already issued");
            exeatService.SendSms(exeat);
            return "redirect:/admin/MasterDashBoard";


        } else if (Minutes >= 30  && ExeatAlreadyExist == null && Invalid >= 3) {
            model.addAttribute("ExeatsLimit", "You can Issue Exeat to a Student three times per Semester");
            return "redirect:/admin/MasterDashBoard";

//return Date is Valid,Exeat has not been issued already, Student hasn't recieved any exeat, Since student has no past exeat records last return will return nulll ;
        }
        else if (Minutes >= 30 && ExeatAlreadyExist == null && Invalid==0 && CheckLastReturn == null) {
            exeatService.IssueExeat(exeat);
//
            return "redirect:/admin/MasterDashBoard";
        }
        // try block to catch error when there is null for calculating days.
            try {
                LocalDate date2 = LocalDate.parse(CheckLastReturn, formatter);
                long Days = ChronoUnit.DAYS.between(date2, date1);
                System.out.println(Days);


                //return Date is Valid,, Student hasn't received 3 (Can be 1 or 2 received we have taken care of zero)
                // Exeat has not been issued already but Returned from an Exeat in the past 24 hours  ;

                if (Minutes >= 30 &&  0<Invalid &&Invalid<=2 && Days<=1 && ExeatAlreadyExist == null) {
                    model.addAttribute("Last24Hours", "Exeat has been issued to this Student in the last 24 hours");
                    return "Card2";

                    //return Date is Valid, Student hasn't received 3 (Can be 1 or 2 received we have taken care of zero)
                    // Exeat has not been issued already , Returned from an Exeat more than a day ago ;
                } else if (Minutes >= 30  && 0<Invalid &&Invalid<=2 && Days>1  && ExeatAlreadyExist == null) {
                    exeatService.IssueExeat(exeat);


                }



            } catch (NullPointerException e) {
// if alll Conditions are valid but CheckLastReturn is null
                System.out.println(CheckLastReturn);
                model.addAttribute("FailedToReport", "Student failed to report on the recently Issued Exeat ");
                return "Card2";
            }
        return "redirect:/admin/MasterDashBoard";
    }
    @RequestMapping  ("/admin/updateExeat/{exeatid}")

    public String updateNewExeat(@PathVariable int exeatid,@ModelAttribute("Exeat") Exeat exeatRequest) throws IOException {

   exeatService.updateExeat(exeatid, exeatRequest);
        return "redirect:/admin/MasterDashBoard";

    }
    @PostMapping("/admin/IssueEmergencyExeat")
    public String IssueEmergencyExeat (@ModelAttribute("exeat") Exeat exeat, Student student, Model model) throws IOException {
        Exeat ExeatAlreadyExist = exeatService.findExeatBygeneratedCode(exeat.getGeneratedCode());
        long Invalid = exeatService.findExeatByStudentNumber(exeat.getStudentNumber());
        String CheckLastIssue = exeatService.CheckLastIssue(exeat.getStudentNumber());
        String dateOut = exeat.getDateOUT();
        LocalDateTime dateToReturn= (exeat.getDateToReturn());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy: hh:mm a");

        LocalDateTime date1 = LocalDateTime.parse(dateOut, formatter);

        Duration duration = Duration.between(date1, dateToReturn);
        long Minutes = duration.toMinutes();
        System.out.println("Duration " + duration);
        System.out.println("Minutes " + Minutes);
            System.out.println("invalid" +Invalid);
            // if we mistakenly select a date that's in the past or time given is less than 30 minutes
        if (Minutes<30){
            model.addAttribute("msg4", "Duration should be 30 minutes or more!! , Kindly select a valid date or time");
            return "redirect:/admin/MasterDashBoard";

        } else if (Minutes>=30 && ExeatAlreadyExist != null) {
            model.addAttribute("msg", "Exeat already issued");
            exeatService.SendSms(exeat);
            return "redirect:/admin/MasterDashBoard";

        } else if (Minutes>=30 && Invalid >= 6 && ExeatAlreadyExist == null && CheckLastIssue!=null) {
            model.addAttribute("msg", "Emergency Exeats can be issued only three times");
            return "redirect:/admin/MasterDashBoard";
        } else if (Minutes>=0 && Invalid < 6 && ExeatAlreadyExist == null&&CheckLastIssue==null) {
            model.addAttribute("msg", "Student failed to report on the recently Issued Exeat ");
            return "redirect:/admin/MasterDashBoard";
        } else if (Minutes>=30 && Invalid < 6 && ExeatAlreadyExist == null&&CheckLastIssue!=null) {
            exeatService.IssueExeat(exeat);

        }


        return "redirect:/admin/MasterDashBoard";
        }


    @GetMapping("/admin/ShowEmergencyExeatCard/{studentNumber}")
    public String ShowEmergencyExeatCard(@PathVariable String studentNumber, Model model, Principal principal) {
        String username = principal.getName();
        DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy: hh:mm a");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("dateOUT",dateString);
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
        model.addAttribute("student", studentService.findByStudentNumber(studentNumber) );
        model.addAttribute("studentHouse", studentService.findStudentHouse(studentNumber) );
        System.out.println(studentService.findStudentHouse(studentNumber));


        return "card3";
    }
    @GetMapping("/admin/ExeatDetail/{generatedCode}")
    public String ExeatDetails(@PathVariable String generatedCode , Model model){
Exeat Exeat = exeatService.findExeatBygeneratedCode(generatedCode);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy: hh:mm a");
String formattedDateToReturn = (Exeat.getDateToReturn().format(formatter));
        model.addAttribute("Exeat",Exeat);
        model.addAttribute("formattedDateToReturn",formattedDateToReturn);
        System.out.println(exeatService.findExeatBygeneratedCode(generatedCode));
        System.out.println(formattedDateToReturn);
        return "ExeatDetailCard";
    }

}

