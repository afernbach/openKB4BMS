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

package at.ac.tuwien.auto.sewoa.obix.jena.device;

public enum DeviceType {
    ON_OFF_SWITCH("OnOffLightSwitch"),
    TEMP_SENSOR("TemperatureSensor"),
    TEMP_CONTROL("TemperatureController"),
    LIGHT_SENSOR("LightSensor"),
    PRESENCE_SENSOR("PresenceSensor"),
    CO2_SENSOR("CO2Sensor"),
    HUMIDITY_SENSOR("HumiditySensor"),
    LOAD_CURRENT_STATE("LoadCurrentStateValue");

    private String type;

    DeviceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
