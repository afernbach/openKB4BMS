package at.ac.tuwien.auto.sewoa.obix.jena.device;

import at.ac.tuwien.auto.sewoa.http.HttpRetrieverImpl;
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
public class TempControllerDeviceHandlerImpl extends TempSensorDeviceHandlerImpl {

    public static final String PLACEHOLDER = "$value";

    public DeviceType getDeviceType() {
        return DeviceType.TEMP_CONTROL;
    }

    public void updateObix(Statement statement) {
        System.out.println(statement.getString());
//        statement.getObject().asResource().hasProperty()
        Individual stateInd = model.getIndividual(statement.getSubject().toString());
        Property webServicePayloadProperty = model.getProperty(BASE_ONTOLOGY_URL + "webservicePayload");
        Statement webService = stateInd.getProperty(webServicePayloadProperty);
        if (webService != null){
            RDFNode webServiceValue = webService.getObject();
            Statement realValue = stateInd.getProperty(this.realStateValue);
            RDFNode realValueObject = realValue.getObject();
            String payload = webServiceValue.asLiteral().getString().replace(PLACEHOLDER, realValueObject.asLiteral().getString());
            System.out.println("url:" + statement.getSubject().toString() + " payload: " + payload);
            new HttpRetrieverImpl().putData(statement.getSubject().toString(), payload);
        } else {
            System.out.println("Change not relevant for Obix!!!");
        }
    }
}
