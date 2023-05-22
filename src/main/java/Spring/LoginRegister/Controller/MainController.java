///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Spring.LoginRegister.Controller;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import jakarta.servlet.ServletRequest;
//
//import Spring.LoginRegister.excel.ExcelService;
//import Spring.LoginRegister.Master.MasterServiceImpl;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//
///**
// *
// * @author siRe
// */
//
//
//public class MainController {
//
//    @Autowired
//    ExcelService service;

//    //method handler
//    @GetMapping("/")
//    public String showIndexPage() {
//        return "index";
//    }
//
//    @GetMapping("/admin/login")
//    public String showAdminLoginPage() {
//        return "adminlogin";
//    }
//
//    @GetMapping("/admindashboard")
//    public String showAdminDashboard() {
//        return "admindashboard";
//    }
//
//    @GetMapping("/master/login")
//    public String showMasterLoginpage() {
//        return "masterlogin";
//    }
//
//    @GetMapping("/master/home")
//    public String showMasterHomepage() {
//
//        return "home";
//    }
//
//    @GetMapping("/contact")
//    public String showContact() {
//
//        return "number";
//    }
//
//    @GetMapping("/sendSMS")
//    public String sendSMS(@RequestParam("code") String code, @RequestParam("contact") String contact) {
//
//        System.out.println("code:" + code);
//        System.out.println("contact:" + contact);
//
//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//        Request request = new Request.Builder()
//                .get()
//                .url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=eEJkSHptdGtTSmlDeFpzWk5hYU4&to=" + contact + "&from=SidGidTech&sms=Hello dear guadian!,be informed that your ward just requested an exeat from stjerome shs.Your exeat token is:" + code + "")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//
//            if (!response.isSuccessful()) {
//                return "redirect:/master/home?fail";
//
////                throw new IOException("Unexpected code " + response);
//            }
//
//            System.out.println(response.body().string());
//            return "redirect:/master/home?sent";
//
//        } catch (IOException ex) {
//            Logger.getLogger(MasterServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            return "redirect:/master/home?fail";
//
//        }
//
//        //service.sendSMS();
//    }
//
//    @GetMapping("/getContact")
//    public String getContact(Model model, @RequestParam(value = "StudentName") String studentName) {
//        List<Object[]> contact = service.getParentContact(studentName);
//
//        System.out.println("contact:"+Arrays.toString(contact.get(0)));
//        model.addAttribute("parentContact", Arrays.toString(contact.get(0)));
//        System.out.println("parentContact:" + model.getAttribute("parentContact"));
//
//        return "redirect:/contact";
//    }
//
//}
