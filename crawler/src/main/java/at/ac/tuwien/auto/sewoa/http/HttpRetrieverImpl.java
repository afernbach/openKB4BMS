package at.ac.tuwien.auto.sewoa.http;

import com.google.common.io.ByteStreams;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

/**
 * User: pelesic
 * Date: 19/02/15
 * Time: 16:00
 */
public class HttpRetrieverImpl implements HttpRetriever {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRetrieverImpl.class);

    private static final int CONNECTION_TO = 3;
    private static final int SOCKET_TO = 4;

    private HttpClientFactory httpClientFactory = new HttpClientFactory();

    public byte[] retrieveData(String url) {
        byte[] result = new byte[]{};
        HttpClient httpClient = httpClientFactory.createClient();
        GetMethod getMethod = new GetMethod(url);

        HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
        params.setConnectionTimeout((int) TimeUnit.SECONDS.toMillis(CONNECTION_TO));
        params.setSoTimeout((int) TimeUnit.SECONDS.toMillis(SOCKET_TO));
        getMethod.addRequestHeader("Content-Type", "application/xml");
        getMethod.addRequestHeader("Accept", "application/xml");

        try {
            result = send(httpClient, getMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte[] postData(String url, String data) {
        byte[] result = new byte[]{};
        HttpClient httpClient = httpClientFactory.createClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestEntity(new StringRequestEntity(data));

        HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
        params.setConnectionTimeout((int) TimeUnit.SECONDS.toMillis(CONNECTION_TO));
        params.setSoTimeout((int) TimeUnit.SECONDS.toMillis(SOCKET_TO));
        postMethod.addRequestHeader("Content-Type", "application/xml");
        postMethod.addRequestHeader("Accept", "application/xml");

        try {
            result = send(httpClient, postMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private byte[] send(HttpClient httpClient, HttpMethod method){
        try {
            int httpResult = httpClient.executeMethod(method);
            if(httpResult != HttpURLConnection.HTTP_OK) {
                LOGGER.warn("cannot post data to url {} -> result: {}.", method.getURI().toString(), httpResult);
                ByteStreams.toByteArray(method.getResponseBodyAsStream());
                throw new IllegalStateException("error code");
            } else {
                return ByteStreams.toByteArray(method.getResponseBodyAsStream());
            }
        } catch (Exception e) {
            LOGGER.warn("cannot post http data to url {} due to {}.", method.toString(), e.getMessage());
            throw new IllegalStateException("could not post data");
        }
    }
}
