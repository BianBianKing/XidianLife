package com.xidian.flow;


import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WJ on 2015/7/22.
 */

@Service("userManager")
public class UserManager {

    private String username;
    private String password;
    private String Cookie;
    private String checkcode;
    private String PHPSESSID;
    private String BIGipServerzyzfw;
    private String used;
    private String remainder;
    private String userDesc;

    public String getPHPSESSID() {
        return PHPSESSID;
    }

    public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getBIGipServerzyzfw() {
        return BIGipServerzyzfw;
    }

    public void setPHPSESSID(String PHPSESSID) {
        this.PHPSESSID = PHPSESSID;
    }

    public void setBIGipServerzyzfw(String BIGipServerzyzfw) {
        this.BIGipServerzyzfw = BIGipServerzyzfw;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean parseCheckCode(){

        // 1 解析登录界面获取登录验证码url
        HttpClient httpClient = HttpClientManager.getInstance();
        HttpGet httpGet = new HttpGet(Constans.loginWebURL);
        httpGet.setHeader("Host", "zyzfw.xidian.edu.cn");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Referer", "http://pay.xidian.edu.cn/");
        httpGet.setHeader("Cookie", PHPSESSID + "; " + BIGipServerzyzfw);
        httpGet.setHeader("Cache-Control", "max-age=0");
        
        try {
            HttpResponse response = httpClient.execute(httpGet);
            saveCookie(response);
            String htmlStr = EntityUtils.toString(response.getEntity());
            Document document = Jsoup.parse(htmlStr);
            String iconUrl = Constans.ZYZFW_ROOT + document.getElementsByClass("login").get(0).getElementsByTag("img").get(3).attr("src");
            System.out.println("hear");
            File imageFile = FileUtilities.saveNetImage(iconUrl, this);
            if(imageFile == null){
                System.out.println("save File Error");
                return false;
            }
            checkcode = OcrkingUtility.parseFileLocal(imageFile);
            if(checkcode == null){
                return false;
            }else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }



    public boolean doLogin(){
        HttpPost httpPost = new HttpPost(Constans.loginURL);
        httpPost.setHeader("Host", "zyzfw.xidian.edu.cn");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.setHeader("Referer", "http://zyzfw.xidian.edu.cn/");
        httpPost.setHeader("Cookie", PHPSESSID + "; " + BIGipServerzyzfw);
        httpPost.setHeader("Pragma", "no-cache");
        httpPost.setHeader("Cache-Control", "no-cache");


        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("ts", "login"));
        formparams.add(new BasicNameValuePair("chekcode", checkcode));
        formparams.add(new BasicNameValuePair("username", username));
        formparams.add(new BasicNameValuePair("password", password));
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(encodedFormEntity);
        try {
            //httpPost.setHeader("Cookie", Cookie);
            HttpResponse response = HttpClientManager.getInstance().execute(httpPost);
            saveCookie(response);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("login result -->" + result);
            if(result.equals("101")){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public Result parseFlowInfo(){
        HttpGet httpGet = new HttpGet(Constans.flowURL);
        httpGet.setHeader("Host", "zyzfw.xidian.edu.cn");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpGet.setHeader("Accept", "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Referer", "http://zyzfw.xidian.edu.cn/");
        httpGet.setHeader("Cookie", PHPSESSID + "; " + BIGipServerzyzfw);

        try {
            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
            saveCookie(response);
            Document document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
            String flowInfo = document.getElementById("user3_list").getElementsByTag("tbody").get(0)
                    .getElementsByTag("tr").get(3).getElementsByTag("td").get(1).text();
            String[] strings = flowInfo.split(";");
            used = strings[2].split(":")[1];
            remainder = strings[4].split(" ")[3];
            userDesc = document.getElementById("user").getElementsByTag("li").get(0).text();
           // System.out.println("used : " + used + "\n" + "" +
            //        "remainder: " + remainder);
            Result result = new Result();
            result.setUsedString(used);
            result.setRemainderString(remainder);
            result.setUserinfo(userDesc);
            //System.out.println("??");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean updateCookie(){

        // 1 解析登录界面获取登录验证码url
        HttpClient httpClient = HttpClientManager.getInstance();
        HttpGet httpGet = new HttpGet(Constans.loginWebURL);
        httpGet.setHeader("Host", "zyzfw.xidian.edu.cn");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Referer", "http://pay.xidian.edu.cn/");
        httpGet.setHeader("Cache-Control", "max-age=0");


        try {
            HttpResponse response = httpClient.execute(httpGet);
            saveCookie(response);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    private void saveCookie(HttpResponse response){
        Header[] headers = response.getHeaders("Set-Cookie");
        setHeader(headers);
    }

    private void setHeader(Header[] header){
        if(header.length != 2){
            return;
        }

        PHPSESSID = header[0].getValue().split(";")[0];
        BIGipServerzyzfw = header[1].getValue().split(";")[0];

    }

}
