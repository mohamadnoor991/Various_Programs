package com.semweb.semWebArt.services;

import com.semweb.semWebArt.model.Hospital;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionRemote;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalServices {

    private RDFConnection conn0 = RDFConnectionRemote.create()
            .destination("http://localhost:3030/Bik-Hos-Data")
            .queryEndpoint("sparql")
            .acceptHeaderSelectQuery("application/sparql-results+json, application/sparql-results+xml;q=0.9")
            .build();
    private String service = "http://localhost:3030/Bik-Hos-Data";

    private List<Hospital> hospitalsList = new ArrayList<>();

    public List<Hospital> getHospitalsList() {
        return hospitalsList;
    }

    @PostConstruct
    @Scheduled(cron = "1 * * * * *")
    public void queryData() throws IOException {
        List<Hospital> updateHospitalsList = new ArrayList<>();
        // Define the prefixes of the knowledge base
        String prefixes =  " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
                + "PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\r\n"
                + "PREFIX  hospital:<https://schema.org/Hospital/>\r\n"
                + "PREFIX localSchema:<http://localhost:3030/Bik-Hos/>\r\n"
                + "PREFIX geao:<http://www.w3.org/2003/01/geo/wgs84_pos#>\r\n"
                + "PREFIX schema: <https://schema.org/>\r\n"
                + "PREFIX ex: <http://www.example.com/>\r\n"+"PREFIX foaf: <http://xmlns.com/foaf/0.1/>";

        //Define the query statement
        String myquery = "SELECT  distinct   ?x ?v (?q As ?Short_Name)  (?w AS ?alternateName) (?e AS ?provider)\r\n"
                + "         		(?r As ?Emergency) (?t AS ?OperatorType)(?y As ?Ref_France_Finess)\r\n"
                + "         		(?u As ?Type_France_Finess)(?i As ?WebSite)(?o As ?Tele)\r\n"
                + "         		(?p As ?Email)(?a As ?BuildingNumber)(?s As ?streetAddress)\r\n"
                + "         		(?d As ?City)(?f As ?postalCode)(?h As ?TimeStamp)\r\n"
                + "         		(?j As ?MedicalSpecialty)(?z As ?lat_long)\r\n"
                + "         		    WHERE { ?x  a schema:Thing ;schema:identifier ?q; schema:alternateName ?w;schema:provider ?e;\r\n"
                + "         		 localSchema:emergency ?r;localSchema:operatorType ?t;\r\n"
                + "         		 localSchema:ref-France-Finess ?y;localSchema:type-France-Finess ?u;\r\n"
                + "         		 schema:url ?i;schema:telephone ?o;schema:email ?p;\r\n"
                + "         		 localSchema:buildingNumber ?a;schema:streetAddress ?s;\r\n"
                + "         		 schema:location ?d;schema:postalCode ?f;localSchema:osm_TimeStamp ?h;\r\n"
                + "            schema:MedicalSpecialty ?j;geao:lat_long ?z;schema:name ?v}limit 200" ;


        // The query used to retrive our information using SPARQL
        Query query = QueryFactory.create( prefixes + myquery);
        QueryExecution queryExe = QueryExecutionFactory.sparqlService(service,query);
//        ResultSet resultSet = queryExe.execSelect();


        /********  mohamad nour********/


        try {
            ResultSet resultSet = queryExe.execSelect();
            while (resultSet.hasNext()) {
                Hospital hospital = new Hospital();
                QuerySolution soln = resultSet.nextSolution();



//                Resource resource=soln.getResource("people");
////                RDFNode pro= soln.get("rdf:type");
//                Literal name = soln.getLiteral("name");


                Literal name= soln.getLiteral("v");
                Literal BuildingNumber= soln.getLiteral("BuildingNumber");
                Literal Emergency= soln.getLiteral("Emergency");
                Literal OperatorType= soln.getLiteral("OperatorType");
                Literal osm_TimeStamp= soln.getLiteral("TimeStamp");//?
                Literal ref_France_Finess= soln.getLiteral("Ref_France_Finess");
                Literal lat_long= soln.getLiteral("lat_long");
                Literal city= soln.getLiteral("City");
                Literal medicalSpecialty = soln.getLiteral("MedicalSpecialty");
                Literal alternateName= soln.getLiteral("alternateName");
                Literal email= soln.getLiteral("Email");

                Literal postalCode= soln.getLiteral("postalCode");
                Literal provider= soln.getLiteral("provider");
                Literal streetAddress= soln.getLiteral("streetAddress");
                Literal telephone= soln.getLiteral("Tele");
//                System.out.println("the buildind vale is "+BuildingNumber+"  the time stamp "+city);


                //assign literal to hospital Var
                hospital.setName(name.toString());
                hospital.setBuildingNumber(BuildingNumber.getString());
                hospital.setEmergency(Emergency.getString());
                hospital.setOperatorType(OperatorType.getString());
                hospital.setOsm_TimeStamp(osm_TimeStamp.getString());//??
                hospital.setRef_France_Finess(ref_France_Finess.getString());
                hospital.setLat_long(lat_long.getString());
                hospital.setCity(city.toString());
                hospital.setMedicalSpecialty(medicalSpecialty.toString());

                hospital.setAlternateName(alternateName.getString());
                hospital.setEmail(email.getString());
                hospital.setPostalCode(postalCode.getString());
                hospital.setProvider(provider.getString());
                hospital.setStreetAddress(streetAddress.getString());
                hospital.setTelephone(telephone.getString());

                updateHospitalsList.add(hospital);


            }

        }finally {
            queryExe.close();
        }

        // Add the result to the main pool.
        this.hospitalsList=updateHospitalsList;


    }
}
