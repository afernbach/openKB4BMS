package at.ac.tuwien.auto.sewoa.obix.jena;

import at.ac.tuwien.auto.sewoa.obix.jena.device.DeviceType;
import com.hp.hpl.jena.ontology.Individual;

import java.util.concurrent.Semaphore;

/**
 * Created by karinigor on 28.12.2015.
 */
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
