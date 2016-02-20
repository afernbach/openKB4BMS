/*
 * 	   openKB4BMS is an open source knowledge base (KB) acting as a building
 *     management application.
 *
 *     Copyright (C) 2016
 * 	   Institute of Computer Aided Automation, Automation Systems Group, TU Wien.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package at.ac.tuwien.auto.sewoa;

import at.ac.tuwien.auto.sewoa.obix.ObixUnitFactory;
import at.ac.tuwien.auto.sewoa.obix.ObixUnitParser;
import at.ac.tuwien.auto.sewoa.obix.jena.ObixIndividual;
import at.ac.tuwien.auto.sewoa.obix.jena.ObixSewoaModelHandler;
import at.ac.tuwien.auto.sewoa.obix.jena.ObixSewoaModelListener;
import at.ac.tuwien.auto.sewoa.obix.ObixModelConverter;
import at.ac.tuwien.auto.sewoa.obix.jena.device.*;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.sparql.core.TriplePath;
import com.hp.hpl.jena.sparql.syntax.ElementPathBlock;
import com.hp.hpl.jena.sparql.syntax.ElementVisitorBase;
import com.hp.hpl.jena.sparql.syntax.ElementWalker;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.util.FileManager;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: pelesic
 * Date: 08/06/15
 * Time: 07:20
 */
public class MainJena {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainJena.class);
    private static final String PREFIX = "http://localhost:8080";

    public static void main( String[] args )
    {
        // Create an empty in-memory ontology model
        OntDocumentManager mgr = new OntDocumentManager();
        OntModelSpec s = new OntModelSpec(PelletReasonerFactory.THE_SPEC);
        s.setDocumentManager( mgr );
        OntModel ontologyModel = ModelFactory.createOntologyModel( s, null );
        ontologyModel.setStrictMode(false);


        // use the FileManager to open the ontology from the filesystem
        InputStream in = FileManager.get().open("sewoa_example_working.owl");
        if (in == null) {
            throw new IllegalArgumentException( "File not found"); }
        ontologyModel.read(in, "");


        ObixModelConverter buildingModelConverter = new ObixModelConverter("http://localhost:8080/networks/e183_1/views/building", "buildings.xsl");
        String buildingOnt = buildingModelConverter.fetchAndConvert();
        System.out.println(buildingOnt);
        ontologyModel.read(new ByteArrayInputStream(buildingOnt.getBytes()), "");

        ObixModelConverter groupModelConverter = new ObixModelConverter("http://localhost:8080/networks/e183_1/views/functional", "groups.xsl");
        String groupOnt = groupModelConverter.fetchAndConvert();
        System.out.println(groupOnt);
        ontologyModel.read(new ByteArrayInputStream(groupOnt.getBytes()), "");

        HashMap<String, ObixIndividual> individuals = new HashMap<String, ObixIndividual>();

        ObixUnitFactory obixUnitFactory = new ObixUnitFactory(PREFIX, new ObixUnitParser());

        ObixSewoaModelHandler handler = new ObixSewoaModelHandler(PREFIX, individuals, ontologyModel, obixUnitFactory);
        handler.registerHandler(new OnOffSwitchDeviceHandlerImpl());
        handler.registerHandler(new TempSensorDeviceHandlerImpl());
        handler.registerHandler(new TempControllerDeviceHandlerImpl());
        handler.registerHandler(new LightSensorDeviceHandlerImpl());
        handler.registerHandler(new PresenceSensorDeviceHandlerImpl());
        handler.registerHandler(new Co2SensorDeviceHandler());
        handler.init();

        ontologyModel.register(new ObixSewoaModelListener(PREFIX, individuals, handler));

        sparqlQuery("11", ontologyModel);
        while(true){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String input = br.readLine();
                if (input.contains("1")){
                    sparqlQuery(input, ontologyModel);
                } else if (input.contains("2")){
                    sparqlUpdate(2, ontologyModel);
                } else if (input.contains("3")){
                    sparqlUpdate(3, ontologyModel);
                } else if (input.contains("4")){
                    sparqlUpdate(4, ontologyModel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static void sparqlQuery(String input, OntModel model){
        String queryStr1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?location " +
                "WHERE { "
//                  +  "?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#DistributionBoard> .\n "
                + "?location EnergyResourceOntology:contains <http://localhost:8080//networks/e183_1/entities/load_switch_n_510_03_n_510_04> . \n "
                +"}";

        String queryStr1a = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?location " +
                "WHERE { "
//                  +  "?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#DistributionBoard> .\n "
                + "?location EnergyResourceOntology:isIn <http://localhost:8080/building/parts/treitlstrasse/parts/4stock/parts/aufputzkasten_a_lab_rechts> . \n "
                +"}";

        String queryStr1b = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?switch " +
                "WHERE { "
//                  +  "?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#DistributionBoard> .\n "
                + "?switch EnergyResourceOntology:hasCurrentStateValue ?type . \n "
                + "?type rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#$state$> . \n "
                +"}";

        String queryStr1c = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?switch ?value ?unit " +
                "WHERE { "
//                  +  "?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#DistributionBoard> .\n "
                + "?switch rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#$sensor$> . \n "
                + "?switch EnergyResourceOntology:hasCurrentStateValue ?current . \n "
                + "?current EnergyResourceOntology:realStateValue ?value . \n "
                + "?current EnergyResourceOntology:hasNativeUnit ?unit . \n "
                + "?switch EnergyResourceOntology:functionOf ?device . \n "
                + "?device EnergyResourceOntology:isIn <$location$> . \n "
                +"}";

        String queryStr2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?switch WHERE {\n" +
                "?switch EnergyResourceOntology:contains <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#Computer1>  .\n" +
                "}\n";

        String queryStr3 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?location WHERE { ?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#DistributionBoard> . \n" +
                " }";

        String queryStr4 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?location WHERE { ?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#OnOffLightSwitch> . \n" +
                " }";

        String queryStr5 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT DISTINCT ?entity\n" +
                "WHERE {\n" +
                "  ?entity rdf:type ?type .\n" +
                "  ?type rdfs:subClassOf <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#OnOffLightSwitch> . \n" +
                "}";

        String query = queryStr1b.replace("$state$", "OffStateValue");
        if (input != null && input.contains("12")){
            query = queryStr1c.replace("$sensor$", "TemperatureSensor").replace("$location$","http://localhost:8080/building/parts/treitlstrasse/parts/4stock/parts/aufputzkasten_a_lab_links");;
        } else if (input != null && input.contains("13")){
            query = queryStr1c.replace("$sensor$", "LightSensor").replace("$location$","http://localhost:8080/building/parts/treitlstrasse/parts/4stock/parts/a_lab");
        } else if (input != null && input.contains("14")){
            query = queryStr1c.replace("$sensor$", "TemperatureController").replace("$location$","http://localhost:8080/building/parts/treitlstrasse/parts/4stock/parts/aufputzkasten_a_lab_links");
        } else if (input != null && input.contains("15")){
            query = queryStr1b.replace("$state$", "NotPresentState");
        }
        System.out.println(query);


        try {
            QueryExecution qe = QueryExecutionFactory.create(
                    query,
                    model);
//            qe.setTimeout(10000);

//            Set<Node> subjects = getSubjects(qe.getQuery());

            for (ResultSet rs = qe.execSelect() ; rs.hasNext() ; ) {
                QuerySolution binding = rs.nextSolution();
                System.out.println("switch: " + binding.get("switch"));
                RDFNode value = binding.get("value");
                if (value != null && value.isLiteral()){
                    System.out.println("value: " +value.toString());
                }

                RDFNode unit = binding.get("unit");
                if (unit != null && unit.isLiteral()){
                    System.out.println("unit: " +unit.toString());
                }
            }
            System.out.println("Query finished!");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    private static void sparqlUpdate(int onOff, OntModel model){
        String update1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#> \n" +
                "DELETE\n" +
                " { <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c#lamp> EnergyResourceOntology:hasCurrentStateValue <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c#stateOn> }\n"+
                "INSERT \n" +
                " { <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c#lamp> EnergyResourceOntology:hasCurrentStateValue <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c#stateOff> }\n"+
                " WHERE {}";

        String update2 = "";
        if (onOff == 2) {
            update2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#> \n" +
                    "DELETE\n" +
                    " { <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c/value> EnergyResourceOntology:hasCurrentStateValue <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c#stateOn> }\n" +
                    "INSERT \n" +
                    " { <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c/value> EnergyResourceOntology:hasCurrentStateValue <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c#stateOff> }\n" +
                    " WHERE {}";
        } else if (onOff == 3) {
            update2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#> \n" +
                    "DELETE\n" +
                    " { <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c/value> EnergyResourceOntology:hasCurrentStateValue <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c#stateOff> }\n" +
                    "INSERT \n" +
                    " { <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c/value> EnergyResourceOntology:hasCurrentStateValue <http://localhost:8080/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c#stateOn> }\n" +
                    " WHERE {}";
        } else if (onOff == 4){
            update2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#> \n" +
                    "DELETE\n" +
                    " { <http://localhost:8080/networks/e183_1/entities/fan_coil_49550/1/datapoints/base_setpoint_temperature#tempStateValue> EnergyResourceOntology:realStateValue ?v }\n" +
                    "INSERT \n" +
                    " { <http://localhost:8080/networks/e183_1/entities/fan_coil_49550/1/datapoints/base_setpoint_temperature#tempStateValue> EnergyResourceOntology:realStateValue \"21.0\" }\n" +
                    " WHERE { <http://localhost:8080/networks/e183_1/entities/fan_coil_49550/1/datapoints/base_setpoint_temperature#tempStateValue> EnergyResourceOntology:realStateValue ?v  }";
        }


                System.out.println(update2);


        try {
            UpdateRequest updateRequest = UpdateFactory.create(update2);
            UpdateAction.execute(updateRequest, model);
            System.out.println("Update finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Set<Node> getSubjects(Query query){
        // Remember distinct subjects in this
        final Set<Node> subjects = new HashSet<Node>();

        // This will walk through all parts of the query
        ElementWalker.walk(query.getQueryPattern(),
                // For each element...
                new ElementVisitorBase() {
                    // ...when it's a block of triples...
                    public void visit(ElementPathBlock el) {
                        // ...go through all the triples...
                        Iterator<TriplePath> triples = el.patternElts();
                        while (triples.hasNext()) {
                            // ...and grab the subject
                            subjects.add(triples.next().getSubject());
                        }
                    }
                }
        );
        return subjects;
    }
}
