package at.ac.tuwien.auto.sewoa.obix.jena.device;

import at.ac.tuwien.auto.sewoa.obix.ObixUnitFactory;
import at.ac.tuwien.auto.sewoa.obix.ObixWatcher;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOutListItem;
import at.ac.tuwien.auto.sewoa.obix.jena.ObixIndividual;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import java.util.HashMap;

import static at.ac.tuwien.auto.sewoa.obix.jena.ObixSewoaModelHandler.BASE_ONTOLOGY_URL;

/**
 * Created by karinigor on 01.02.2016.
 */
public class PresenceSensorDeviceHandlerImpl implements ObixDeviceHandler {
    private String prefix;
    private HashMap<String, ObixIndividual> individuals;
    private OntModel model;
    private Property hasStateProperty;
    private Property hasStateValueProperty;
    private Property webservicePayloadProperty;
    private Property currentStateValueProperty;


    public DeviceType getDeviceType() {
        return DeviceType.PRESENCE_SENSOR;
    }

    public void init(String prefix, HashMap<String, ObixIndividual> individuals, OntModel model, ObixWatcher watcher, ObixUnitFactory obixUnitFactory) {

        this.prefix = prefix;
        this.individuals = individuals;
        this.model = model;

        Resource sensorClass = ResourceFactory.createResource(BASE_ONTOLOGY_URL + getDeviceType().getType());

        this.hasStateProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasState");
        this.hasStateValueProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasStateValue");
        this.webservicePayloadProperty = model.getProperty(BASE_ONTOLOGY_URL + "webservicePayload");
        this.currentStateValueProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasCurrentStateValue");


        ExtendedIterator<Individual> individualExtendedIterator = model.listIndividuals(sensorClass);
        while(individualExtendedIterator.hasNext()){
            Individual next = individualExtendedIterator.next();
            individuals.put(next.toString().replace(prefix, ""), new ObixIndividual(next, getDeviceType()));
            watcher.addInstance(next.toString());
            System.out.println("added individual: " + next);
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
            }
        }
    }

    public void updateObix(Statement statement) {

    }
}
