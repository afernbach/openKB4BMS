/*
 *     openKB4BMS is an open source knowledge base (KB) acting as a
 *     building management application.
 *
 *     Copyright (C) 2016
 *     Institute of Computer Aided Automation, Automation Systems Group, TU Wien.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package at.ac.tuwien.auto.sewoa.http;

import com.google.common.io.ByteStreams;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
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

    public byte[] putData(String url, String data) {
        byte[] result = new byte[]{};
        HttpClient httpClient = httpClientFactory.createClient();
        PutMethod putmethod = new PutMethod(url);
        putmethod.setRequestEntity(new StringRequestEntity(data));

        HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
        params.setConnectionTimeout((int) TimeUnit.SECONDS.toMillis(CONNECTION_TO));
        params.setSoTimeout((int) TimeUnit.SECONDS.toMillis(SOCKET_TO));
        putmethod.addRequestHeader("Content-Type", "application/xml");
        putmethod.addRequestHeader("Accept", "application/xml");

        try {
            result = send(httpClient, putmethod);
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
