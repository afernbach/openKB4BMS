package at.ac.tuwien.auto.sewoa;

import at.ac.tuwien.auto.sewoa.obix.ObixRetriever;
import at.ac.tuwien.auto.sewoa.xml.XMLParser;
import at.ac.tuwien.auto.sewoa.xslt.XsltTransformer;
import com.google.common.io.Resources;
import com.hp.hpl.jena.ontology.OntClass;
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
import com.hp.hpl.jena.util.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * User: pelesic
 * Date: 08/06/15
 * Time: 07:20
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main( String[] args )
    {

        try {
//            String obix = Resources.toString(Main.class.getResource("/test_obix.txt"), Charset.defaultCharset());
//            String response = HtmlUtils.htmlUnescape(obix);
//            System.out.println(response);
//
//            XMLParser xmlParser = new XMLParser();
//            String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
//            System.out.println(xmlParser.parse(xmlHeader+obix));
//
//            XsltTransformer xsltTransformer = new XsltTransformer();
//            String convert = xsltTransformer.convert(xmlHeader + obix);
//            System.out.println(convert);


            // Create an empty in-memory ontology model
            OntDocumentManager mgr = new OntDocumentManager();
            OntModelSpec s = new OntModelSpec( OntModelSpec.OWL_DL_MEM );
            s.setDocumentManager( mgr );
            OntModel ontologyModel = ModelFactory.createOntologyModel( s, null );
            ontologyModel.setStrictMode(false);

            // use the FileManager to open the ontology from the filesystem
            InputStream in = FileManager.get().open("sewoa_example_cleanedup.owl");
            if (in == null) {
                throw new IllegalArgumentException( "File not found"); }
            ontologyModel.read(in, "");

            InputStream instIN = FileManager.get().open("licht_buildings_out.owl");
//            InputStream locationIN = FileManager.get().open("buildings_out.owl");

            Model instances = ModelFactory.createDefaultModel();
            instances.read (instIN, "");

//            Model locations = ModelFactory.createDefaultModel();
//            locations.read (locationIN, "");

//            instances.add(locations);

            Reasoner reasoner = ReasonerRegistry.getOWLMiniReasoner();
            reasoner = reasoner.bindSchema(ontologyModel);

            InfModel model = ModelFactory.createInfModel (reasoner, instances);
//            ontologyModel.write(System.out);


//            Resource r = ontologyModel.getResource ("http://10.10.10.6:8080/parts/westfluegel");
//            for (StmtIterator sti = ontologyModel.listStatements(r, null, (RDFNode) null);
//                 sti.hasNext(); ) {
//                Statement stmt = sti.nextStatement();
//                System.out.println(" - " + PrintUtil.print(stmt));
//            }

            String queryStr = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                    "SELECT ?switch " +
                    "WHERE { " +
                    "?switch rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#OnOffLightSwitch> .\n" +
                    "?switch EnergyResourceOntology:isIn <http://localhost:8080/building/parts/treitlstrasse/parts/4stock/parts/aufputzkasten_a_lab_rechts> .}"
                    ;

            String queryStr1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX EnergyResourceOntology: <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#>\n" +
                    "SELECT ?location " +
                    "WHERE { "
//                  +  "?location rdf:type <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#DistributionBoard> .\n "
                    + "?location EnergyResourceOntology:contains <http://localhost:8080//networks/e183_1/entities/load_switch_n_510_03_n_510_04> . \n "
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
            System.out.println(queryStr1);
            QueryExecution qe = QueryExecutionFactory.create(
                    queryStr1,
                    model);


            for (ResultSet rs = qe.execSelect() ; rs.hasNext() ; ) {
                QuerySolution binding = rs.nextSolution();
                System.out.println("switch: " + binding.get("location"));
            }



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
