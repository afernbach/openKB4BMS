package at.ac.tuwien.auto.sewoa.http;

/**
 * User: pelesic
 * Date: 19/02/15
 * Time: 16:00
 */
public interface HttpRetriever {

    byte [] retrieveData(String url);

    byte [] postData(String url, String data);
}
