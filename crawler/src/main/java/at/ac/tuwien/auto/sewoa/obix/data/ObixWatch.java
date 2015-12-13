package at.ac.tuwien.auto.sewoa.obix.data;

import java.util.HashMap;

/**
 * Created by karinigor on 06.12.2015.
 */
public class ObixWatch {

    private String name;
    private String href;
    private String is;
    private ObixReltime reltime;
    private HashMap<String, ObixOperation> operations = new HashMap<String, ObixOperation>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    public ObixReltime getReltime() {
        return reltime;
    }

    public void setReltime(ObixReltime reltime) {
        this.reltime = reltime;
    }

    public HashMap<String, ObixOperation> getOperations() {
        return operations;
    }

    public void setOperations(HashMap<String, ObixOperation> operations) {
        this.operations = operations;
    }

    public void addOperation(ObixOperation operation){
        operations.put(operation.getName(), operation);
    }
}
