package at.ac.tuwien.auto.sewoa.obix.jena.device;

/**
 * Created by igorpelesic on 21.02.2016.
 */
public class OperatingHoursStateDeviceHandlerImpl extends LoadCurrentStateDeviceHandlerImpl {

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.OPERATING_HOURS_STATE;
    }
}
