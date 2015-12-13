package at.ac.tuwien.auto.sewoa.jena;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelChangedListener;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import java.util.List;

/**
 * Created by karinigor on 06.12.2015.
 */
public class SewoaModelListener implements ModelChangedListener {
    public void addedStatement(Statement statement) {
        System.out.println("added: " + statement.getObject());
    }

    public void addedStatements(Statement[] statements) {

    }

    public void addedStatements(List<Statement> list) {

    }

    public void addedStatements(StmtIterator stmtIterator) {

    }

    public void addedStatements(Model model) {

    }

    public void removedStatement(Statement statement) {
        System.out.println("removed: " + statement.getObject());
    }

    public void removedStatements(Statement[] statements) {

    }

    public void removedStatements(List<Statement> list) {

    }

    public void removedStatements(StmtIterator stmtIterator) {

    }

    public void removedStatements(Model model) {

    }

    public void notifyEvent(Model model, Object o) {

    }
}
