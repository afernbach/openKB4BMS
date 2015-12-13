package at.ac.tuwien.auto.sewoa.obix;

import at.ac.tuwien.auto.sewoa.http.HttpRetriever;
import at.ac.tuwien.auto.sewoa.http.HttpRetrieverImpl;

/**
 * User: pelesic
 * Date: 01/07/15
 * Time: 20:48
 */
public class ObixRetriever {

    public static final String ENCODING = "UTF-8";
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    private String restURL;
    private HttpRetriever httpRetriever;

    public ObixRetriever(String restURL) {
        this.restURL = restURL;
        this.httpRetriever = new HttpRetrieverImpl();
    }

    public String retrieveObix() {
        byte[] bytes = httpRetriever.retrieveData(restURL);
        return XML_HEADER + new String(bytes);
    }
}
