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
 * Created by igorpelesic on 21.02.2016.
 */
public class LoadCurrentStateDeviceHandlerImpl implements ObixDeviceHandler {

    protected HashMap<String, ObixIndividual> individuals;
    protected OntModel model;
    protected Property realStateValue;

    public DeviceType getDeviceType() {
        return DeviceType.LOAD_CURRENT_STATE;
    }

    public void init(String prefix, HashMap<String, ObixIndividual> individuals, OntModel model, ObixWatcher watcher, ObixUnitFactory obixUnitFactory) {
        this.individuals = individuals;
        this.model = model;

        Resource stateClass = ResourceFactory.createResource(BASE_ONTOLOGY_URL + getDeviceType().getType());

        ExtendedIterator<Individual> individualExtendedIterator = model.listIndividuals(stateClass);
        while(individualExtendedIterator.hasNext()){
            Individual next = individualExtendedIterator.next();
            individuals.put(next.toString().replace(prefix, ""), new ObixIndividual(next, getDeviceType()));
            watcher.addInstance(next.toString());

            System.out.println("added individual: " + next);
        }



    }

    public void updateModel(ObixWatchOutListItem item, ObixIndividual obixIndividual) {
        Individual individual = obixIndividual.getIndividual();

        individual.removeAll(realStateValue);
        individual.addProperty(realStateValue, item.getVal());
    }

    public void updateObix(Statement statement) {

    }
}
