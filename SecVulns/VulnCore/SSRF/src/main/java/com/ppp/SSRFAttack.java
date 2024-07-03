package com.ppp;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * @author Whoopsunix
 */
public class SSRFAttack {
    public static void main(String[] args) throws Exception {
        String url = "http://baidu.com";
//        URLConnection(url);
        httpClient(url);
//        okhttp(url);
//        test(url);
    }

    public static Object URLConnection(String u) throws Exception {
        URL url = new URL(u);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3000); // 设置连接超时时间为3秒
        connection.setReadTimeout(5000); // 设置读取超时时间为5秒
        connection.connect();
        return connection.getResponseCode();
    }

    // org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect
    public static Object httpClient(String u) throws Exception {
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(u);
        CloseableHttpResponse response = aDefault.execute(httpGet);
        HttpEntity entity = response.getEntity();

        return response.getStatusLine();
    }

    public static Object okhttp(String u) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(u)
                .build();
        Response response = client.newCall(request).execute();
        return response.code();
    }


    public static void test(String u) throws Exception {
        new URI(u);
    }



}
