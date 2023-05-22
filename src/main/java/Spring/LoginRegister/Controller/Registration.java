///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Spring.LoginRegister.Controller;
//
//import Spring.LoginRegister.House.HouseService;
//import Spring.LoginRegister.Master.MasterServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//
///**
// *
// * @author siRe
// */
//
//public class Registration {
//
//    @Autowired
//    HouseService houseService;
//    MasterServiceImpl service;
//
//    @Autowired
//    public Registration(MasterServiceImpl service) {
//        this.service = service;
//    }
//
//    @ModelAttribute("masterDetails")
//    public MasterDto register() {
//        return new MasterDto();
//
//    }
//
//    @PostMapping("/masterregister")
//    public String registerMaster(@ModelAttribute("masterDetails") MasterDto details) {
//
//        service.registerMaster(details);
//        return "redirect:/?success";
//    }
//
//    @GetMapping("/master/register")
//    public String showMasterReg(Model model) {
//
////        model.addAttribute("houses", houseService.getAllHouses());
//        return "masterregister";
//    }
//
//}
