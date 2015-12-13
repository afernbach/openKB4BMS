package at.ac.tuwien.auto.sewoa.jena;

import at.ac.tuwien.auto.sewoa.obix.ObixWatcher;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOut;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOutListItem;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import java.util.HashMap;

/**
 * Created by karinigor on 08.12.2015.
 */
public class SewoaModelHandler {

    public static final String BASE_ONTOLOGY_URL = "https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#";
    private final String prefix;

    private OntModel model;
    private HashMap<String, Individual> individuals = new HashMap<String, Individual>();
    private Property hasStateValueProperty;
    private Property hasStateProperty;
    private ObixWatcher obixWatcher;
    private Property webservicePayloadProperty;
    private Property currentStateValueProperty;
    private Property hasControlProperty;

    public SewoaModelHandler(String prefix, OntModel model) {
        this.prefix = prefix;
        this.model = model;
        init();
    }

    private void init(){
        this.obixWatcher = new ObixWatcher(this.prefix, this);

        Resource switchClass = ResourceFactory.createResource(BASE_ONTOLOGY_URL + "OnOffLightSwitch");
        Property controlledObjectProperty = model.getProperty(BASE_ONTOLOGY_URL + "controlledObject");
         this.hasControlProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasControl");
         this.hasStateProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasState");
         this.hasStateValueProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasStateValue");
         this.webservicePayloadProperty = model.getProperty(BASE_ONTOLOGY_URL + "webservicePayload");
         this.currentStateValueProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasCurrentStateValue");


        ExtendedIterator<Individual> individualExtendedIterator = model.listIndividuals(switchClass);
        while(individualExtendedIterator.hasNext()){
            Individual next = individualExtendedIterator.next();
            Statement property = next.getProperty(controlledObjectProperty);
            RDFNode object = property.getObject();
            Individual individual = model.getIndividual(object.toString());
            individuals.put(object.toString().replace(prefix, ""), individual);
            obixWatcher.addInstance(object.toString());
            System.out.println("added individual: " + individual);
        }

    }

    public void handle(ObixWatchOut obixWatchOut) {
        for(ObixWatchOutListItem item: obixWatchOut.getList()){
            Individual individual = individuals.get(item.getHref());
            Statement property = individual.getProperty(hasStateProperty);
            RDFNode object = property.getObject();

            Individual stateIndividual = model.getIndividual(object.toString());
            NodeIterator nodeIterator = stateIndividual.listPropertyValues(hasStateValueProperty);
            while(nodeIterator.hasNext()){
                RDFNode next = nodeIterator.next();
                Individual stateValueInd = model.getIndividual(next.toString());
                Statement webService = stateValueInd.getProperty(this.webservicePayloadProperty);
                RDFNode webServiceValue = webService.getObject();
                if (webServiceValue.toString().contains(item.getVal())){
                    Statement currentStateControlled = individual.getProperty(currentStateValueProperty);
                    individual.removeProperty(currentStateValueProperty, currentStateControlled.getObject());
//                    System.out.println("removed1: " +  currentStateControlled.getObject().toString());
                    individual.addProperty(currentStateValueProperty, stateValueInd);
//                    System.out.println("added1: " + stateValueInd.toString());

                    if(individual.hasProperty(hasControlProperty)){
                        Statement hasControl = individual.getProperty(hasControlProperty);
                        RDFNode object1 = hasControl.getObject();
                        Individual controllingInd = model.getIndividual(object1.toString());

                        Statement currentState = controllingInd.getProperty(currentStateValueProperty);
                        controllingInd.removeProperty(currentStateValueProperty, currentState.getObject());
//                        System.out.println("removed2: " +  currentState.getObject().toString());
                        controllingInd.addProperty(currentStateValueProperty, stateValueInd);
//                        System.out.println("added2: " + stateValueInd.toString());
                    }
                }
            }

        }
    }
}
