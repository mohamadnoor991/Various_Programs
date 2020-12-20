package com.semweb.semWebArt.controllers;

import com.semweb.semWebArt.model.BikeStation;
import com.semweb.semWebArt.model.Hospital;
import com.semweb.semWebArt.model.HospitalWiki;
import com.semweb.semWebArt.model.Museums;
import com.semweb.semWebArt.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class GeneralControoler {

    @Autowired
    HospitalServices hospitalServices;

    @Autowired
    MuseumService museumService;

    @Autowired
    StEtienneBikeStationService stEtienneBikeStationService;

    @Autowired
    LyonBikeStationService lyonBikeStationService;

    @Autowired
    HospitalServicesW hospitalServicesW;

    @RequestMapping(method = RequestMethod.GET, value = "/Local-Hopital-Data")
    public String index(Model model) {

        List<Hospital> hospitals = hospitalServices.getHospitalsList();
        model.addAttribute("hospitals", hospitals);
        return "hospitals";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/StEtienne-bike-staion")
    public String StBikeStation(Model model) {

        List<BikeStation> StStations = stEtienneBikeStationService.getBikeStationList();
//        System.out.println(Stations);
        model.addAttribute("bikeStations", StStations);

        return "bikeStation";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/Lyon-bike-station")
    public String lybikeStation(Model model) {

        List<BikeStation> LyStations = lyonBikeStationService.getLyonBikeStationList();
        System.out.println(LyStations);
        model.addAttribute("LybikeStations", LyStations);

        return "lyBikeStation";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/wikihospitals")
    public String moveToWikiHospital(Model model) {

        List<HospitalWiki> hospitals = hospitalServicesW.getHospitalsList();
        model.addAttribute("hospitals", hospitals);

        return "hospitalsWiki";
    }

    @GetMapping("/museum")
    public String museumQuery(Model model){
        List <Museums> museums = museumService.getMuseumelsList();
        model.addAttribute("museums",museums);
        return "Museums";
    }






}
