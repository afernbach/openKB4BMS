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

import at.ac.tuwien.auto.sewoa.obix.jena.device.DeviceType;
import com.hp.hpl.jena.ontology.Individual;

import java.util.concurrent.Semaphore;

public class ObixIndividual {

    private DeviceType deviceType;
    private boolean obixUpdateRequired;
    private Individual individual;

    private Semaphore semaphore;

    public ObixIndividual(Individual individual, DeviceType deviceType) {
        this.individual = individual;
        this.deviceType = deviceType;
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

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public synchronized Semaphore getSemaphore() {
        return semaphore;
    }
}
