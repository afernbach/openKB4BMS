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

import at.ac.tuwien.auto.sewoa.obix.data.ObixOperation;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatch;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOut;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOutListItem;
import at.ac.tuwien.auto.sewoa.xml.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ObixWatchParser {

    private XMLParser xmlParser;

    public ObixWatchParser() {
        this.xmlParser = new XMLParser();
    }

    public ObixWatch parseWatch(String input){
        ObixWatch obixWatch = new ObixWatch();
        Document document = xmlParser.parse(input);
        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getAttributes().getNamedItem("name") == null) {
                obixWatch.setHref(node.getAttributes().getNamedItem("href").getNodeValue());
                obixWatch.setName(obixWatch.getHref().substring(1));
            }
            else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("op")) {
                // do something with the current element
                ObixOperation operation = getObixOperation(node);
                obixWatch.addOperation(operation);
            }
        }

        return obixWatch;
    }

    private ObixOperation getObixOperation(Node node) {
        ObixOperation operation = new ObixOperation();
        operation.setName(node.getAttributes().getNamedItem("name").getNodeValue());
        operation.setHref(node.getAttributes().getNamedItem("href").getNodeValue());
        return operation;
    }

    /*
    <obj is="obix:WatchOut">
    <list>
    <bool href="/networks/e183_1/entities/load_switch_n_510_03_n_510_04/1/datapoints/switch_channel_c/value" val="true" displayName="On / Off" writable="true"/>
    </list>
    </obj>
*/
    public ObixWatchOut parseWatchOut(String input){
        ObixWatchOut result = new ObixWatchOut();
        Document document = xmlParser.parse(input);
        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
             if (node.getNodeType() == Node.ELEMENT_NODE && (node.getNodeName().equals("bool") || node.getNodeName().equals("real"))) {
                // do something with the current element
                 ObixWatchOutListItem item = new ObixWatchOutListItem();
                 item.setType(node.getNodeName());
                 item.setDisplayName(node.getAttributes().getNamedItem("displayName").getNodeValue());
                 item.setHref(node.getAttributes().getNamedItem("href").getNodeValue());
                 item.setVal(node.getAttributes().getNamedItem("val").getNodeValue());
                 Node writable = node.getAttributes().getNamedItem("writable");
                 if (writable != null) {
                     item.setWritable(Boolean.valueOf(writable.getNodeValue()));
                 }
                 Node unitNode = node.getAttributes().getNamedItem("unit");
                 if (unitNode != null){
                     item.setUnit(unitNode.getNodeValue());
                 }
                 result.addItem(item);
            }
        }

        return result;
    }

}
