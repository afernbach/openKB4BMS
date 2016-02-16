package at.ac.tuwien.auto.sewoa.obix.jena.device;

import at.ac.tuwien.auto.sewoa.http.HttpRetrieverImpl;
import at.ac.tuwien.auto.sewoa.obix.ObixUnitFactory;
import at.ac.tuwien.auto.sewoa.obix.ObixWatcher;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOutListItem;
import at.ac.tuwien.auto.sewoa.obix.jena.ObixIndividual;
import static at.ac.tuwien.auto.sewoa.obix.jena.ObixSewoaModelHandler.BASE_ONTOLOGY_URL;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import java.util.HashMap;

/**
 * Created by karinigor on 30.01.2016.
 */
public class OnOffSwitchDeviceHandlerImpl implements ObixDeviceHandler {

    private HashMap<String, ObixIndividual> individuals;
    private OntModel model;
    private String prefix;
    private Property hasControlProperty;
    private Property hasStateProperty;
    private Property hasStateValueProperty;
    private Property webservicePayloadProperty;
    private Property currentStateValueProperty;

    public DeviceType getDeviceType() {
        return DeviceType.ON_OFF_SWITCH;
    }

    public void init(String prefix, HashMap<String, ObixIndividual> individuals, OntModel model, ObixWatcher obixWatcher, ObixUnitFactory obixUnitFactory) {
        this.prefix = prefix;
        this.individuals = individuals;
        this.model = model;

        Resource switchClass = ResourceFactory.createResource(BASE_ONTOLOGY_URL + getDeviceType().getType());
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
            individuals.put(object.toString().replace(prefix, ""), new ObixIndividual(individual, getDeviceType()));
            obixWatcher.addInstance(object.toString());
            System.out.println("added individual: " + individual);
        }

    }

    public void updateModel(ObixWatchOutListItem item, ObixIndividual obixIndividual) {
        Individual individual = obixIndividual.getIndividual();
        Statement property = individual.getProperty(hasStateProperty);
        RDFNode object = property.getObject();

        Individual stateIndividual = model.getIndividual(object.toString());
        NodeIterator nodeIterator = stateIndividual.listPropertyValues(hasStateValueProperty);
        while(nodeIterator.hasNext()) {
            RDFNode next = nodeIterator.next();
            Individual stateValueInd = model.getIndividual(next.toString());
            Statement webService = stateValueInd.getProperty(this.webservicePayloadProperty);
            RDFNode webServiceValue = webService.getObject();
            if (webServiceValue.toString().contains(item.getVal())) {
                Statement currentStateControlled = individual.getProperty(currentStateValueProperty);
                individual.removeProperty(currentStateValueProperty, currentStateControlled.getObject());
//                    System.out.println("removed1: " +  currentStateControlled.getObject().toString());
                individual.addProperty(currentStateValueProperty, stateValueInd);
//                    System.out.println("added1: " + stateValueInd.toString());

                if (individual.hasProperty(hasControlProperty)) {
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

    public void updateObix(Statement statement) {
        Individual stateInd = model.getIndividual(statement.getObject().toString());
        Statement webService = stateInd.getProperty(this.webservicePayloadProperty);
        if (webService != null){
            RDFNode webServiceValue = webService.getObject();
            System.out.println("url:" + statement.getSubject().toString() + " payload: " + webServiceValue.asLiteral().getString());
            new HttpRetrieverImpl().putData(statement.getSubject().toString(), webServiceValue.asLiteral().getString());
        } else {
            System.out.println("Change not relevant for Obix!");
        }
    }
}
