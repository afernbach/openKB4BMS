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

package at.ac.tuwien.auto.sewoa.adapter;

import at.ac.tuwien.auto.sewoa.obix.ObixUnitFactory;
import at.ac.tuwien.auto.sewoa.obix.ObixWatcher;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOutListItem;
import at.ac.tuwien.auto.sewoa.obix.model.ObixIndividual;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import java.util.HashMap;

import static at.ac.tuwien.auto.sewoa.obix.model.ObixOwlModelHandler.BASE_ONTOLOGY_URL;

public class TempSensorModelAdapterImpl implements ObixModelAdapter {

    protected HashMap<String, ObixIndividual> individuals;
    protected OntModel model;
    protected ObixUnitFactory obixUnitFactory;
    protected Property realStateValue;
    protected Property currentStateValueProperty;
    protected Property hasNativeUnitProperty;

    public ModelType getModelType() {
        return ModelType.TEMP_SENSOR;
    }

    public void init(String prefix, HashMap<String, ObixIndividual> individuals, OntModel model, ObixWatcher obixWatcher, ObixUnitFactory obixUnitFactory) {
        this.individuals = individuals;
        this.model = model;
        this.obixUnitFactory = obixUnitFactory;

        Resource tempSensorClass = ResourceFactory.createResource(BASE_ONTOLOGY_URL + getModelType().getType());

        this.realStateValue = model.getProperty(BASE_ONTOLOGY_URL + "realStateValue");
        this.hasNativeUnitProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasNativeUnit");
        this.currentStateValueProperty = model.getProperty(BASE_ONTOLOGY_URL + "hasCurrentStateValue");


        ExtendedIterator<Individual> individualExtendedIterator = model.listIndividuals(tempSensorClass);
        while(individualExtendedIterator.hasNext()){
            Individual next = individualExtendedIterator.next();
            individuals.put(next.toString().replace(prefix, ""), new ObixIndividual(next, getModelType()));
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
