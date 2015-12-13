package at.ac.tuwien.auto.sewoa.obix.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karinigor on 08.12.2015.
 */
public class ObixWatchOut {

    private List<ObixWatchOutListItem> list;

    public ObixWatchOut() {
        this.list = new ArrayList<ObixWatchOutListItem>();
    }

    public List<ObixWatchOutListItem> getList() {
        return list;
    }

    public void setList(List<ObixWatchOutListItem> list) {
        this.list = list;
    }

    public void addItem(ObixWatchOutListItem item){
        this.list.add(item);
    }
}
