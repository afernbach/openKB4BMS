package at.ac.tuwien.auto.sewoa.obix.jena;

import at.ac.tuwien.auto.sewoa.obix.ObixUnitFactory;
import at.ac.tuwien.auto.sewoa.obix.ObixWatcher;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOut;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOutListItem;
import at.ac.tuwien.auto.sewoa.obix.jena.device.DeviceType;
import at.ac.tuwien.auto.sewoa.obix.jena.device.ObixDeviceHandler;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;

import java.util.HashMap;

/**
 * Created by karinigor on 08.12.2015.
 */
public class ObixSewoaModelHandler {

    public static final String BASE_ONTOLOGY_URL = "https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#";
    private final String prefix;

    private OntModel model;
    private HashMap<String, ObixIndividual> individuals;
    private ObixWatcher obixWatcher;
    private ObixUnitFactory obixUnitFactory;
    private HashMap<DeviceType, ObixDeviceHandler> deviceHandlerMap = new HashMap<DeviceType, ObixDeviceHandler>();

    public ObixSewoaModelHandler(String prefix, HashMap<String, ObixIndividual> individuals, OntModel model, ObixUnitFactory obixUnitFactory) {
        this.prefix = prefix;
        this.individuals = individuals;
        this.model = model;
        this.obixUnitFactory = obixUnitFactory;
    }

    public void registerHandler(ObixDeviceHandler deviceHandler){
        this.deviceHandlerMap.put(deviceHandler.getDeviceType(), deviceHandler);
    }

    public void init(){
        this.obixWatcher = new ObixWatcher(this.prefix, this);
        for (ObixDeviceHandler handler : deviceHandlerMap.values()){
            handler.init(prefix,individuals, model, obixWatcher, obixUnitFactory);
        }
    }

    public void handle(ObixWatchOut obixWatchOut) {
        for(ObixWatchOutListItem item: obixWatchOut.getList()){
            ObixIndividual obixIndividual = individuals.get(item.getHref());
            try {
                obixIndividual.getSemaphore().acquire();
                obixIndividual.setObixUpdateRequired(false);
                deviceHandlerMap.get(obixIndividual.getDeviceType()).updateModel(item, obixIndividual);
                obixIndividual.setObixUpdateRequired(true);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                obixIndividual.getSemaphore().release();
            }
        }
    }

    public void updateObix(Statement statement, ObixIndividual individual) {
        deviceHandlerMap.get(individual.getDeviceType()).updateObix(statement);
    }
}
