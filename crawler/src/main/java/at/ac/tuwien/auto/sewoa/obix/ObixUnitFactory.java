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

package at.ac.tuwien.auto.sewoa.obix;

import at.ac.tuwien.auto.sewoa.http.HttpRetrieverImpl;
import at.ac.tuwien.auto.sewoa.obix.data.ObixUnit;

import java.util.HashMap;

public class ObixUnitFactory {

    private HashMap<String, ObixUnit> units = new HashMap<String, ObixUnit>();
    private String prefix;
    private ObixUnitParser parser;

    public ObixUnitFactory(String prefix, ObixUnitParser parser) {
        this.prefix = prefix;
        this.parser = parser;
    }

    public String getUnitSymbol(String unitHref){
        if (!units.containsKey(unitHref)){
            byte[] bytes = new HttpRetrieverImpl().retrieveData(prefix + unitHref);
            ObixUnit newUnit = parser.parseUnit(new String(bytes));
            if (newUnit != null){
                units.put(unitHref, newUnit);
            }
        }
        ObixUnit obixUnit = units.get(unitHref);
        if (obixUnit != null) {
            return obixUnit.getSymbol();
        }

        return "";
    }
}
