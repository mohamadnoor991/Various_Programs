package semwebA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Hospitals {
public static void main(String[]args){
		// TODO Auto-generated method stub
		Model model = ModelFactory.createDefaultModel();
		
		String schema = "https://schema.org/";
		String geo          = "http://www.w3.org/2003/01/geo/wgs84_pos#";
		String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
		String ex = "http://www.example.com/";
		String hospital ="https://schema.org/Hospital/";
//		String thing = "https://schema.org/Thing/";
		String mHosSceh = "http://localhost:3030/Bik-Hos/";
//		String name =  "https://schema.org/name/";
		
		String filePath = "src\\main\\java\\FH.csv";
		String line;
		try {
			BufferedReader buffreader = new BufferedReader(new FileReader(filePath));
			buffreader.readLine(); // This will read the first line header
			while ((line = buffreader.readLine()) != null) {
				String[] value = line.split(","); /*** has the header titles ***/

			
				// the values of the header
				// String = value[];
//				String h= value[3].replace(" ", "_");
				
				String osm_id = value[1];//resource
				
				String hospitalName =value[3];
				
				
				String short_name = value[4];
				String alt_name = value[6];
				String operAtor = value[8];
				 String operT = value[9];
				 String emergency = value[10];
				 String refF= value[13];
				 String typeF= value[14];
				 String urL= value[17];
				 String  tele= value[19];
				 String email = value[23];
				 String BuildingNumber= value[26];
				String streetAddress = value[27];
				String city = value[28];
				String postalCode = value[29];
				String wikiData = value[30];
				
				String osm_TimeStamp= value[38];
				String medicalSpecialty= value[39];
				String geom = value[40];
				
				/***///code for geo
//				String[] geomPoint = geom.split(" ");
//				String lat =geomPoint[1];
//				String latNew = lat.replace("(", "");//lat
//				String lon =geomPoint[2];
//				String lonNew = lon.replace(")", "");//lon
//				 System.out.print("lat "+ geomPoint[1]+"\n"+"longtuide "+geomPoint[2]);
				/****/

				Resource hospitalN = model.createResource(ex + osm_id);
				hospitalN.addProperty(RDF.type, model.createResource(schema + "Thing"));

//				Property addres = model.createProperty(schema + "address");
//				Literal place = model.createTypedLiteral(placeA);
				
				Property nameP = model.createProperty( schema +"name");
				Literal nameL = model.createTypedLiteral(hospitalName);
				
				

				Property shortName = model.createProperty(schema + "identifier");
				Literal shortN = model.createTypedLiteral(short_name);

				Property altName = model.createProperty(schema + "alternateName");
				Literal altN = model.createTypedLiteral(alt_name);

				Property oPerator = model.createProperty(schema + "provider");
				Literal operator = model.createTypedLiteral(operAtor);

	                Property    emerge         = model.createProperty(mHosSceh + "emergency"); 
	                Literal    emergenc     = model.createTypedLiteral(emergency);
	                
	                Property     operatorT        = model.createProperty(mHosSceh + "operatorType"); 
	                Literal     operatoRT    = model.createTypedLiteral(operT);

	                Property    refff         = model.createProperty(mHosSceh + "ref-France-Finess"); 
	                Literal     reff    = model.createTypedLiteral(refF);

	                Property     typefff        = model.createProperty(mHosSceh + "type-France-Finess"); 
	                Literal    typeff     = model.createTypedLiteral(typeF);

	                Property        urlP     = model.createProperty(schema + "url"); 
	                Literal   urlL      = model.createTypedLiteral(urL);

	                Property    teleP         = model.createProperty(schema + "telephone"); 
	                Literal       teleL  = model.createTypedLiteral(tele);

	                Property      emailP        = model.createProperty(schema + "email"); 
	                Literal      emailL    = model.createTypedLiteral( email);

	                Property        BuildingNumberP     = model.createProperty(mHosSceh + "buildingNumber"); 
	                Literal   BuildingNumberL      = model.createTypedLiteral(BuildingNumber);

	                Property      streetAddressP       = model.createProperty(schema + "streetAddress"); 
	                Literal streetAddressL        = model.createTypedLiteral(streetAddress);

	                Property       cityP      = model.createProperty(schema + "location"); //change city to hospital
	                Literal        cityL = model.createTypedLiteral(city);
	                
	                Property       postalCodeP      = model.createProperty(schema + "postalCode"); //"^^xsd:int" added
	                Literal        postalCodeL = model.createTypedLiteral(postalCode);

	                Property      wikiDataP       = model.createProperty(schema + "WikiData"); 
	                Literal      wikiDataL   = model.createTypedLiteral(wikiData);
	                
	                Property    osm_TimeStampP         = model.createProperty(mHosSceh + "osm_TimeStamp"); 
	                Literal      osm_TimeStampL   = model.createTypedLiteral(osm_TimeStamp);

	                Property      medicalSpecialtyP       = model.createProperty(schema + "MedicalSpecialty"); 
	                Literal      medicalSpecialtyL   = model.createTypedLiteral(medicalSpecialty);

	                Property      geomP       = model.createProperty(geo + "lat_long"); 
	                Literal     geomL    = model.createTypedLiteral(geom);
	                
//	                Property      lonP       = model.createProperty(geo + "lon"); 
//	                Literal     lonL    = model.createTypedLiteral(lonNew);


				// To add properties and values to the resource
				// model.add(Resource, Property, literal);

//				model.add(hospitalN, addres, place);
	                model.add(hospitalN,nameP,nameL);
				model.add(hospitalN, shortName, shortN);
				model.add(hospitalN, altName, alt_name);
				model.add(hospitalN, oPerator, operator);
	                model.add(hospitalN,emerge ,emergenc );
	                model.add(hospitalN,operatorT,operatoRT);
	                model.add(hospitalN,refff ,reff );
	                model.add(hospitalN,typefff ,typeff );
	                model.add(hospitalN,urlP ,urlL );
	                model.add(hospitalN,teleP ,teleL );
	                model.add(hospitalN, emailP , emailL );
	                model.add(hospitalN,BuildingNumberP ,BuildingNumberL );
	                model.add(hospitalN,streetAddressP ,streetAddressL );
	                model.add(hospitalN,cityP ,cityL );
	                model.add(hospitalN,postalCodeP ,postalCodeL );
	                model.add(hospitalN,osm_TimeStampP ,osm_TimeStampL );
	                model.add(hospitalN,medicalSpecialtyP ,medicalSpecialtyL );
//	                model.add(hospitalN,latP ,latL );
//	                model.add(hospitalN,lonP ,lonL );
	                model.add(hospitalN,geomP ,geomL );
	                


				// define the prefixes
				model.setNsPrefix("schema", schema);
				model.setNsPrefix("rdf", rdf);
				model.setNsPrefix("rdfs", rdfs);
				model.setNsPrefix("ex", ex);
				model.setNsPrefix("hospital", hospital);
//				model.setNsPrefix("thing", thing);
				model.setNsPrefix("mHosSceh", mHosSceh);
				model.setNsPrefix("geo", geo);
				// For Testing
//				model.write(System.out, "Turtle");
//				break;
////				
				
//				try(OutputStream out = new FileOutputStream("filename1.rdf")) {
//			        RDFDataMgr.write(out, model, Lang.TTL);
//			    } catch (FileNotFoundException e) {
//			        e.printStackTrace();
//			    } catch (IOException e) {
//			        e.printStackTrace();
//			    }
				
				
				/***********************************/
				
//				String fileName = "DataFile.ttl";
//				FileWriter out = new FileWriter( fileName );
//				try {
//				    model.write( out, "ttl" );
//				}
//				finally {
//				   try {
//				       out.close();
//				   }
//				   catch (IOException closeException) {
//				       // ignore
//				   }
//				}
				/**************************************/
//			System.out.print("lat "+ latNew+"\n"+"longtuide "+lonNew);
//				break;

				// API to connect Fuseki and store our data.
	                String datasetURL ="http://localhost:3030/Bik-Hos-Data";
	                String sparqlEndpoint = datasetURL + "/sparql";
	                String sparqlUpdate = datasetURL + "/update";
	                String graphStore = datasetURL + "/data";
	                RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
	                conneg.load(model); // add the content of model to the triplestore
	                conneg.update("INSERT DATA { <test> a <TestClass> }"); // add the triple to the triplestore

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
