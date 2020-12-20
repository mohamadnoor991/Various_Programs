package com.semweb.semWebArt.services;

import com.bordercloud.sparql.SparqlClient;
import com.bordercloud.sparql.SparqlClientException;
import com.bordercloud.sparql.SparqlResult;
import com.bordercloud.sparql.SparqlResultModel;
import com.semweb.semWebArt.model.HospitalWiki;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HospitalServicesW {
    private List<HospitalWiki> hospitalsList = new ArrayList<>();

    public List<HospitalWiki> getHospitalsList() {
        return hospitalsList;
    }

    @PostConstruct
    @Scheduled(cron = "1 * * * * *")
    public void queryData() throws IOException, URISyntaxException, SparqlClientException {
        List<HospitalWiki> updateHospitalsList = new ArrayList<>();

        URI endpoint = new URI("https://query.wikidata.org/sparql");
        String querySelect = "#Map of hospitals\n" +
                "#added 2017-08\n" +
                "#defaultView:Map\n" +
                "SELECT distinct ?NameLabel ?Version ?countryLabel ?GeoLocation ?LastDataUpdated ?HospitalTimeStamp  ?ImageHospital  ?InstanceOfLabel\n" +
                "             ?CityNameLabel ?Co_CategoryLabel  ?StreetLabel  ?MerimeeId ?UnviersalHistoryLabel ?LabelOfHospital \n" +
                "WHERE {\n" +
                "  ?Name wdt:P31/wdt:P279* wd:Q16917;   \n" +
                "           wdt:P17 wd:Q142;  schema:version ?Version; wdt:P17 ?country ; wdt:P625 ?GeoLocation; schema:dateModified ?LastDataUpdated ;\n" +
                "      wikibase:timestamp ?HospitalTimeStamp;  wdt:P18 ?ImageHospital;   wdt:P31 ?InstanceOf;  wdt:P131 ?CityName; \n" +
                "      wdt:P373 ?Co_Category;  wdt:P669 ?Street;  wdt:P380 ?MerimeeId; wdt:P1435   ?UnviersalHistory;rdfs:label ?LabelOfHospital filter (lang(?LabelOfHospital ) = \"en\").\n" +
                "    SERVICE wikibase:label {\n" +
                "    bd:serviceParam wikibase:language \"en\" .\n" +
                "   } \n" +
                "  } ";


        SparqlClient sc = new SparqlClient(false);
        sc.setEndpointRead(endpoint);
        SparqlResult sr = sc.query(querySelect);
//        SparqlResultModel rs=sr.getModel();

        /******************************/
        SparqlResultModel resultforQiki = sr.getModel();//added new
//
//        HashMap<String, Object> resultSetWiki =sr.resultHashMap ;


        for (HashMap row : resultforQiki.getRows()) {
            HospitalWiki hospital = new HospitalWiki();
            for (String variable : resultforQiki.getVariables()) {

                hospital.setNameLabel((String) row.get("NameLabel"));
                hospital.setVersion((String) row.get("Version"));
                hospital.setCountryLabel((String) row.get("countryLabel"));
                hospital.setGeoLocation((String) row.get("GeoLocation"));
                hospital.setLastDataUpdated((String) row.get("LastDataUpdated"));
                hospital.setHospitalTimeStamp((String) row.get("HospitalTimeStamp"));
//                hospital.setImageHospital((Image) row.get("ImageHospital"));
                hospital.setInstanceOfLabel((String) row.get("InstanceOfLabel"));
                hospital.setCityNameLabel((String) row.get("CityNameLabel"));
                hospital.setCo_CategoryLabel((String) row.get("Co_CategoryLabel"));
                hospital.setStreetLabel((String) row.get("StreetLabel"));
                hospital.setMerimeeId((String) row.get("MerimeeId"));
                hospital.setUnviersalHistoryLabel((String) row.get("UnviersalHistoryLabel"));
                hospital.setLabelOfHospital((String) row.get("LabelOfHospital"));





            }
            updateHospitalsList.add(hospital);
            // Add the result to the main pool.
        }
        this.hospitalsList = updateHospitalsList;
    }
}
        /******************************************/


