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

import at.ac.tuwien.auto.sewoa.obix.data.ObixUnit;
import junit.framework.TestCase;

/**
 * Created by karinigor on 31.01.2016.
 */
public class ObixUnitParserTest extends TestCase {

    private String testXml = "<obj name=\"celsius\" href=\"celsius/\" is=\"obix:Unit\" displayName=\"temperature (°C)\">\n" +
            "            <str name=\"symbol\" href=\"celsius/symbol\" val=\"°C\"/>\n" +
            "            <real name=\"scale\" href=\"celsius/scale\" val=\"1.0\"/>\n" +
            "            <real name=\"offset\" href=\"celsius/offset\" val=\"-273.15\"/>\n" +
            "            <obj href=\"celsius/dimension\" is=\"obix:Dimension\">\n" +
            "                <int name=\"kg\" href=\"celsius/dimension/kg\" val=\"0\"/>\n" +
            "                <int name=\"m\" href=\"celsius/dimension/m\" val=\"0\"/>\n" +
            "                <int name=\"sec\" href=\"celsius/dimension/sec\" val=\"0\"/>\n" +
            "                <int name=\"K\" href=\"celsius/dimension/K\" val=\"1\"/>\n" +
            "                <int name=\"A\" href=\"celsius/dimension/A\" val=\"0\"/>\n" +
            "                <int name=\"mol\" href=\"celsius/dimension/mol\" val=\"0\"/>\n" +
            "                <int name=\"cd\" href=\"celsius/dimension/cd\" val=\"0\"/>\n" +
            "            </obj>\n" +
            "        </obj>";

    ObixUnitParser parser = new ObixUnitParser();

    public void testParse() throws Exception {

        ObixUnit obixUnit = parser.parseUnit(testXml);

        assertEquals("celsius", obixUnit.getName());
        assertEquals("celsius/", obixUnit.getHref());
        assertEquals("temperature (°C)", obixUnit.getDispaylName());
        assertEquals("°C", obixUnit.getSymbol());

    }

}