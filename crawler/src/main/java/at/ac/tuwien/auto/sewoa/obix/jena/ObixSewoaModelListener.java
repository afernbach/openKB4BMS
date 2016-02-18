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

package at.ac.tuwien.auto.sewoa.obix.jena;

import com.hp.hpl.jena.rdf.listeners.ObjectListener;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

import static at.ac.tuwien.auto.sewoa.obix.jena.ObixSewoaModelHandler.BASE_ONTOLOGY_URL;

import java.util.HashMap;

public class ObixSewoaModelListener extends ObjectListener {

    public static final String VALUE_UPDATE = "StateValue";

    private final String prefix;
    private HashMap<String, ObixIndividual> individuals;
    private ObixSewoaModelHandler obixHandler;

    public ObixSewoaModelListener(String prefix, HashMap<String, ObixIndividual> individuals, ObixSewoaModelHandler obixHandler) {
        this.prefix = prefix;
        this.individuals = individuals;
        this.obixHandler = obixHandler;
    }

    public void addedStatement(Statement statement) {
        boolean isObixRelevant = statement.getPredicate().toString().endsWith(VALUE_UPDATE);
        if (isObixRelevant) {
            Property valueProperty = statement.getModel().getProperty(BASE_ONTOLOGY_URL + "isValueOf");

            if (statement.getSubject().hasProperty(valueProperty)) {
                String subject = getValueOfObject(valueProperty, statement.getSubject());
                performObixUpdate(subject, statement);
            } else if (statement.getObject().asResource().hasProperty(valueProperty)) {
                String object = getValueOfObject(valueProperty, statement.getObject().asResource());
                performObixUpdate(object, statement);
            }
        }
    }

    private String getValueOfObject(Property property, Resource resource){
        Statement valueStatement = resource.getProperty(property);
        RDFNode valueObject = valueStatement.getObject();
        return valueObject.toString().replace(prefix, "");
    }

    private void performObixUpdate(String individualKey, Statement statement){
        ObixIndividual obixIndividual = this.individuals.get(individualKey);
//        System.out.println(obixIndividual);
        if (obixIndividual != null && obixIndividual.isObixUpdateRequired()){
            System.out.println("added: " + statement.getObject() + " s: " + statement.getSubject());
            obixHandler.updateObix(statement, obixIndividual);
        }
    }



    public void removedStatement(Statement statement) {
        String subject = statement.getSubject().toString().replace(prefix, "");
        ObixIndividual obixIndividual = this.individuals.get(subject);
        if (obixIndividual != null && obixIndividual.isObixUpdateRequired()) {
            System.out.println("removed: " + statement.getObject() + " s: " + statement.getSubject());
        }
    }

}
