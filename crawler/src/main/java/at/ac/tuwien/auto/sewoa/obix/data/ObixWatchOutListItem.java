package at.ac.tuwien.auto.sewoa.obix.data;

/**
 * Created by karinigor on 08.12.2015.
 */
public class ObixWatchOutListItem {

    private String type;
    private String href;
    private String val;
    private String displayName;
    private boolean writable;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }
}
