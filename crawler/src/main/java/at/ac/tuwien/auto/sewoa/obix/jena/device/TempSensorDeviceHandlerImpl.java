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
 * Created by karinigor on 30.01.2016.
 */
public class TempSensorDeviceHandlerImpl implements ObixDeviceHandler {

    protected HashMap<String, ObixIndividual> individuals;
    protected OntModel model;
    protected ObixUnitFactory obixUnitFactory;
    protected Property realStateValue;
    protected Property currentStateValueProperty;
    protected Property hasNativeUnitProperty;

    public DeviceType getDeviceType() {
        return DeviceType.TEMP_SENSOR;
    }

    public void init(String prefix, HashMap<String, ObixIndividual> individuals, OntModel model, ObixWatcher obixWatcher, ObixUnitFactory obixUnitFactory) {
        this.individuals = individuals;
        this.model = model;
        this.obixUnitFactory = obixUnitFactory;

        Resource tempSensorClass = ResourceFactory.createResource(BASE_ONTOLOGY_URL + getDeviceType().getType());

        this.realStateValue = model.getProperty(BASE_ONTOLOGY_URL + "realStateValue");
        this.hasNativeUnitProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasNativeUnit");
        this.currentStateValueProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasCurrentStateValue");


        ExtendedIterator<Individual> individualExtendedIterator = model.listIndividuals(tempSensorClass);
        while(individualExtendedIterator.hasNext()){
            Individual next = individualExtendedIterator.next();
            individuals.put(next.toString().replace(prefix, ""), new ObixIndividual(next, getDeviceType()));
            obixWatcher.addInstance(next.toString());

            System.out.println("added individual: " + next);
        }

    }

    public void updateModel(ObixWatchOutListItem item, ObixIndividual obixIndividual) {
        Individual individual = obixIndividual.getIndividual();

        Statement currentState = individual.getProperty(currentStateValueProperty);
        RDFNode object = currentState.getObject();
        Individual currentStateInd = model.getIndividual(object.toString());

        currentStateInd.removeAll(realStateValue);
        currentStateInd.addProperty(realStateValue, item.getVal());

        if (item.getUnit() != null) {
            currentStateInd.removeAll(hasNativeUnitProperty);
            currentStateInd.addProperty(hasNativeUnitProperty, obixUnitFactory.getUnitSymbol(item.getUnit()));
        }
    }

    public void updateObix(Statement statement) {

    }
}
