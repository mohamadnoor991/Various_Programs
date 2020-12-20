package com.semweb.semWebArt.services;

import com.semweb.semWebArt.model.BikeStation;
import com.semweb.semWebArt.repository.SaintEtienneDBUpdate;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionRemote;
import org.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StEtienneBikeStationService {
    private RDFConnection conneg = RDFConnectionRemote.create()
            .destination("http://localhost:3030/bikeStationDB")
            .queryEndpoint("sparql")
            .acceptHeaderSelectQuery("application/sparql-results+json, application/sparql-results+xml;q=0.9")
            .build();
    private String service = "http://localhost:3030/bikeStationDB";

    private List<BikeStation> bikeStationList = new ArrayList<>();

    public List<BikeStation> getBikeStationList() {

        return bikeStationList;
    }



    @PostConstruct
    @Scheduled(cron = "* * 2 * * *")
    public void queryBikeStationData() throws IOException, JSONException, InterruptedException {

        String url_1 = "https://saint-etienne-gbfs.klervi.net/gbfs/en/station_information.json";
        String url_2 = "https://saint-etienne-gbfs.klervi.net/gbfs/en/station_status.json";
        SaintEtienneDBUpdate saintEtienneDBUpdate = new SaintEtienneDBUpdate();
        conneg.delete();
        saintEtienneDBUpdate.paresStEtienne(url_1,url_2);

        List<BikeStation> updateBikeStationList = new ArrayList<>();

        // Define the prefixes of the knowledge base
        String prefixes = "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "prefix bike: <http://localhost:3030/Bik-Hos/>";

        // The query used to retrive our information using SPARQL
        Query query = QueryFactory.create(prefixes + "\n" + "select ?stationID ?stationName ?availableBike ?availableDock ?lat ?long ?capacity where {\n" +
                "     ?stationID a geo:SpatialThing;\n" +
                "     bike:stationName ?stationName;\n" +
                "     bike:capacity ?capacity;\n" +
                "     bike:availableBike ?availableBike;\n" +
                "     bike:availableDock ?availableDock;\n" +
                "     geo:lat ?lat;\n" +
                "     geo:long ?long.\n" +
                "}");
        QueryExecution queryExe = QueryExecutionFactory.sparqlService(service, query);

        try {
            ResultSet resultSet = queryExe.execSelect();
            while (resultSet.hasNext()) {
                BikeStation bikeStation = new BikeStation();
                QuerySolution data = resultSet.nextSolution();

                // retrive the values of the sparql query by the varaible of the query
                Resource stationId = data.getResource("stationID");
                RDFNode stationName = data.get("stationName");
                Literal capacity = data.getLiteral("capacity");
                Literal availableBike = data.getLiteral("availableBike");
                Literal availableDock = data.getLiteral("availableDock");
                Literal latitude = data.getLiteral("lat");
                Literal longitude = data.getLiteral("long");

                // set the data to the model of BikeStation to call it later on controller.
                bikeStation.setStationID(stationId.toString());
                bikeStation.setStationName(stationName.toString());
                bikeStation.setCapacity(capacity.getInt());
                bikeStation.setAvailableDock(availableDock.getInt());
                bikeStation.setAvalaibleBike(availableBike.getInt());
                bikeStation.setLatitude(latitude.getDouble());
                bikeStation.setLongitude(longitude.getDouble());

                updateBikeStationList.add(bikeStation);
            }
        }finally{
            queryExe.close();
        }
        this.bikeStationList    =   updateBikeStationList;
    }
}