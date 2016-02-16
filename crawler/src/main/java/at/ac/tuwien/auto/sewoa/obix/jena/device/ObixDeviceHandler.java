package at.ac.tuwien.auto.sewoa.obix.jena.device;

import at.ac.tuwien.auto.sewoa.obix.ObixUnitFactory;
import at.ac.tuwien.auto.sewoa.obix.ObixWatcher;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOutListItem;
import at.ac.tuwien.auto.sewoa.obix.jena.ObixIndividual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Statement;

import java.util.HashMap;

/**
 * Created by karinigor on 30.01.2016.
 */
public interface ObixDeviceHandler {

    DeviceType getDeviceType();
    void init(String prefix, HashMap<String, ObixIndividual> individuals, OntModel model, ObixWatcher watcher, ObixUnitFactory obixUnitFactory);
    void updateModel(ObixWatchOutListItem item, ObixIndividual individual);
    void updateObix(Statement statement);
}
