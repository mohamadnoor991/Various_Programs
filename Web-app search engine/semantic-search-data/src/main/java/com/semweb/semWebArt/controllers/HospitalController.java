//package com.semweb.semWebArt.controllers;
//
////import com.example0.SW.Services.HospitalServices;
//
//import com.semweb.semWebArt.model.HospitalWiki;
//import com.semweb.semWebArt.services.HospitalServicesW;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.List;
//
//@Controller
//public class HospitalController {
//
//    @Autowired
//    HospitalServicesW hospitalServicesW;
//
//    @RequestMapping(method = RequestMethod.GET, value = "/wikihospitals")
//    public String moveToWikiHospital(Model model) {
//
//        List<HospitalWiki> hospitals = hospitalServicesW.getHospitalsList();
//        model.addAttribute("hospitals", hospitals);
//
//        return "hospitalsWiki";
//    }
//
//
//}