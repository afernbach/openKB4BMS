/*
 *     openKB4BMS is an open source knowledge base (KB) acting as a
 *     building management application.
 *
 *     Copyright (C) 2016
 *     Institute of Computer Aided Automation, Automation Systems Group, TU Wien.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

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
