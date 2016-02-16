package at.ac.tuwien.auto.sewoa.obix;

import at.ac.tuwien.auto.sewoa.http.HttpRetriever;
import at.ac.tuwien.auto.sewoa.http.HttpRetrieverImpl;
import at.ac.tuwien.auto.sewoa.obix.jena.ObixSewoaModelHandler;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatch;
import at.ac.tuwien.auto.sewoa.obix.data.ObixWatchOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by karinigor on 06.12.2015.
 */
public class ObixWatcher {

    public static final String ADD_TEMPLATE =
            "<obj is=\"obix:WatchIn\">\n" +
            "  <list name=\"hrefs\">\n" +
            "       $uris" +
            "  </list>\n" +
            "</obj>";

    public static final String URI_TEMPLATE = "   <uri val=\"$device\" />\n";
    public static final String MAKE_URL_TEMPLATE = "/watchService/make";
    public static final String ADD_URL_TEMPLATE = " /$watch/add";

    private String baseURL;
    private HttpRetriever httpRetriever;
    private ObixWatchParser parser;
    private ObixWatch obixWatch;
    private List<String> instances;
    private ObixSewoaModelHandler handler;

    public ObixWatcher(String url, ObixSewoaModelHandler handler){
        this.baseURL = url;
        instances = new ArrayList<String>();
        this.httpRetriever = new HttpRetrieverImpl();
        this.parser = new ObixWatchParser();
        byte[] response = this.httpRetriever.postData(baseURL + MAKE_URL_TEMPLATE, "");
        this.obixWatch = parser.parseWatch(new String(response));
        this.handler = handler;
    }

    private TimerTask pollTimerTask(final HttpRetriever retriever, final ObixWatch watch){
        return new TimerTask() {
            @Override
            public void run() {
                byte[] pollChanges = retriever.postData(baseURL + obixWatch.getOperations().get("pollChanges").getHref(), "");
                ObixWatchOut obixWatchOut = parser.parseWatchOut(new String(pollChanges));
                handler.handle(obixWatchOut);
                if (obixWatchOut.getList().size() > 0) {
                    System.out.println(new String(pollChanges));
                }
            }
        };
    }


    public boolean addInstance(String url){
        instances.add(url);

        String watchAddUrl = baseURL + obixWatch.getOperations().get("add").getHref();
        System.out.println(watchAddUrl);

        String device = URI_TEMPLATE.replace("$device", url);
        String payload = ADD_TEMPLATE.replace("$uris", device);
        System.out.println(payload);

        byte[] bytes = httpRetriever.postData(watchAddUrl, payload);
        System.out.println(new String(bytes));
        handler.handle(this.parser.parseWatchOut(new String(bytes)));

        if (instances.size() == 1){
            new Timer().scheduleAtFixedRate(pollTimerTask(this.httpRetriever, this.obixWatch), 0, 10000);
        }


        return false;
    }



}
