package at.ac.tuwien.auto.sewoa.http;

import org.apache.commons.httpclient.HttpClient;

/**
 * User: pelesic
 * Date: 06/11/14
 * Time: 04:06
 */
public class HttpClientFactory {

    public HttpClient createClient(){
        return new HttpClient();
    }
}
