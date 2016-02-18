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

import at.ac.tuwien.auto.sewoa.obix.data.*;
import at.ac.tuwien.auto.sewoa.xml.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ObixUnitParser {

    private XMLParser xmlParser;

    public ObixUnitParser() {
        this.xmlParser = new XMLParser();
    }

    /*
        <obj name="celsius" href="celsius/" is="obix:Unit" displayName="temperature (°C)">
            <str name="symbol" href="celsius/symbol" val="°C"/>
            <real name="scale" href="celsius/scale" val="1.0"/>
            <real name="offset" href="celsius/offset" val="-273.15"/>
            <obj href="celsius/dimension" is="obix:Dimension">
                <int name="kg" href="celsius/dimension/kg" val="0"/>
                <int name="m" href="celsius/dimension/m" val="0"/>
                <int name="sec" href="celsius/dimension/sec" val="0"/>
                <int name="K" href="celsius/dimension/K" val="1"/>
                <int name="A" href="celsius/dimension/A" val="0"/>
                <int name="mol" href="celsius/dimension/mol" val="0"/>
                <int name="cd" href="celsius/dimension/cd" val="0"/>
            </obj>
        </obj>
     */
    public ObixUnit parseUnit(String input){
        ObixUnit obixUnit = new ObixUnit();
        Document document = xmlParser.parse(input);
        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("obj")){
                if (node.getAttributes().getNamedItem("is").getNodeValue().equals("obix:Unit")){
                    obixUnit.setName(node.getAttributes().getNamedItem("name").getNodeValue());
                    obixUnit.setHref(node.getAttributes().getNamedItem("href").getNodeValue());
                    obixUnit.setDispaylName(node.getAttributes().getNamedItem("displayName").getNodeValue());
                }
            } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("str")){
                obixUnit.setSymbol(node.getAttributes().getNamedItem("val").getNodeValue());
            }
        }
        return obixUnit;
    }

}
