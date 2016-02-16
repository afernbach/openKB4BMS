package at.ac.tuwien.auto.sewoa.obix;

import at.ac.tuwien.auto.sewoa.http.HttpRetrieverImpl;
import at.ac.tuwien.auto.sewoa.obix.data.ObixUnit;

import java.util.HashMap;

/**
 * Created by karinigor on 31.01.2016.
 */
public class ObixUnitFactory {

    private HashMap<String, ObixUnit> units = new HashMap<String, ObixUnit>();
    private String prefix;
    private ObixUnitParser parser;

    public ObixUnitFactory(String prefix, ObixUnitParser parser) {
        this.prefix = prefix;
        this.parser = parser;
    }

    public String getUnitSymbol(String unitHref){
        if (!units.containsKey(unitHref)){
            byte[] bytes = new HttpRetrieverImpl().retrieveData(prefix + unitHref);
            ObixUnit newUnit = parser.parseUnit(new String(bytes));
            if (newUnit != null){
                units.put(unitHref, newUnit);
            }
        }
        ObixUnit obixUnit = units.get(unitHref);
        if (obixUnit != null) {
            return obixUnit.getSymbol();
        }

        return "";
    }
}
