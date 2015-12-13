package at.ac.tuwien.auto.sewoa.obix.data;

/**
 * Created by karinigor on 06.12.2015.
 */
public class ObixReltime {

    private String name;
    private String href;
    private String val;
    private boolean writable;

    public ObixReltime() {
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

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }
}
