# Semantic Web Project



#### Group members
|Name|  Domain |  Master |  year|
|---|---|---|---|
|Mohamad Nour Badr |  CPS2 |  M2 | 2020/2021|
|Jehad Melad| CPS2  | M2  |2020/2021|

### Notes:
- The webserver gets heavy so we reduce the result of the qurey to 25 entities for the Museum Data and for the Local Hospital data we reduce it to 200 entities.
- Due to map integration, it may take you time to load the page which contains a Maps.
- Be sure that your text editor have java 11 or above. because we have some dependencies work only with >=  java 11.
- About the stacture data we used RDFa and we have i technical issue to validate it in **RDFa Distiller**, but it works when we just take one part of (div) `<div>.....</div>` as shown in img below:

![set up database.](https://raw.githubusercontent.com/jehadmelad/semWeb/master/fuseki%20schema%20and%20data/rdfa.PNG)


#### Introduction
This project is an extension of what we learn during the year of 
Master program M2 in the domain Cyber-Physical and social systems. 
This Project reflects the achievement of the course in many aspects 
that we had learned (  Basic of RDF, Turtle syntax, RDFa, JSON-LD, 
Schema.org Embedding structured data in Web pages, SPARQL, 
triplestores, Querying RDF databases and the Semantic Web, RDFS, 
OWL, RDF data management and programming with Java). Here within 
this repository, we were working to build a webpage using almost 
all concepts of the course and integrate them.

#### Objective
To Make a Web application that integrates geospatial data from multiple sources, including dynamic data.
To Define a knowledge model (that is, an ontology) that describes the types of entities that are stored in the database.
To Display information on Web pages together with structured data, for best search engine optimization.

### Files
There are some requirements before running this project. Therefore, after ```git clone https://github.com/jehadmelad/semWeb.git``` 
our repository on your machine. You have to copy 2 folders **Bik-Hos** and  **Bike-Hos-data** Inside the following Fuseki Directory :
<ol>  
<li>Put the (Bik-Hos and Bike-Hos-data) Folders in the Fuseki folder 

````
path:/apache-jena-fuseki-3.16.0\apache-jena-fuseki-3.16.0\run\databases
```` 
</li>

<li>  After that, you have to run Fuseki server and create a new dataset hold the 
same names as the two folders (Bik-Hos and Bike-Hos-data ), at the end it sould look like the img below.
</li>

<li>
Also for the real-time non-rdf data, you have to setup and create two dataset within Fsueki server
with these two name (lyBike and bikeStationDB), otherwise, you may have some errors.
you have to have as the below img.
</li>
</ol>

![set up database.](https://raw.githubusercontent.com/jehadmelad/semWeb/master/fuseki%20schema%20and%20data/fuseki-1.PNG)




#### Tools
- The tools that we used to achieve our goals were:
- Java environment (__Spring boot__).
- HTML, Bootstrap. ( __combined by thymeleaf on SpringBoot__)
- Fuseki ( __local RDF DB__ )
- mohamad (__external RDF DB__ )
- External non-rdf data (__json__)
- internal non-rdf data (__csv__)
- online tool to build own Ontology (__http://www.visualdataweb.de/__).
- TextEditor ( __Intellij__ )

#### Data used:
* Bike stations 
    * (st-etienne, Lyon) non-rdf-data and realtime data.
* Hospitals 
    * (france) non-rdf-data and local (csv)
* Museum 
    * (France) rdf-data and external.
    
    
#### Ontology (_Bik-Hos_ )
For the ontology, we try to define our ontology based on topics that we are interested in. Therefore we have an ontology to serve bike stations and hospitals, the ontology illustrate as shown in the img below . In the next few lines, we will go through details:

![set up database.](https://raw.githubusercontent.com/jehadmelad/semWeb/master/fuseki%20schema%20and%20data/ontology.PNG)


#### Our ontology
```
#################################################################
###  Generated with the experimental alpha version of the TTL exporter of WebVOWL (version 1.1.7)  http://visualdataweb.de/webvowl/   ###
#################################################################

@prefix : 		<http://visualdataweb.org/Bik-Hos/> .
@prefix rdf: 		<http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs: 		<http://www.w3.org/2000/01/rdf-schema#> .
@prefix owl: 		<http://www.w3.org/2002/07/owl#> .
@prefix xsd: 		<http://www.w3.org/2001/XMLSchema#> .
@prefix dc: 		<http://purl.org/dc/elements/1.1/> .
@prefix xml: 		<http://www.w3.org/XML/1998/namespace> .
@prefix wot: 		<http://xmlns.com/wot/0.1/> .
@prefix vs: 		<http://www.w3.org/2003/06/sw-vocab-status/ns#> .
@prefix foaf: 		<http://xmlns.com/foaf/0.1/> .
@prefix bikhos: 		<http://localhost:3030/Bik-Hos/> .
@prefix geo: 		<http://www.w3.org/2003/01/geo/wgs84_pos#> .
@base 			<http://visualdataweb.org/Bik-Hos/> .

<http://visualdataweb.org/Bik-Hos/> rdf:type owl:Ontology ;
                                    dc:title "Bik-Hos"@en;
                                    dc:description "This ontology has been defined based on specific data, which hospitals and bike station."@en;
                                    owl:versionInfo "2"@en;
                                    dc:creator "Mohamad Bader, Jehad Melad" . 
#################################################################

###  Property Definitions (Number of Property) 18 ###
#  --------------------------- Property 0------------------------- 
:osm_TimeStamp rdf:type owl:DatatypeProperty ;
               rdfs:label "osm_TimeStamp"@en; 
               rdfs:domain :Hospital;
               rdfs:range xsd:string . 
#  --------------------------- Property 1------------------------- 
:wikiData rdf:type owl:DatatypeProperty ;
          rdfs:label "wikiData"@en; 
          rdfs:domain :Hospital;
          rdfs:range xsd:string . 
#  --------------------------- Property 2------------------------- 
:buildingNumber rdf:type owl:DatatypeProperty ;
                rdfs:label "buildingNumber"@en; 
                rdfs:domain :Hospital;
                rdfs:range rdfs:Datatype . 
#  --------------------------- Property 3------------------------- 
:ref-France-Finess rdf:type owl:DatatypeProperty ;
                   rdfs:label "ref-France-Finess"@en; 
                   rdfs:domain :Hospital;
                   rdfs:range xsd:string . 
#  --------------------------- Property 4------------------------- 
:emergency rdf:type owl:DatatypeProperty ;
           rdfs:label "emergency"@en; 
           rdfs:domain :Hospital;
           rdfs:range rdfs:Datatype . 
#  --------------------------- Property 5------------------------- 
:type-France-Finess rdf:type owl:DatatypeProperty ;
                    rdfs:label "type-France-Finess"@en; 
                    rdfs:domain :Hospital;
                    rdfs:range rdfs:Datatype . 
#  --------------------------- Property 6------------------------- 
:availableDock rdf:type owl:DatatypeProperty ;
               rdfs:label "availableDock"@en; 
               rdfs:domain :BikeStation;
               rdfs:range xsd:integer . 
#  --------------------------- Property 7------------------------- 
:availableBike rdf:type owl:DatatypeProperty ;
               rdfs:label "availableBike"@en; 
               rdfs:domain :BikeStation;
               rdfs:range xsd:integer . 
#  --------------------------- Property 8------------------------- 
:capacity rdf:type owl:DatatypeProperty ;
          rdfs:label "capacity"@en; 
          rdfs:domain :BikeStation;
          rdfs:range xsd:integer . 
#  --------------------------- Property 9------------------------- 
:lat rdf:type owl:DatatypeProperty ;
     rdfs:label "lat"@en; 
     rdfs:domain :BikeStation;
     rdfs:range xsd:double . 
#  --------------------------- Property 10------------------------- 
:lon rdf:type owl:DatatypeProperty ;
     rdfs:label "lon"@en; 
     rdfs:domain :BikeStation;
     rdfs:range xsd:double . 
#  --------------------------- Property 11------------------------- 
:hasStatus rdf:type owl:DatatypeProperty ;
           rdfs:label "hasStatus"@en; 
           rdfs:domain :BikeStation;
           rdfs:range rdfs:Datatype . 
#  --------------------------- Property 12------------------------- 
:stationName rdf:type owl:DatatypeProperty ;
             rdfs:label "stationName"@en; 
             rdfs:domain :BikeStation;
             rdfs:range rdfs:Datatype . 
#  --------------------------- Property 13------------------------- 
:hasAddress rdf:type owl:DatatypeProperty ;
            rdfs:label "hasAddress"@en; 
            rdfs:domain :BikeStation;
            rdfs:range rdfs:Datatype . 
#  --------------------------- Property 14------------------------- 
:locatedInCommune rdf:type owl:DatatypeProperty ;
                  rdfs:label "locatedInCommune"@en; 
                  rdfs:domain :BikeStation;
                  rdfs:range rdfs:Datatype . 
#  --------------------------- Property 15------------------------- 
:name rdf:type owl:DatatypeProperty ;
      rdfs:label "name"@en; 
      rdfs:domain :City;
      rdfs:range xsd:string . 
#  --------------------------- Property 16------------------------- 
:lat rdf:type owl:DatatypeProperty ;
     rdfs:label "lat"@en; 
     rdfs:domain :City;
     rdfs:range xsd:double . 
#  --------------------------- Property 17------------------------- 
:lon rdf:type owl:DatatypeProperty ;
     rdfs:label "lon"@en; 
     rdfs:domain :City;
     rdfs:range xsd:double . 
###  Class Definitions (Number of Classes) 8 ###
#  --------------------------- Class  0------------------------- 
geo:SpatialThing rdf:type owl:Class; 
                 rdfs:subClassOf owl:Thing ;
                 rdfs:label "Spatial Thing"@en . 
#  --------------------------- Class  1------------------------- 
:Country rdf:type owl:Class; 
         rdfs:subClassOf geo:SpatialThing ;
         rdfs:label "Country"@en . 
#  --------------------------- Class  2------------------------- 
:City rdf:type owl:Class; 
      rdfs:subClassOf :Country ;
      owl:disjointWith :BikeStation ;
      rdfs:label "City"@en . 
#  --------------------------- Class  3------------------------- 
:Hospital rdf:type owl:Class; 
          rdfs:subClassOf :Building ;
          rdfs:label "Hospital"@en . 
#  --------------------------- Class  4------------------------- 
:Transportaion rdf:type owl:Class; 
               rdfs:subClassOf :City ;
               rdfs:label "Transportaion"@en . 
#  --------------------------- Class  5------------------------- 
:BikeStation rdf:type owl:Class; 
             rdfs:subClassOf :Transportaion ;
             rdfs:label "BikeStation"@en . 
#  --------------------------- Class  6------------------------- 
:Building rdf:type owl:Class; 
          rdfs:subClassOf :City ;
          rdfs:label "Building"@en . 
#  --------------------------- Class  7------------------------- 
owl:Thing rdf:type owl:Class; 
          rdfs:label "Thing"@en . 

```

#### structure Data `RDFa`
We have used a type of stracture data which embeded in html to inhance the content of the page on the internet and the search engin. down here a part of our validation



<div id="classes"> 

<h4>Classes</h4>
<p>There are 8 Classes defined beside reuseing some existing ontologies. </p>

<a href="#SpatialThing">SpatialThing</a> |  &nbsp;
<a href="#Country">Country</a> | &nbsp;
<a href="#City">City</a> | &nbsp;
<a href="#Hospital">Hospital</a> | &nbsp;
<a href="#Transportaion">Transportaion</a> | &nbsp; 
<a href="#BikeStation">BikeStation</a> | &nbsp;
<a href="#Building">Building</a> | &nbsp;
<a href="#Thing">Thing</a> | &nbsp;
</div>

<div id="prop">
<h4>Properties</h4>
<p></p>
<a href="ref-France-Finess">ref-France-Finess</a> | &nbsp;
<a href="wikiData">wikiData</a> | &nbsp;
<a href="buildingNumber">buildingNumber</a> | &nbsp;
<a href="type-France-Finess">type-France-Finess</a> | &nbsp;
<a href="osm_TimeStamp">osm_TimeStamp</a> | &nbsp;
<a href="emergency">emergency</a> | &nbsp;
<a href="availableDock">availableDock</a> | &nbsp;
<a href="availableBike">availableBike</a> | &nbsp;
<a href="capacity">capacity</a> | &nbsp;
<a href="lat">lat</a> | &nbsp;
<a href="lon">lon</a> | &nbsp;
<a href="hasStatus">hasStatus</a> | &nbsp;
<a href="stationName">stationName</a> | &nbsp;
<a href="hasAddress">hasAddress</a> | &nbsp;
<a href="locatedInCommune">locatedInCommune</a> | &nbsp;
<a href="name">name</a> | &nbsp;
<a href="lat">lat</a> | &nbsp;
<a href="lon">lon</a> | &nbsp;
<a href="name">name</a> | &nbsp;
<a href="name">name</a> | &nbsp;
</div>
<p ><strong>Hint:</strong> More explaination will be included in the report ... To be continue </p>
<br>

### Full Details
***
<div id="SpatialThing" >  
<p>Spatial Thing - A spatial coordination</p>
  <h5>Class: &nbsp;&nbsp;bikhos:SpatialThing</h5>
    <p><strong>Status:</strong> Stable</p>
    <p><strong>Subclass Of:</strong> &nbsp;<a href="#Thing">Thing</a></p>
  <p>[ SpatialThing ] It is an existing vocabulary model representing geolocation coordinations lat(itude), long(itude) and other information about spatially-located things, using WGS84 as a reference datum</p>
</div>
<br>
<br>

***
<div id="Country" >  
  <h5>Class: &nbsp;&nbsp;bikhos:Country</h5>
  <p>Country - Relevent to Country names</p>
    <p><strong>Status:</strong> Testing</p>
    <p><strong>Subclass Of:</strong> &nbsp;<a href="#SpatialThing">Spatial Thing</a></p>
<p>[Country] - it used when we are going to describe a country which could have geolocation or name properties.
</div><br>
<br>

***
<div id="City" >  
  <h5>Class: &nbsp;&nbsp;bikhos:City</h5>
  <p></p>
    <p><strong>Status:</strong> Testing</p>
    <p><strong>disjointWith:</strong> <a href="#BikeStation">BikeStation</a></p>
    <p><strong>Subclass Of:</strong> &nbsp;<a href="#Country">Country</a>
    </strong> </p>
    <p><strong>Properties include: </strong><a href="#name">name</a>
    <a href="#lat">lat</a>
    <a href="#lon">lon</a></p>
<p>[City] - Class useing to decribe the city entity </p>
</div>
<br>

***
<div id="Hospital" >  
  <h5>Class: &nbsp;&nbsp;bikhos:Hospital</h5>
  <p></p>//
    <p><strong>Status:</strong> Testing</p>
    <p><strong>Subclass Of:</strong> &nbsp;<a href="#Building">Building</a></p>
    <p><strong>Properties include: </strong><a href="#ref-France-Finess">ref-France-Finess</a>
        <a href="#wikiData">wikiData</a> </strong><a href="#buildingNumber">buildingNumber</a>
         <a href="#type-France-Finess">type-France-Finess</a> <a href="#osm_TimeStamp">osm_TimeStamp</a>
        <a href="#emergency">emergency</a></p>
        <p>[ Hospital ] - class useing to describe the hospital entity   </p>
</div>
<br>

***
<div id="Transportaion" >  
  <h5>Class: &nbsp;&nbsp;bikhos:Transportaion</h5>
  <p></p>
    <p><strong>Status:</strong> Testing</p>
    <p><strong>Subclass Of:</strong> &nbsp;<a href="#City">City</a></p>
    <p>[Transportaion] - Father class</p>
</div>
<br>

***
<div id="BikeStation" >  
  <h5>Class: &nbsp;&nbsp;bikhos:BikeStation</h5>
  <p></p>
    <p><strong>Status:</strong> Testing</p>
    <p><strong>Subclass Of:</strong> &nbsp;<a href="#Transportaion">Transportaion</a></p>
    <p><strong>Properties include: </strong><a href="#availableDock">availableDock</a>
            <a href="#availableBike">availableBike</a> </strong><a href="#capacity">capacity</a>
             <a href="#hasStatus">hasStatus</a> <a href="#stationName">stationName</a>
            <a href="#hasAddress">hasAddress</a> <a href="#locatedInCommune">locatedInCommune</a> <a href="#lat">lat</a>
              <a href="#lon">lon</a>
            </p>
    <p>[ BikeStation ] - class Useing to describr the BikeStation entity</p>
</div>
<br>

***
<div id="Building" >  
  <h5>Class: &nbsp;&nbsp;bikhos:Building</h5>
  <p></p>
    <p><strong>Status:</strong> Testing</p>
    <p><strong>Subclass Of:</strong> &nbsp;<a href="#City">City</a></p>
    <p>[Building] - useing to describe the building entity(Father class of 
    hospitla, subclass of City</p>
</div>
<br>

***
<div id="Thing" >  
  <h5>Class: &nbsp;&nbsp;bikhos:Thing</h5>
  <p></p>
    <p><strong>Status:</strong> Testing</p>
    <p>[Thing] - Thing of every thing (Main Class of all classes)</p>
</div>
<br>




