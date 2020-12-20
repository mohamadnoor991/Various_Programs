package com.semweb.semWebArt.services;

import com.semweb.semWebArt.model.BikeStation;
import com.semweb.semWebArt.repository.LyonDBUpdate;
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
public class LyonBikeStationService {

    // At the begining, we call the function to pares the link then send the data to RDF store
//    String url_1 = "https://download.data.grandlyon.com/wfs/rdata?SERVICE=WFS&VERSION=1.1.0&outputformat=GEOJSON&request=GetFeature&typename=jcd_jcdecaux.jcdvelov&SRSNAME=urn:ogc:def:crs:EPSG::4171";
//    LyonDBUpdate update = new LyonDBUpdate();


    private String DBName = "lyBike";
    private RDFConnection conneg = RDFConnectionRemote.create()
            .destination("http://localhost:3030/"+DBName)
            .queryEndpoint("sparql")
            .acceptHeaderSelectQuery("application/sparql-results+json, application/sparql-results+xml;q=0.9")
            .build();
    private String service = "http://localhost:3030/"+DBName;

    private List<BikeStation> lyonBikeStationList = new ArrayList<>();



    public List<BikeStation> getLyonBikeStationList() {
        return lyonBikeStationList;
    }

    @PostConstruct
    @Scheduled(cron = "* 1 * * * *")
    public void queryBikeStationData() throws IOException, JSONException, InterruptedException {
        String url_1 = "https://download.data.grandlyon.com/wfs/rdata?SERVICE=WFS&VERSION=1.1.0&outputformat=GEOJSON&request=GetFeature&typename=jcd_jcdecaux.jcdvelov&SRSNAME=urn:ogc:def:crs:EPSG::4171";
        LyonDBUpdate lyonDBUpdate = new LyonDBUpdate();
        conneg.delete();
        lyonDBUpdate.paresLyon(url_1);

        List<BikeStation> updateBikeStationList = new ArrayList<>();

        // Define the prefixes of the knowledge base
        String prefixes = "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "prefix bike: <http://localhost:3030/Bik-Hos/>";

        // The query used to retrive our information using SPARQL
        Query query = QueryFactory.create(prefixes + "\n" + "select ?stationID ?status ?commun ?address ?stationName ?availableBike ?availableDock ?lat ?long ?capacity where {\n" +
                "     ?stationID a geo:SpatialThing;\n" +
                "     bike:stationName ?stationName;\n" +
                "     bike:capacity ?capacity;\n" +
                "     bike:availableBike ?availableBike;\n" +
                "     bike:availableDock ?availableDock;\n" +
                "     bike:hasAddress ?address;\n" +
                "     bike:locatedInCommune ?commun;\n" +
                "     bike:hasStatus ?status;\n" +
                "     geo:lat ?lat;\n" +
                "     geo:lon ?long.\n" +
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
                //lyon Extra data
                Literal status          =   data.getLiteral("status");
                Literal commun          =   data.getLiteral("commun");
                Literal address          =   data.getLiteral("address");

                // set the data to the model of BikeStation to call it later on controller.
                bikeStation.setStationID(stationId.toString());
                bikeStation.setStationName(stationName.toString());
                bikeStation.setCapacity(capacity.getInt());
                bikeStation.setAvailableDock(availableDock.getInt());
                bikeStation.setAvalaibleBike(availableBike.getInt());
                bikeStation.setLatitude(latitude.getDouble());
                bikeStation.setLongitude(longitude.getDouble());

                // Lyon extra data
                bikeStation.setCommune(commun.toString());
                bikeStation.setStatus(status.toString());
                bikeStation.setStationAddress(address.toString());

//                System.out.println(bikeStation.toString());

                updateBikeStationList.add(bikeStation);
            }
        }finally{
            queryExe.close();
//
        }
        this.lyonBikeStationList   =   updateBikeStationList;
    }
}
