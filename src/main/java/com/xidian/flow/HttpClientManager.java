package com.xidian.flow;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

/**
 * Created by WJ on 2015/7/23.
 */
@Service("httpClientManager")
public class HttpClientManager {
    private static HttpClient client = null;
    public static HttpClient getInstance(){
    	  client = HttpClients.createDefault();
//        synchronized (HttpClientManager.class){
//            HttpHost proxy = new HttpHost("192.168.0.220", 8888);
//            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//            client = HttpClients.custom()
//                    .setRoutePlanner(routePlanner)
//                    .build();
//
//            return client;
//        }
        return  client;
    }
}
