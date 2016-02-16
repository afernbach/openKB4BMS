package at.ac.tuwien.auto.sewoa.obix.jena.device;

/**
 * Created by karinigor on 30.01.2016.
 */
public enum DeviceType {
    ON_OFF_SWITCH("OnOffLightSwitch"),
    TEMP_SENSOR("TemperatureSensor"),
    TEMP_CONTROL("TemperatureController"),
    LIGHT_SENSOR("LightSensor"),
    PRESENCE_SENSOR("PresenceSensor");

    private String type;

    DeviceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
