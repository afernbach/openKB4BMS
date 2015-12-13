package at.ac.tuwien.auto.sewoa.obix.data;

/**
 * Created by karinigor on 06.12.2015.
 */
public class ObixOperation {

    private String name;
    private String href;
    private String in;
    private String out;

    public ObixOperation() {
    }

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

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
