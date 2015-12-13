package at.ac.tuwien.auto.sewoa.obix;

import at.ac.tuwien.auto.sewoa.obix.data.ObixOperation;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatch;
import at.ac.tuwien.auto.sewoa.xml.XMLParser;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by karinigor on 06.12.2015.
 */
public class ObixWatcherTest extends TestCase {

    private String makeAnswer = "<obj href=\"/watch1\" is=\"obix:Watch\">\n"+
            "<op name=\"add\" href=\"/watch1/add\" in=\"obix:WatchIn\" out=\"obix:WatchOut\" />\n"+
            "<op name=\"remove\" href=\"/watch1/remove\" in=\"obix:WatchIn\" out=\"obix:Nil\" />\n"+
            "<reltime name=\"lease\" href=\"/watch1/lease\" val=\"PT60S\" writable=\"true\" />\n"+
            "<op name=\"pollChanges\" href=\"/watch1/pollChanges\" in=\"obix:WatchIn\" out=\"obix:WatchOut\" />\n"+
            "<op name=\"pollRefresh\" href=\"/watch1/pollRefresh\" in=\"obix:WatchIn\" out=\"obix:WatchOut\" />\n"+
            "<op name=\"delete\" href=\"/watch1/delete\" in=\"obix:Nil\" out=\"obix:Nil\" />\n"+
            " </obj>";

    public void testParse() throws Exception {


    }
}