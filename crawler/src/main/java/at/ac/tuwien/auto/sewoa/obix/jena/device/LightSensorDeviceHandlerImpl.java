package at.ac.tuwien.auto.sewoa.obix.jena.device;

/**
 * Created by karinigor on 30.01.2016.
 */
public class LightSensorDeviceHandlerImpl extends TempSensorDeviceHandlerImpl {


    public DeviceType getDeviceType() {
        return DeviceType.LIGHT_SENSOR;
    }
}
