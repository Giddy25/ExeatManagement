package Spring.LoginRegister.Controller;

import Spring.LoginRegister.Admin.AdminRepository;
import Spring.LoginRegister.Admin.AdminService;

import Spring.LoginRegister.Announcement.AnnounceService;
import Spring.LoginRegister.Announcement.AnnouncementRepository;
import Spring.LoginRegister.DutyRoster.dutyRosterService;
import Spring.LoginRegister.Entity.*;
import Spring.LoginRegister.Exeats.ExeatRepository;
import Spring.LoginRegister.Exeats.ExeatService;
import Spring.LoginRegister.House.HouseService;
import Spring.LoginRegister.Master.*;
//import Spring.LoginRegister.Master.SemesterService;
import Spring.LoginRegister.Student.StudentRepository;
import Spring.LoginRegister.Student.StudentService;
import Spring.LoginRegister.Teacher.TeacherRepository;
import Spring.LoginRegister.Teacher.TeacherService;


import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

@RequiredArgsConstructor
@AllArgsConstructor
public class MasterController {
    @Autowired
    SemesterService switchService;
    @Autowired
    GroupGeneratorService groupGeneratorService;
    @Autowired
    StudentService service;
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    AdminRepository adminRepository;
//    @Autowired
//    ExcelService service;

    @Autowired
    MasterRepository masterRepository;
    @Autowired
    TeacherService teacherService;
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExeatRepository exeatRepository;


    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private ExeatService exeatService;
@Autowired
    dutyRosterService dutyRosterService;

    @Autowired
    private MasterService masterservice;
    @Autowired
AnnounceService announceService;

    @ModelAttribute
    private void MasterDetails(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            Master masterDetails = masterRepository.findMasterByEmail(username);
            model.addAttribute("master", masterDetails);
        }
    }

    @GetMapping("/master/login")
    public String showMastersLoginpage(LoginForm form, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("status", "Ivalid");
            return "masterlogin";
        }

        return "masterlogin";
    }
    @GetMapping("/Master/StudentRegister")
    public String RegisterStudents() {

        return "home";
    }

//    @GetMapping("/Master/StudentRegister")
//    public String showStudentRegistrationpage() {
//
//        return "home";
//    }
    @GetMapping("/master/MasterDashBoard")
    public String MastersDashBoard(@RequestParam(required = false,defaultValue = "B") String newValue, @RequestParam(defaultValue = "0")int page, Principal principal, Model model, Exeat exeat) throws IOException {
//       this is just to make the exeat table always updated
        newValue = RandomStringUtils.randomAlphabetic(4);
        exeatService.updateColumn(newValue);
        System.out.println(newValue);


        Page<Exeat> exeats= exeatService.findSortedPage(page,10);

//delete any images that do not match any student
        studentService.deleteUnlinkedImages();

        String username = principal.getName();
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
        model.addAttribute("exeats",exeats );
        model.addAttribute("currentPage",page );

        model.addAttribute("countUsers", masterRepository.count());
        return "masterDash";
    }
    @GetMapping("/m/StudentRegister")
    public String MastershowStudentsRegistrationpage() {

        return "StudentRegister";
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
    @GetMapping("/master/ShowExeatCard/{studentNumber}")
    public String MasterShowExeatCard(@PathVariable String studentNumber, Model model, Principal principal) {
        String username = principal.getName();
        DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy: hh:mm a");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("dateOUT",dateString);
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
        model.addAttribute("student", studentService.findByStudentNumber(studentNumber) );
        System.out.println(studentService.findByStudentNumber(studentNumber));
        model.addAttribute("studentHouse", studentService.findStudentHouse(studentNumber) );
        System.out.println(studentService.findStudentHouse(studentNumber));


        return "card2";
    }


    @GetMapping("/master/AlreadyIssuedExeat/{exeatID}/{studentNumber}")
    public String MasterAlreadyIssuedExeatCard(@PathVariable int exeatID,@PathVariable String studentNumber,Exeat exeat,Model model, Principal principal) {
        String username = principal.getName();

        DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy: hh:mm a");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("returnedDate",dateString);
        model.addAttribute("student", studentService.findByStudentNumber(studentNumber) );
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
        model.addAttribute("exeat", exeatService.findExeatById(exeatID) );

        return "card";
    }
    @GetMapping("/master/search")
    public  String Search(@RequestParam("masterid")int masterid,@RequestParam("keyword") String keyword,Master master,Model model){
        model.addAttribute("studentHouse", studentRepository.findStudentHouseByMaster(masterid) );
        model.addAttribute("students",studentService.findByStudentNameContainingIgnoreCase(masterid,keyword));
        System.out.println("studentRepository.findStudentHouseByMaster(masterid)");
        return "StudentTable";
    }
    @GetMapping("/master/searchStudentTable")
    public String MasterStudentTable(Model model, Principal principal, Master master) {



        String username = principal.getName();


        DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy: hh:mm a");
        String dateString = dateFormat.format(new Date());

        model.addAttribute("returnedDate",dateString);
        model.addAttribute("master", masterRepository.findMasterByEmail(username));
//        model.addAttribute("students", studentService.findMasterStudentsMatchesHouse(master.getMasterID()));


        return "SearchStudentsTable";
    }
    @PostMapping("/master/IssueExeat")
    public String MasterIssueExeat (@ModelAttribute("exeat") Exeat exeat, Student student, Model model,
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
            return "redirect:/master/MasterDashBoard";


        } else if (Minutes >= 30  && ExeatAlreadyExist == null && Invalid >= 3) {
            model.addAttribute("ExeatsLimit", "You can Issue Exeat to a Student three times per Semester");
            return "redirect:/master/MasterDashBoard";

//return Date is Valid,Exeat has not been issued already, Student hasn't recieved any exeat, Since student has no past exeat records last return will return nulll ;
        }
        else if (Minutes >= 30 && ExeatAlreadyExist == null && Invalid==0 && CheckLastReturn == null) {
            exeatService.IssueExeat(exeat);
//
            return "redirect:/master/MasterDashBoard";
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
        return "redirect:/master/MasterDashBoard";
    }
    @RequestMapping  ("/master/updateExeat/{exeatid}")

    public String MasterupdateNewExeat(@PathVariable int exeatid,@ModelAttribute("Exeat") Exeat exeatRequest) throws IOException {

        exeatService.updateExeat(exeatid, exeatRequest);
        return "redirect:/master/MasterDashBoard";

    }
    @PostMapping("/master/IssueEmergencyExeat")
    public String MasterIssueEmergencyExeat (@ModelAttribute("exeat") Exeat exeat, Student student, Model model) throws IOException {
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
            return "redirect:/master/MasterDashBoard";

        } else if (Minutes>=30 && ExeatAlreadyExist != null) {
            model.addAttribute("msg", "Exeat already issued");
            exeatService.SendSms(exeat);
            return "redirect:/master/MasterDashBoard";

        } else if (Minutes>=30 && Invalid >= 6 && ExeatAlreadyExist == null && CheckLastIssue!=null) {
            model.addAttribute("msg", "Emergency Exeats can be issued only three times");
            return "redirect:/master/MasterDashBoard";
        } else if (Minutes>=0 && Invalid < 6 && ExeatAlreadyExist == null&&CheckLastIssue==null) {
            model.addAttribute("msg", "Student failed to report on the recently Issued Exeat ");
            return "redirect:/master/MasterDashBoard";
        } else if (Minutes>=30 && Invalid < 6 && ExeatAlreadyExist == null&&CheckLastIssue!=null) {
            exeatService.IssueExeat(exeat);

        }


        return "redirect:/master/MasterDashBoard";
    }


    @GetMapping("/master/ShowEmergencyExeatCard/{studentNumber}")
    public String MasterShowEmergencyExeatCard(@PathVariable String studentNumber, Model model, Principal principal) {
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
    @GetMapping("/master/ExeatDetail/{generatedCode}/{studentNumber}")
    public String MasterExeatDetails(@PathVariable String generatedCode ,@PathVariable String studentNumber, Model model){
        Exeat Exeat = exeatService.findExeatBygeneratedCode(generatedCode);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy: hh:mm a");
        String formattedDateToReturn = (Exeat.getDateToReturn().format(formatter));
        model.addAttribute("Exeat",Exeat);
        model.addAttribute("formattedDateToReturn",formattedDateToReturn);
        model.addAttribute("student", studentService.findByStudentNumber(studentNumber) );
        System.out.println(exeatService.findExeatBygeneratedCode(generatedCode));
        System.out.println(formattedDateToReturn);
        return "ExeatDetailCard";
    }

@GetMapping("/master/switchSemester")
public void switchSemester(){


    switchService.changeSemester();

}
    @GetMapping("/master/UpdateExeatForm/{studentNumber}")
    public String ShowCardUpdate(@PathVariable String studentNumber, Model model, Principal principal) {

        model.addAttribute("student", studentService.findByStudentNumber(studentNumber) );
        model.addAttribute("studentHouse", studentService.findStudentHouse(studentNumber) );
        model.addAttribute("houses", houseService.getAllHouses());

        System.out.println(studentService.findStudentHouse(studentNumber));

        return "UpdateCard";

}
    @GetMapping("/master/UpdateFullExeatForm/{studentID}")
    public String ShowFullCardUpdate(@PathVariable int studentID, Model model, Principal principal) throws IOException {
//delete any images that do not match any student
        studentService.deleteUnlinkedImages();
        model.addAttribute("student", studentService.getStudentById(studentID) );
        model.addAttribute("studentHouse", studentService.findStudentHouseBYID(studentID) );
        model.addAttribute("houses", houseService.getAllHouses());

        System.out.println(studentService.findStudentHouseBYID(studentID));

        return "UpdateCard";

    }
    @GetMapping("/phones")
    public List<String> getPhoneNumbersByStatus(@RequestParam(name = "Studentclass") String Studentclass) {
        List<Student> students;
        if (Studentclass.equalsIgnoreCase("all")) {
            students = studentRepository.findAll();
        } else {
            students = studentRepository.findByStudentClassStartingWith(Studentclass);
            System.out.println(students.stream().map(Student::getParentContact).collect(Collectors.toList()));
        }
        return students.stream().map(Student::getParentContact).collect(Collectors.toList());

    }
    @PostMapping("/master/UploadStudentPicOnIssue/{studentNumber}")
    public String updateStudentImageOnIssue(@PathVariable("studentNumber") String studentNumber,
                                @RequestParam("image-upload") MultipartFile image, RedirectAttributes redirectAttributes) throws IOException {
    studentService.updateImage(studentNumber, image);
//    redirectAttributes.addAttribute("studentID",studentID);
        Student student = studentService.findByStudentNumber(studentNumber);

    return "redirect:/master/MasterDashBoard";
    }
    @PostMapping("/master/UploadStudentPicOnReturn/{studentNumber}")
    public String updateStudentImageOnReturn(@PathVariable("studentNumber") String studentNumber,
                                  @RequestParam("image-upload") MultipartFile image, RedirectAttributes redirectAttributes) throws IOException {
        studentService.updateImage(studentNumber, image);
//    redirectAttributes.addAttribute("studentID",studentID);
        Student student = studentService.findByStudentNumber(studentNumber);

        return "redirect:/master/MasterDashBoard";
    }

    @PostMapping("/master/FullStudentUpdate/{studentID}")
    public String updateStudentFullDetails(@PathVariable("studentID") int studentID,
                                             @RequestParam(value = "image-upload",required = false) MultipartFile image,@ModelAttribute("student") Student student,String studentNumber) throws IOException {

        studentService.FullStudentInfo(studentID,student);

        studentService.updateImageFull(studentID,image);


//    redirectAttributes.addAttribute("studentID",studentID);
//        student = studentService.findByStudentNumber(studentID);

        return "redirect:/master/MasterDashBoard";
    }
    @PostMapping("/master/bulk")
    public String sendBulkSMS(@ModelAttribute("Announcement") Announcement announcement,@RequestParam(name = "message") String msg,@RequestParam(name = "Studentclass") String Studentclass) throws IOException {
        List<String> phoneNumbers = getPhoneNumbersByStatus(Studentclass);
if(msg!=null && phoneNumbers!=null){


    announceService.saveAnnouncement(announcement);
            System.out.println(phoneNumbers);
//            Object[] listOfContactsFromDb = service.getContacts().toArray();
//            System.out.println("contacts:" + listOfContactsFromDb);
//      List<String> l= Arrays.asList((String[]) listOfContactsFromDb) ;


            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("api-key", "eEJkSHptdGtTSmlDeFpzWk5hYU4")
                                .addHeader("Content-Type", "application/json")
                                .build();

                        return chain.proceed(request);
                    })
                    .build();

//        List <String>listOfContactsFromDb = Arrays.asList("0247909835","0247909835");
            Map<String, Object> requestBody = new LinkedHashMap<>();
            requestBody.put("sender", "SIDGIDTECH");
            requestBody.put("message", msg);
            requestBody.put("recipients", phoneNumbers);
            System.out.println("rbody" + requestBody);
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<Map<String, Object>> jsonAdapter = moshi.adapter((Types.newParameterizedType(Map.class, String.class, Object.class)));

            String mapJsonAdapter = jsonAdapter.indent(" ").toJson(requestBody);
            System.out.println("json:" + mapJsonAdapter);
            RequestBody jsonRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mapJsonAdapter);
            String host = "https://sms.arkesel.com/api/v2/sms/send";

            Request request = new Request.Builder()
                    .url(host.trim())
                    .post(jsonRequestBody)
                    .build();
            System.out.println("request:" + request);

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                System.out.println("responsebody" + response.body().string());
            }

        }
        return "redirect:/master/MasterDashBoard?sent";
    }
        @GetMapping("/master/SMS_Management")

        public String showSMSManagement () {
            return "SMS_Management";
        }

        @GetMapping("/master/bulkSMS")
        public String textBulkSMS () {
            return "bulkText";
        }
    @GetMapping("/dutyrosterPage")
        public String ShowdutyrosterPage(){
        return "dutyroster";
    }


    @GetMapping("/dutyRoster")
    public String generateGroups(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {


        List<Teacher> teachers = teacherRepository.findAll();
        Collections.shuffle(teachers);
        int numWeeks = (int) ChronoUnit.WEEKS.between(
                startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
                endDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))) + 1;

        List<Group> weekGroups = new ArrayList<>();
        Group weekGroup = null; // declare weekGroup variable here

        int numTeachersPerGroup = teachers.size() / numWeeks;
        if (numTeachersPerGroup < 5) {
            numTeachersPerGroup = 5;
        }
        int numExtraTeachers = teachers.size() % numWeeks;

        int teacherIndex = 0;
        int i;
        List<Teacher> weekTeachers = null;
        for ( i = 1; i <= numWeeks; i++) {
            LocalDate weekStartDate = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusWeeks(i - 1);
            LocalDate weekEndDate = weekStartDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

            // Calculate the number of days left in the week
            int daysLeftInWeek = (int) ChronoUnit.DAYS.between(LocalDate.now(), weekEndDate) + 1;

            // Skip the week if there are less than 2 days left
            if (daysLeftInWeek < 2) {
                continue;
            }

            weekGroup = new Group(i, weekStartDate, weekEndDate); // initialize weekGroup here

            int numTeachersInGroup = numTeachersPerGroup;

            if (numExtraTeachers > 0) {
                numTeachersInGroup++;
                numExtraTeachers--;
            }

            weekTeachers = new ArrayList<>();
            Set<Teacher> usedTeachers = new HashSet<>(); // keep track of teachers already added
            Set<Teacher> lastWeekTeachers = new HashSet<>(); // keep track of teachers from last week

            // get teachers from last week, if available
            if (weekGroups.size() > 0) {
                Group lastWeekGroup = weekGroups.get(weekGroups.size() - 1);
                lastWeekTeachers.addAll(lastWeekGroup.getTeachers());
            }

            for (int j = 0; j < numTeachersInGroup; j++) {
                if (teacherIndex >= teachers.size()) {
                    teachers = teacherRepository.findAll();
                    Collections.shuffle(teachers);
                    teacherIndex = 0;
                }
                Teacher teacher = teachers.get(teacherIndex);
                if (!usedTeachers.contains(teacher) && !lastWeekTeachers.contains(teacher)) { // check if teacher has already been added or selected last week
                    weekTeachers.add(teacher);
                    usedTeachers.add(teacher); // add teacher to set of used teachers
                }
                teacherIndex++;
            }

            weekGroup.setTeachers(weekTeachers);
            weekGroups.add(weekGroup);

        }

        if (weekTeachers.size() < 5) {
            int numExtraTeachersToRedistribute = 5 - weekTeachers.size();
            int currentGroupIndex = weekGroups.size() - 1;

            // Redistribute the extra teachers to other groups
            for (int j = 0; j < weekGroups.size(); j++) {
                if (j != currentGroupIndex && weekGroups.get(j).getTeachers().size() >= 5) {
                    List<Teacher> otherGroupTeachers = weekGroups.get(j).getTeachers();
                    int numExtraTeachersToTake = Math.min(numExtraTeachersToRedistribute, otherGroupTeachers.size() - 5);

                    List<Teacher> extraTeachersToTake = otherGroupTeachers.subList(otherGroupTeachers.size() - numExtraTeachersToTake, otherGroupTeachers.size());
                    weekTeachers.addAll(extraTeachersToTake);
                    otherGroupTeachers.removeAll(extraTeachersToTake);
                    numExtraTeachersToRedistribute -= extraTeachersToTake.size();
                }

                if (numExtraTeachersToRedistribute == 0) {
                    break;
                }
            }

            // If there are still extra teachers to redistribute, start a fresh regrouping
            if (numExtraTeachersToRedistribute > 0) {
                teachers = teacherRepository.findAll();
                Collections.shuffle(teachers);
                numTeachersPerGroup = teachers.size() / numWeeks;
                if (numTeachersPerGroup < 5) {
                    numTeachersPerGroup = 5;
                }
                numExtraTeachers = teachers.size() % numWeeks;

                weekGroups.clear();
                teacherIndex = 0;
                i = 0;
            }
        }

        model.addAttribute("weekGroups", weekGroups);

        try {
            String filename = groupGeneratorService.generatePdf(weekGroups);
            model.addAttribute("pdfFilename", filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "dutyroster";
    }
    @GetMapping("/download-pdf")
    public ResponseEntity<ByteArrayResource> downloadPdf(@RequestParam String filename) throws IOException {
        // Read the PDF file
        InputStream inputStream = new FileInputStream(new File(filename));
        byte[] contents = IOUtils.toByteArray(inputStream);

        // Set the content type and length of the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentLength(contents.length);

        // Create a ByteArrayResource from the PDF file contents
        ByteArrayResource resource = new ByteArrayResource(contents);

        // Return a ResponseEntity with the ByteArrayResource and headers
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


    private List<List<Teacher>> getTeacherGroups(int groupIndex, List<List<Teacher>> teacherGroups) {
        List<List<Teacher>> groups = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            groups.add(teacherGroups.get((groupIndex + i) % teacherGroups.size()));
        }
        return groups;
    }






        @GetMapping("/TeacherRegisterForm")
        public String TeacherRegisterForm(){
        return "TeacherRegisterPage";
        }
    @PostMapping("/TeacherRegister")
    public String TeacherRegistration(@ModelAttribute("Teacher") Teacher teacher, Model model) {
        teacherService.Save(teacher);

        return "redirect:/TeacherRegisterForm";
    }
        }






