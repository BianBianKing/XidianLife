package com.xidian.mastergrade;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;

/**
 * Created by WJ on 2015/7/27.
 */
public class HttpClientManager {

    private static volatile HttpClient httpClient = null;
    private static volatile RequestConfig requestConfig = null;

    private HttpClientManager(){

    }

    public static void init(){
        httpClient = null;
        httpClient = HttpClients.createDefault();
        requestConfig = RequestConfig.custom()   /*  杩欓儴鍒嗚缃秴鏃舵椂闂寸殑璁剧疆 鎴戠幇鍦ㄤ笉鐭ラ亾鑳戒笉鑳戒娇鐢ㄥ崟渚嬫ā寮忋�傘�傘�� 濡傛灉涓嶈兘瑕佷慨鏀瑰摝 */
                .setConnectTimeout(12000)
                .setConnectionRequestTimeout(12000)
                .setSocketTimeout(12000)
                .build();
//        HttpHost httpHost = new HttpHost("192.168.0.220", 8888);
//        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(httpHost);
//        httpClient = HttpClients.custom()
//                .setRoutePlanner(routePlanner)
//                .build();
    }

    public static HttpClient getInstance(){

        if (httpClient == null){
            try {
                throw new Exception("Before getInstance you should call init()");
            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                return null;
            }
        }else
            return httpClient;
    }

    public static RequestConfig getRequestConfig(){
        if(requestConfig == null){
            try {
                throw new Exception("Before getRequestConfig you should call init()");
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }else {
            return requestConfig;
        }

    }

}
