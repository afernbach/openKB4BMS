/*
 *
 *  * 	   openKB4BMS is an open source knowledge base (KB) acting as a building
 *  *     management application.
 *  *
 *  *     Copyright (C) 2016
 *  * 	   Institute of Computer Aided Automation, Automation Systems Group, TU Wien.
 *  *
 *  *     This program is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     This program is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU General Public License
 *  *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package at.ac.tuwien.auto.sewoa.filter;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

import static at.ac.tuwien.auto.sewoa.obix.model.ObixOwlModelHandler.BASE_ONTOLOGY_URL;

/**
 * Created by igorpelesic on 21.02.2016.
 */
public class SewoaObixUpdateFilterImpl implements ObixUpdateFilter {

    public static final String VALUE_UPDATE = "StateValue";

    private final String prefix;

    public SewoaObixUpdateFilterImpl(String prefix) {
        this.prefix = prefix;
    }

    public String getUpdateObject(Statement statement) {
        boolean isObixRelevant = statement.getPredicate().toString().endsWith(VALUE_UPDATE);
        if (isObixRelevant) {
            Property valueProperty = statement.getModel().getProperty(BASE_ONTOLOGY_URL + "isValueOf");

            if (statement.getSubject().hasProperty(valueProperty)) {
                String subject = getValueOfObject(valueProperty, statement.getSubject());
                return subject;
            } else if (statement.getObject().asResource().hasProperty(valueProperty)) {
                String object = getValueOfObject(valueProperty, statement.getObject().asResource());
                return object;
            }
        }

        return null;
    }

    private String getValueOfObject(Property property, Resource resource){
        Statement valueStatement = resource.getProperty(property);
        RDFNode valueObject = valueStatement.getObject();
        return valueObject.toString().replace(prefix, "");
    }

}
