package com.xidian.newflow;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientManager {

    private static HttpClient httpClient = null;

    private HttpClientManager(){

    }

    public static void init(){
        httpClient = null;
        if(httpClient == null){
            synchronized (HttpClientManager.class){
                if (httpClient == null){
                    httpClient = HttpClients.createDefault();
                }
            }
        }
    }

    public static HttpClient getInstance(){

        return httpClient;
    }

}
