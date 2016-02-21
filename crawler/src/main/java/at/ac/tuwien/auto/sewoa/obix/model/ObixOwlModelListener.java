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

package at.ac.tuwien.auto.sewoa.obix.model;

import at.ac.tuwien.auto.sewoa.filter.ObixUpdateFilter;
import com.hp.hpl.jena.rdf.listeners.ObjectListener;
import com.hp.hpl.jena.rdf.model.Statement;

import static at.ac.tuwien.auto.sewoa.obix.model.ObixOwlModelHandler.BASE_ONTOLOGY_URL;

import java.util.HashMap;

public class ObixOwlModelListener extends ObjectListener {

    private HashMap<String, ObixIndividual> individuals;
    private ObixOwlModelHandler obixHandler;
    private ObixUpdateFilter updateFilter;

    public ObixOwlModelListener(ObixUpdateFilter updateFilter, HashMap<String, ObixIndividual> individuals, ObixOwlModelHandler obixHandler) {
        this.updateFilter = updateFilter;
        this.individuals = individuals;
        this.obixHandler = obixHandler;
    }

    public void addedStatement(Statement statement) {
        String updateObject = updateFilter.getUpdateObject(statement);
        if (updateObject != null){
            performObixUpdate(updateObject, statement);
        }
    }

    private void performObixUpdate(String individualKey, Statement statement){
        ObixIndividual obixIndividual = this.individuals.get(individualKey);
        if (obixIndividual != null && obixIndividual.isObixUpdateRequired()){
            System.out.println("added: " + statement.getObject() + " s: " + statement.getSubject());
            obixHandler.updateObix(statement, obixIndividual);
        }
    }



    public void removedStatement(Statement statement) {
        String subject = statement.getSubject().toString();
        ObixIndividual obixIndividual = this.individuals.get(subject);
        if (obixIndividual != null && obixIndividual.isObixUpdateRequired()) {
            System.out.println("removed: " + statement.getObject() + " s: " + statement.getSubject());
        }
    }

}
