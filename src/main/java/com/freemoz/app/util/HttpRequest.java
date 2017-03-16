package com.freemoz.app.util;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class HttpRequest {

    private final String url;

    public HttpRequest(String url) {
        this.url = url;
    }

    public String executePost(List<NameValuePair> urlParameters) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(this.url);

        post.setHeader("User-Agent", USER_AGENT);
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }
}
