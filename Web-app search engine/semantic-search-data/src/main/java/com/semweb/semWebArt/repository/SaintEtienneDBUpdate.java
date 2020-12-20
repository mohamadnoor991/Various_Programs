package com.semweb.semWebArt.repository;

import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SaintEtienneDBUpdate {

    // Create a Fuseki params
    String DB = "bikeStationDB";
    String datasetURL = "http://localhost:3030/"+ DB;
    String sparqlEndpoint = datasetURL + "/sparql";
    String sparqlUpdate = datasetURL + "/update";
    String sparqlQuery = datasetURL + "/query";
    String graphStore = datasetURL + "/data";

    private Model model = ModelFactory.createDefaultModel();
    RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);

    public SaintEtienneDBUpdate(String url1) throws IOException, InterruptedException, JSONException {
        paresStEtienne(url1,null);
    }
    public SaintEtienneDBUpdate(String url1, String url2) throws IOException, InterruptedException, JSONException {
        paresStEtienne(url1,url2); }

    public SaintEtienneDBUpdate() { }

    public  void paresStEtienne(String url1 , String url2) throws IOException, InterruptedException, JSONException {

        // Connect to the endpoint to retrive run-time data
        HttpClient client = HttpClient.newHttpClient();

        // define RDF variables Prefixes
        String geo			= "http://www.w3.org/2003/01/geo/wgs84_pos#";
        String rdf			= "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
        String rdfs			= "http://www.w3.org/2000/01/rdf-schema#";
        String bike         = "http://localhost:3030/Bik-Hos/";

        model.setNsPrefix("geo", geo);
        model.setNsPrefix("rdf", rdf);
        model.setNsPrefix("rdfs", rdfs);
        model.setNsPrefix("bike", bike);

        try{
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url1)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //        System.out.println("body json : "+ response.body());

            // Pares a JSON format data
            JSONObject object = new JSONObject(response.body()).getJSONObject("data");
            JSONArray stations = (JSONArray) object.get("stations");

            int count = 0;
            for (int i = 0; i < stations.length(); ++i) {
                JSONObject data = (JSONObject) stations.get(i);

                String stationId = data.getString("station_id");
                String stationName = data.getString("name");
                Double lon = data.getDouble("lon");
                Double lat = data.getDouble("lat");
                Double capacity = data.getDouble("capacity");
                ++count;

                Resource thisSubject = model.createResource( bike + stationId);
                Property typeof = model.createProperty(rdf +"type");
                Resource thisObjec = model.createResource(geo + "SpatialThing");

                Property latitude = model.createProperty(geo + "lat");
                Literal latValue = model.createTypedLiteral((double)lat);

                Property longitude = model.createProperty(geo + "long");
                Literal longValue = model.createTypedLiteral((double)lon);

                Property stationNameProp = model.createProperty(bike + "stationName");
                Literal stationNameValue = model.createTypedLiteral((String)stationName);

                Property capacityProp = model.createProperty(bike + "capacity");
                Literal capacityValue = model.createTypedLiteral((Double) capacity);

                model.add(thisSubject,  typeof,  thisObjec);
                model.add(thisSubject,  stationNameProp,  stationNameValue);
                model.add(thisSubject,  latitude,   latValue );
                model.add(thisSubject,  longitude,   longValue );
                model.add(thisSubject,  capacityProp,   capacityValue );

            }

//            conneg.update("INSERT DATA { <test> a <TestClass> }"); // add the triple to the triplestore
        }finally {
//            client = null;
            if (url2 != null){
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url2)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("body json : "+ response.body());

                // Pares a JSON format data
                JSONObject object = new JSONObject(response.body()).getJSONObject("data");
                JSONArray stations = (JSONArray) object.get("stations");

                int count = 0;
                for (int i = 0; i < stations.length(); ++i) {
                    JSONObject data = (JSONObject) stations.get(i);

                    String stationId = data.getString("station_id");
                    int numBikesAvailable = data.getInt("num_bikes_available");
                    int  numDocksAvailable = data.getInt("num_docks_available");
                    ++count;

                    Resource thisSubject = model.createResource( bike + stationId);

                    Property availableBike = model.createProperty(bike + "availableBike");
                    Literal availableBikeValue = model.createTypedLiteral((int)numBikesAvailable);

                    Property availableDocks = model.createProperty(bike + "availableDock");
                    Literal availableDocksValue = model.createTypedLiteral((int) numDocksAvailable);

                    model.add(thisSubject,  availableBike,   availableBikeValue );
                    model.add(thisSubject,  availableDocks,   availableDocksValue );

                }

                conneg.load(model); // add the content of model to the triplestore
                conneg.update("INSERT DATA { <test> a <TestClass> }"); // add the triple to the triplestore
                conneg.close();
            }
        }
    }
}
