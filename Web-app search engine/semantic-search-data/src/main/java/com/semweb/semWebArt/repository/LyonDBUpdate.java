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
public class LyonDBUpdate {

    // Create a Fuseki params
    String DB = "lyBike";
    String datasetURL = "http://localhost:3030/" + DB;
    String sparqlEndpoint = datasetURL + "/sparql";
    String sparqlUpdate = datasetURL + "/update";
    String sparqlQuery = datasetURL + "/query";
    String graphStore = datasetURL + "/data";

    private Model model = ModelFactory.createDefaultModel();
    RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint, sparqlUpdate, graphStore);

    public LyonDBUpdate()  {

    }
    public LyonDBUpdate(String url1) throws InterruptedException, JSONException, IOException {
        paresLyon(url1);
    }
    public void paresLyon(String url1) throws IOException, InterruptedException, JSONException {

        // Connect to the endpoint to retrive run-time data
        HttpClient client = HttpClient.newHttpClient();

        // define RDF variables Prefixes
        String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
        String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
        String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
        String bike = "http://localhost:3030/Bik-Hos/";

        model.setNsPrefix("geo", geo);
        model.setNsPrefix("rdf", rdf);
        model.setNsPrefix("rdfs", rdfs);
        model.setNsPrefix("bike", bike);

//        conneg.update("DELETE DATA { <test> a <TestClass> }");
//        conneg.update("DELETE WHERE { <test> a <TestClass> }");
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url1)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //        System.out.println("body json : "+ response.body());

            // Pares a JSON format data
            JSONObject object = new JSONObject(response.body());
            JSONArray features = (JSONArray) object.get("features");

            for (int i = 0; i < features.length(); ++i) {
                JSONObject data = (JSONObject) features.get(i);
                JSONObject properties = data.getJSONObject("properties");

                String stationId = properties.getString("number");
                String stationName = properties.getString("name");
                String stationAddress = properties.getString("address");
                String commune = properties.getString("commune");
                Double lat = properties.getDouble("lat");
                Double lon = properties.getDouble("lng");
                int capacity = properties.getInt("bike_stands");
                String numDocksAvailable = (properties.getString("available_bike_stands").isEmpty()) ? "0" : properties.getString("available_bike_stands");
                String  numBikesAvailable = (properties.getString("available_bikes").isEmpty()) ? "0" : properties.getString("available_bikes");
                String status = properties.getString("status");

                Resource thisSubject = model.createResource(bike + stationId);
                Property typeof = model.createProperty(rdf + "type");
                Resource thisObjec = model.createResource(geo + "SpatialThing");

                Property stationAddressProp = model.createProperty(bike + "hasAddress");
                Literal stationAddressValue = model.createTypedLiteral((String) stationAddress);

                Property communeProp = model.createProperty(bike + "locatedInCommune");
                Literal communeValue = model.createTypedLiteral((String) commune);

                Property statusProp = model.createProperty(bike + "hasStatus");
                Literal statusValue = model.createTypedLiteral((String) status);

                Property latitude = model.createProperty(geo + "lat");
                Literal latValue = model.createTypedLiteral((double) lat);

                Property longitude = model.createProperty(geo + "lon");
                Literal longValue = model.createTypedLiteral((double) lon);

                Property stationNameProp = model.createProperty(bike + "stationName");
                Literal stationNameValue = model.createTypedLiteral((String) stationName);

                Property capacityProp = model.createProperty(bike + "capacity");
                Literal capacityValue = model.createTypedLiteral((int) capacity);

                Property availableBike = model.createProperty(bike + "availableBike");
                Literal availableBikeValue = model.createTypedLiteral( Integer.parseInt(numBikesAvailable));

                Property availableDocks = model.createProperty(bike + "availableDock");
                Literal availableDocksValue = model.createTypedLiteral( Integer.parseInt(numDocksAvailable));

                model.add(thisSubject, availableBike, availableBikeValue);
                model.add(thisSubject, availableDocks, availableDocksValue);

                model.add(thisSubject, typeof, thisObjec);
                model.add(thisSubject, stationNameProp, stationNameValue);
                model.add(thisSubject, latitude, latValue);
                model.add(thisSubject, longitude, longValue);
                model.add(thisSubject, capacityProp, capacityValue);
                model.add(thisSubject, statusProp, statusValue);
                model.add(thisSubject, stationAddressProp, stationAddressValue);
                model.add(thisSubject, communeProp, communeValue);
            }


        }finally {
            conneg.load(model); // add the content of model to the triplestore
            conneg.update("INSERT DATA { <test> a <TestClass> }"); // add the triple to the triplestore
            conneg.close();
        }

//        TimeUnit.SECONDS.sleep(10);

    }
}