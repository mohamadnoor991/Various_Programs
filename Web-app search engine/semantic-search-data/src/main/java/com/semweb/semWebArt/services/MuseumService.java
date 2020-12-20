package com.semweb.semWebArt.services;

import com.bordercloud.sparql.SparqlClient;
import com.bordercloud.sparql.SparqlClientException;
import com.bordercloud.sparql.SparqlResult;
import com.bordercloud.sparql.SparqlResultModel;
import com.semweb.semWebArt.model.Museums;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MuseumService {


    private List<Museums> museumelsList = new ArrayList<>();

    public List<Museums> getMuseumelsList() {
        return museumelsList;
    }

    @PostConstruct
    @Scheduled(cron = "1 * * * * *")
            public void museumeQuery() throws URISyntaxException, SparqlClientException {
        List <Museums> updateMuseumList = new ArrayList<>();


        URI endpoint = new URI("https://query.wikidata.org/sparql");

    String queryResult="SELECT DISTINCT ?museumLabel ?museumDescription ?villeId ?villeIdLabel (?villeIdLabel AS ?ville) ?coord ?lat ?lon\n" +
            "WHERE\n" +
            "{\n" +
            "  ?museum wdt:P539 ?museofile.  # french museofile Id\n" +
            "  ?museum wdt:P131* wd:Q142. # in Brittany\n" +
            "  ?museum wdt:P131 ?villeId. #city of the museum\n" +
            "  # ?object wdt:P166 wd:Q2275045 # that have french label \"musées de France\"\n" +
            "  OPTIONAL {\n" +
            "    ?museum p:P625 ?statement.\n" +
            "    ?statement psv:P625 ?node.\n" +
            "    ?node wikibase:geoLatitude ?lat.\n" +
            "    ?node wikibase:geoLongitude ?lon.\n" +
            "   }\n" +
            "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"fr\". } #french label\n" +
            "}limit 25\n";

        SparqlClient sc = new SparqlClient(false);
        sc.setEndpointRead(endpoint);
        SparqlResult sr = sc.query(queryResult);

        SparqlResultModel resultforQiki = sr.getModel();

        for (HashMap row : resultforQiki.getRows()) {
                Museums museme=new Museums();
            for (String variable : resultforQiki.getVariables()) {

                museme.setMuseumLabel((String) row.get("museumLabel"));
                museme.setMuseumDescription((String) row.get("museumDescription"));
                museme.setVilleIdLabel((String) row.get("villeIdLabel"));
                museme.setVille((String) row.get("ville"));
                museme.setLatN((String) row.get("lat"));
                double latU = Double.parseDouble((String) row.get("lat"));//under testing
                museme.setLat(latU);//underTesting
                museme.setLonN((String) row.get("lon"));

                double lonU = Double.parseDouble((String) row.get("lon"));
                museme.setLon(lonU);

            }
            updateMuseumList.add(museme);
        }
        this.museumelsList = updateMuseumList;
}}
