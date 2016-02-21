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

package at.ac.tuwien.auto.sewoa.obix.model;

import at.ac.tuwien.auto.sewoa.adapter.ModelType;
import com.hp.hpl.jena.ontology.Individual;

import java.util.concurrent.Semaphore;

public class ObixIndividual {

    private ModelType modelType;
    private boolean obixUpdateRequired;
    private Individual individual;

    private Semaphore semaphore;

    public ObixIndividual(Individual individual, ModelType modelType) {
        this.individual = individual;
        this.modelType = modelType;
        this.obixUpdateRequired = true;
        this.semaphore = new Semaphore(1);
    }

    public synchronized boolean isObixUpdateRequired() {
        return obixUpdateRequired;
    }

    public synchronized void setObixUpdateRequired(boolean obixUpdateRequired) {
        this.obixUpdateRequired = obixUpdateRequired;
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }

    public synchronized Semaphore getSemaphore() {
        return semaphore;
    }
}
