package com.xidian.newflow;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;






/**
 * Created by yyt on 2015/7/27.
 */
public class HttpOperate {
	
	  //
	  public static boolean getLoginInfo(UserInfo user){

	        HttpGet httpGet = new HttpGet(NetConstans.LOGIN_URL);
	        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
	        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
	        httpGet.setHeader("Connection", "keep-alive");
	        httpGet.setHeader("Host","zyzfw.xidian.edu.cn");
	        httpGet.setHeader("Cache-Control","max-age=0");
	        httpGet.setHeader("Referer","http://pay.xidian.edu.cn/");
	        try {
	            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
	           

	            Header[] headers = response.getHeaders("Set-Cookie");
	            StringBuilder sb=new StringBuilder();
	            for(int i=0;i<headers.length;i++){
	            	sb.append(headers[i].toString());
	            }
	            user.setCookiesString(sb.toString());
	            //System.out.println(user.getCookiesString());
	            //System.out.println(user.getUserCookies().toString());
	            
	            String loginWebStr = EntityUtils.toString(response.getEntity());
	            Document document = Jsoup.parse(loginWebStr);
	            DocHandle.getCsrfToken(document, user);
	            DocHandle.getVerifyCode(document, user);
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }


	    }
	  public static boolean getFlowInfo(UserInfo user){

	        HttpGet httpGet = new HttpGet(NetConstans.FLOW_URL);
	        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
	        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
	        httpGet.setHeader("Connection", "keep-alive");
	        httpGet.setHeader("Host","zyzfw.xidian.edu.cn");
	        httpGet.setHeader("Cache-Control","max-age=0");
	        httpGet.setHeader("Referer","ttp://zyzfw.xidian.edu.cn/");
	        try {
	            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
	       

	            String loginWebStr = EntityUtils.toString(response.getEntity());
	            Document document = Jsoup.parse(loginWebStr);
	            DocHandle.getFlowInfo(document, user);
	            //System.out.println(loginWebStr);
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }


	    }
	  public static ORCKingCookies getORCKing(){

	        HttpGet httpGet = new HttpGet(NetConstans.OCRKING);
	        httpGet.setHeader("Host", "lab.ocrking.com");
	        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
	        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
	        httpGet.setHeader("Referer", "http://210.27.8.14/");
	        httpGet.setHeader("Connection", "keep-alive");
	        try {
	            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
	            Header[] headers = response.getHeaders("Set-Cookie");
	            StringBuilder sb=new StringBuilder();
	            for(int i=0;i<headers.length;i++){
	            	sb.append(headers[i].toString());
	            }
	            //System.out.println(sb.toString());
	            try {
	    			String[] cc=sb.toString().split(";");
		    		String s1=(cc[0].split("="))[1];
		    		String s2=(cc[2].split("="))[1];
		    		String s3=(cc[3].split("="))[1];
		    		String s4=(cc[5].split("="))[1];
		    		System.out.println(s1+"----"+s2+"----"+s3+"----"+s4);
		    		return new ORCKingCookies(s1, s2, s3, s4);
	    		}catch(ArrayIndexOutOfBoundsException e){
	    			e.printStackTrace();
	    		}
	           

	         
	            return null;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }


	    }
	  public static boolean loginFlowQuery(UserInfo user){


	        HttpPost httpPost = new HttpPost(NetConstans.LOGIN_URL);
	        httpPost.setHeader("Host", "zyzfw.xidian.edu.cn");
	        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
	        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
	        httpPost.setHeader("Referer", "http://zyzfw.xidian.edu.cn/");
	        httpPost.setHeader("Origin", "http://zyzfw.xidian.edu.cn");
	        httpPost.setHeader("Connection", "keep-alive");
	        

	        // set param
	        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
	        
	        formparams.add(new BasicNameValuePair("_csrf", user.getCsrf_token()));
	        formparams.add(new BasicNameValuePair("LoginForm[username]", user.getUserName()));
	        formparams.add(new BasicNameValuePair("LoginForm[password]", user.getPassword()));
	        formparams.add(new BasicNameValuePair("LoginForm[verifyCode]", user.getCode()));
	        formparams.add(new BasicNameValuePair("login-button", ""));
	        
	        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
	        httpPost.setEntity(encodedFormEntity);


	        //System.out.println("Bfore Login");
	        HttpResponse response = null;
	        try {
	            response = HttpClientManager.getInstance().execute(httpPost);
	            if(response == null){
	            	System.out.println("null");
	                return false;
	            }
	            String loginWebStr = EntityUtils.toString(response.getEntity());
	            //System.out.println(loginWebStr);
	            
	            if(loginWebStr.equals("")||loginWebStr==null)
	            	return  true;
	            else {
	            	Document document = Jsoup.parse(loginWebStr);
		            DocHandle.getErrorInfo(document, user);
	            	return false;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }

	    }
	 public static String recognizeCodeByORCKingWebsite(String url){
		 	//ORCKingCookies t = getORCKing();
		 	//if(t==null){
		 		
		 		//return null;
		 	//}

	        HttpPost httpPost = new HttpPost(NetConstans.OCRKINGDO);
	        httpPost.setHeader("Host", "lab.ocrking.com");
	        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
	        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
	        httpPost.setHeader("Referer", "http://210.27.8.14/");
	        httpPost.setHeader("Connection", "keep-alive");
	        

	        // set param
	        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();

	        formparams.add(new BasicNameValuePair("charset", "1"));
	        formparams.add(new BasicNameValuePair("email", ""));
	        formparams.add(new BasicNameValuePair("language", "eng"));
	        formparams.add(new BasicNameValuePair("outputFormat", ""));
	        formparams.add(new BasicNameValuePair("service", "OcrKingForNumber"));
	        formparams.add(new BasicNameValuePair("url",url));
	        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
	        httpPost.setEntity(encodedFormEntity);


	        //System.out.println("Bfore Login");
	        HttpResponse response = null;
	        try {
	            response = HttpClientManager.getInstance().execute(httpPost);
	            if(response == null){
	            	System.out.println("null");
	                return null;
	            }
	            
	            
	            String loginWebStr = EntityUtils.toString(response.getEntity());
	            Document document = Jsoup.parse(loginWebStr);
	            return  document.select("result").text();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }

	    }
	 public static boolean getRunningList(UserInfo user){
		 HttpGet httpGet = new HttpGet(NetConstans.LOGIN_URL);

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
	            //DocHandle.getRunningList(document, user);
	            //System.out.println(body);
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	 }

}
