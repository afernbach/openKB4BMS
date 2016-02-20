package at.ac.tuwien.auto.sewoa.obix.jena.device;

/**
 * Created by igorpelesic on 20.02.2016.
 */
public class HumiditySensorDeviceHandlerImpl extends TempSensorDeviceHandlerImpl {

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.HUMIDITY_SENSOR;
    }
}
