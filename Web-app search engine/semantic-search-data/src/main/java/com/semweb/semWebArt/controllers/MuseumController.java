//package com.semweb.semWebArt.controllers;
//
//import com.semweb.semWebArt.model.Museums;
//import com.semweb.semWebArt.services.MuseumService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class MuseumController {
//    @Autowired
//    MuseumService museumService;
//    @GetMapping("/museum")
//    public String museumQuery(Model model){
//        List <Museums> museums = museumService.getMuseumelsList();
//            model.addAttribute("museums",museums);
//        return "Museums";
//    }
//}
