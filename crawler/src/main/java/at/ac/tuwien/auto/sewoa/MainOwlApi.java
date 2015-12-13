package at.ac.tuwien.auto.sewoa;

import at.ac.tuwien.auto.sewoa.obix.ObixModelConverter;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.mindswap.pellet.KnowledgeBase;
import org.mindswap.pellet.jena.PelletInfGraph;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

/**
 * User: pelesic
 * Date: 08/06/15
 * Time: 07:20
 */
public class MainOwlApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainOwlApi.class);

    public static final String BASE_ONTOLOGY_URL = "https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#";

    public static void main(String[] args) {

        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
//        OWLOntology owlOntology = null;
        OWLOntology mergedOntology = null;
        try {

            File ontFile = new File(MainOwlApi.class.getClassLoader().getResource("sewoa_example_working.owl").toURI());
             m.loadOntologyFromOntologyDocument(ontFile);

            ObixModelConverter buildingModelConverter = new ObixModelConverter("http://localhost:8080/networks/e183_1/views/building", "buildings.xsl");
            String buildingOnt = buildingModelConverter.fetchAndConvert();
            System.out.println(buildingOnt);
            m.loadOntologyFromOntologyDocument(new ByteArrayInputStream(buildingOnt.getBytes()));

            ObixModelConverter groupModelConverter = new ObixModelConverter("http://localhost:8080/networks/e183_1/views/functional", "groups.xsl");
            String groupOnt = groupModelConverter.fetchAndConvert();
            System.out.println(groupOnt);
            m.loadOntologyFromOntologyDocument(new ByteArrayInputStream(groupOnt.getBytes()));

            OWLOntologyMerger merger = new OWLOntologyMerger(m);

            mergedOntology = merger.createMergedOntology(m, IRI.create(BASE_ONTOLOGY_URL));

        } catch (Exception e) {
            e.printStackTrace();
        }


        // create the Pellet reasoner
        PelletReasoner reasoner = PelletReasonerFactory.getInstance().createNonBufferingReasoner(mergedOntology);
//        reasoner.precomputeInferences();

        // add the reasoner as an ontology change listener
        m.addOntologyChangeListener(reasoner);

        // Get the KB from the reasoner
        KnowledgeBase kb = reasoner.getKB();
        // Create a Pellet graph using the KB from OWLAPI
        PelletInfGraph graph = new org.mindswap.pellet.jena.PelletReasoner().bind(kb);
        // Wrap the graph in a model
        InfModel model = ModelFactory.createInfModel(graph);

        OntDocumentManager mgr = new OntDocumentManager();
        OntModelSpec s = new OntModelSpec(org.mindswap.pellet.jena.PelletReasonerFactory.THE_SPEC);
        s.setDocumentManager( mgr );
        OntModel ontologyModel = ModelFactory.createOntologyModel( s, model );
        ontologyModel.setStrictMode(false);
        // Use the model to answer SPARQL queries

        System.out.println("consistent: "+ reasoner.isConsistent());
        DefaultPrefixManager defaultPrefixManager = new DefaultPrefixManager();
        defaultPrefixManager.setDefaultPrefix(BASE_ONTOLOGY_URL);


        OWLDataFactory owlDataFactory = m.getOWLDataFactory();
        OWLObjectProperty controlledObject = owlDataFactory.getOWLObjectProperty(":controlledObject", defaultPrefixManager);
        OWLClass switchClass = owlDataFactory.getOWLClass(":OnOffLightSwitch", defaultPrefixManager);

//        Set<OWLLogicalAxiom> axiomSet=mergedOntology.getLogicalAxioms();
//        Iterator<OWLLogicalAxiom> iteratorAxiom= axiomSet.iterator();
//
//        while(iteratorAxiom.hasNext()) {
//            OWLAxiom tempAx= iteratorAxiom.next();
//            if(!tempAx.getIndividualsInSignature().isEmpty()){
//                System.out.println(tempAx.getIndividualsInSignature());
//                System.out.println(tempAx.getDataPropertiesInSignature());
//                System.out.println(tempAx.getObjectPropertiesInSignature());
//            }
//        }


//        NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(switchClass, true);
//        Set<OWLNamedIndividual> flattened = instances.getFlattened();
//        for (OWLNamedIndividual individual: flattened){
//            Set<OWLObjectPropertyAssertionAxiom> objectPropertyAssertionAxioms = mergedOntology.getObjectPropertyAssertionAxioms(individual);
//            for (OWLObjectPropertyAssertionAxiom ax: objectPropertyAssertionAxioms) {
//                System.out.println(ax.getProperty());
//            }
//
//            OWLNamedIndividual lightSwitch =  owlDataFactory.getOWLNamedIndividual(individual.getIRI());
//            NodeSet<OWLNamedIndividual> propertyValues  = reasoner.getObjectPropertyValues(lightSwitch, controlledObject);
//            System.out.println("aha");
////            Set<OWLObjectProperty> objectPropertiesInSignature = lightSwitch.get();
////            for (OWLObjectProperty property: objectPropertiesInSignature){
////                System.out.println(property.getIRI());
////            }
//        }


        String queryStr1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-synt" +
                "ax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?location " +
                "WHERE { "
//                  +  "?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#DistributionBoard> .\n "
                + "?location EnergyResourceOntology:contains <http://localhost:8080//networks/e183_1/entities/load_switch_n_510_03_n_510_04> . \n "
                + "}";

        String queryStr1a = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                "SELECT ?location " +
                "WHERE { "
//                  +  "?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#DistributionBoard> .\n "
                + "?location EnergyResourceOntology:isIn <http://localhost:8080/building/parts/treitlstrasse/parts/4stock/parts/aufputzkasten_a_lab_rechts> . \n "
                + "}";

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
        System.out.println(queryStr1);
        try {
            QueryExecution qe = QueryExecutionFactory.create(
                    queryStr1,
                    ontologyModel);
//            qe.setTimeout(5000);


            for (ResultSet rs = qe.execSelect(); rs.hasNext(); ) {
                QuerySolution binding = rs.nextSolution();
                System.out.println("switch: " + binding.get("location"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


    }
}
