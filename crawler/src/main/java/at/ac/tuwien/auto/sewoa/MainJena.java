package at.ac.tuwien.auto.sewoa;

import at.ac.tuwien.auto.sewoa.jena.SewoaModelHandler;
import at.ac.tuwien.auto.sewoa.jena.SewoaModelListener;
import at.ac.tuwien.auto.sewoa.obix.ObixModelConverter;
import at.ac.tuwien.auto.sewoa.obix.ObixWatcher;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;
import org.mindswap.pellet.jena.PelletReasoner;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: pelesic
 * Date: 08/06/15
 * Time: 07:20
 */
public class MainJena {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainJena.class);

    public static void main( String[] args )
    {
        // Create an empty in-memory ontology model
        OntDocumentManager mgr = new OntDocumentManager();
        OntModelSpec s = new OntModelSpec(PelletReasonerFactory.THE_SPEC);
        s.setDocumentManager( mgr );
        OntModel ontologyModel = ModelFactory.createOntologyModel( s, null );
        ontologyModel.setStrictMode(false);
        ontologyModel.register(new SewoaModelListener());


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

        SewoaModelHandler handler = new SewoaModelHandler("http://localhost:8080", ontologyModel);

        sparqlQuery(ontologyModel);
        while(true){
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sparqlQuery(ontologyModel);
        }


    }

    private static void sparqlQuery(OntModel model){
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
                + "?type rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#OffStateValue> . \n "
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
        System.out.println(queryStr1b);
        try {
            QueryExecution qe = QueryExecutionFactory.create(
                    queryStr1b,
                    model);
//            qe.setTimeout(10000);


            for (ResultSet rs = qe.execSelect() ; rs.hasNext() ; ) {
                QuerySolution binding = rs.nextSolution();
                System.out.println("switch: " + binding.get("switch"));
            }
            System.out.println("Query finished!");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
