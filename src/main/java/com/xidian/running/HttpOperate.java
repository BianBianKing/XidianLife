package com.xidian.running;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;






public class HttpOperate {
	
	//test a website
		public static boolean urlConnect(String str) {
			if(str.indexOf("http://")==-1)
				str= "http://"+str;
		    int state;
			try {
				URL url = new URL(str);  
				HttpURLConnection con = (HttpURLConnection) url.openConnection();  
				state = con.getResponseCode();
				 if (state == 200) {  
				     return true; 
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		   
			return false;
		}
	  //
	  public static boolean getLoginInfo(RunningUserInfo user){

	        HttpGet httpGet = new HttpGet("http://210.27.8.14/runner/");
	        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
	        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
	        httpGet.setHeader("Connection", "keep-alive");
	        try {
	            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
	            String loginWebStr = EntityUtils.toString(response.getEntity());


	           

	            Document document = Jsoup.parse(loginWebStr);
	            DocHandle.getUserInfo(document,user);
	       
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }


	    }

	 public static boolean doLoginAction(RunningUserInfo user){

	        HttpPost httpPost = new HttpPost(NetConstans.LOGIN_URL);
	        httpPost.setHeader("Host", "210.27.8.14");
	        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
	        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
	        httpPost.setHeader("Referer", "http://210.27.8.14/");
	        httpPost.setHeader("Connection", "keep-alive");
	        

	        // set param
	        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();

	        formparams.add(new BasicNameValuePair("username", user.getUserName()));
	        formparams.add(new BasicNameValuePair("password", user.getPassword()));

	        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
	        httpPost.setEntity(encodedFormEntity);


	        //System.out.println("Bfore Login");
	        HttpResponse response = null;
	        try {
	        	//connect timeout set
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(2000).build();//设置请求和传输超时时间
                httpPost.setConfig(requestConfig);
	            response = HttpClientManager.getInstance().execute(httpPost);
	            if(response == null){
	            	System.out.println("null");
	                return false;
	            }
	            
	            Header[] headers;
	            Header[] headerLocation;
	            //changed 2016-3-1
	            if(response.getHeaders("Set-Cookie").length!=0){
	            	headerLocation=response.getHeaders("Location");
	            	//System.out.println(headerLocation[0].toString());
	            	if(headerLocation[0].toString().indexOf("err=true")!=-1)
            		{
            			System.out.println(headerLocation[0].toString());
            			System.out.println("login error");
            			throw new RuntimeException(new LoginException());
            		}
            		else
            		{
		            	headers = response.getHeaders("Set-Cookie");
		            	String cookie = headers[0].toString();
            		}
	            }
	            else {
	            	System.out.println("login error,can't get cookies");
	            	return false;
				}
	            //changed 2016-3-1
	            
	           // System.out.println(response.getFirstHeader("Set-Cookie").toString());
	            //user.setCookies(cookies);
	            //System.out.println(response.toString());
	            

	            return true;
	        }catch (IOException e) {
	        	throw new RuntimeException(e);
	            //return false;
	        }

	    }
	 public static boolean getRunningList(RunningUserInfo user){
		 HttpGet httpGet = new HttpGet(NetConstans.RUN_INFO);

	        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
	        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        httpGet.setHeader("Connection", "keep-alive");
	        httpGet.setHeader("Host", "210.27.8.14");
	        httpGet.setHeader("Referer", "http://210.27.8.14/runner/");
	        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");

	        try {
	            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
	            	
	            String body  = EntityUtils.toString(response.getEntity());
	            Document document = Jsoup.parse(body);
	            DocHandle.getRunningList(document, user);
	            //System.out.println(body);
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	 }

}
