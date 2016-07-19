package com.xidian.mastergrade;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WJ on 2015/7/27.
 */
public class NetUtility {

    public static boolean getLoginWebCookie(MasterUserInfo userInfo){

        HttpGet httpGet = new HttpGet(NetConstans.LOGIN_WEB_URL);

        httpGet.setConfig(HttpClientManager.getRequestConfig());
        httpGet.setHeader("Host", "ids.xidian.edu.cn");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Connection", "keep-alive");

        try {
            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
            String loginWebStr = EntityUtils.toString(response.getEntity());
            Document document = Jsoup.parse(loginWebStr);

            Elements elements = document.getElementsByClass("banner-bg").get(0).getElementsByTag("input");

            userInfo.setLt(elements.get(3).attr("value"));
            userInfo.setExecution(elements.get(4).attr("value"));
            userInfo.set_eventId(elements.get(5).attr("value"));
            userInfo.setRmShown(elements.get(6).attr("value"));

            Header[] headers = response.getHeaders("Set-Cookie");
            //userInfo.setErrorCode(NetConstans.WEB_ERROR);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }


    public static boolean doLoginAction(MasterUserInfo userInfo){

        HttpPost httpPost = new HttpPost(NetConstans.LOGIN_URL);
        httpPost.setConfig(HttpClientManager.getRequestConfig());
        httpPost.setHeader("Host", "ids.xidian.edu.cn");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Referer", NetConstans.LOGIN_WEB_URL);
        httpPost.setHeader("Connection", "keep-alive");


        // set param
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("_eventId", userInfo.get_eventId()));
        formparams.add(new BasicNameValuePair("execution", userInfo.getExecution()));
        formparams.add(new BasicNameValuePair("lt", userInfo.getLt()));
        formparams.add(new BasicNameValuePair("password", userInfo.getPassword()));
        formparams.add(new BasicNameValuePair("rmShown", userInfo.getRmShown()));
        formparams.add(new BasicNameValuePair("submit", ""));
        formparams.add(new BasicNameValuePair("username", userInfo.getUserName()));
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(encodedFormEntity);


        //System.out.println("Bfore Login");
        HttpResponse response = null;
        try {
            response = HttpClientManager.getInstance().execute(httpPost);
            if(response == null){
                return false;
            }
        //    System.out.println("After Login");
         //   System.out.println(EntityUtils.toString(response.getEntity()));
            Header[] headers = response.getHeaders("Location");
            userInfo.setJysxt_url(headers[0].toString().split(" ")[1]);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            userInfo.setErrorCode(NetConstans.PASSWD_ERROR);
            return false;
        }

    }

    public static boolean getGradeWebCookie(MasterUserInfo userInfo){
        HttpGet httpGet = new HttpGet(userInfo.getJysxt_url());
        httpGet.setConfig(HttpClientManager.getRequestConfig());
        httpGet.setHeader("Host", "yjsxt.xidian.edu.cn");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Referer", NetConstans.LOGIN_URL);

        try {
            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
        //    System.out.println(EntityUtils.toString(response.getEntity()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean getGradeInfo(MasterUserInfo userInfo){

        HttpGet httpGet = new HttpGet(NetConstans.QUERY_GRADE_URL);
        httpGet.setConfig(HttpClientManager.getRequestConfig());
        httpGet.setHeader("Host", "ids.xidian.edu.cn");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Referer", "http://yjsxt.xidian.edu.cn/student/index.jsp");

        try {
            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);

            if(!parseUserInfo(userInfo, response)){
                return false;
            }else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    private static boolean parseUserInfo(MasterUserInfo userInfo, HttpResponse httpResponse){
        String webStr = null;
        try {
            webStr = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(webStr);
        userInfo.setUserDesc(document.getElementsByClass("SubHeadLink").text());
        if (parseGradeInfo(userInfo, webStr)){
            return true;
        }else {
            return false;
        }
    }


    private static boolean parseGradeInfo(MasterUserInfo userInfo, String webStr){

        GradeInfo gradeInfo = new GradeInfo();
        List<CourseInfo> courseInfos = new ArrayList<CourseInfo>();

        Document document = Jsoup.parse(webStr);
        System.out.println(webStr);
        try {
            gradeInfo.setLowestTotalCredit(document.getElementsByClass("tab").get(0).getElementsByTag("td").get(0).text());
            gradeInfo.setLowestDegreeCredit(document.getElementsByClass("tab").get(0).getElementsByTag("td").get(1).text());
            gradeInfo.setHadCredit(document.getElementsByClass("tab").get(0).getElementsByTag("td").get(2).text());
            gradeInfo.setHadDegreeCredit(document.getElementsByClass("tab").get(0).getElementsByTag("td").get(3).text());
            gradeInfo.setAverageScore(document.getElementsByClass("tab").get(0).getElementsByTag("td").get(4).text());

            Elements elements = document.getElementsByTag("table").get(2).getElementsByTag("tr");


            for(int i = 1; i < elements.size(); i++){
                Elements elements1 = elements.get(i).getElementsByTag("td");
                CourseInfo courseInfo = new CourseInfo();
                courseInfo.setCourseTime(elements1.get(0).text());
                courseInfo.setCourseCode(elements1.get(1).text());
                courseInfo.setCourseName(elements1.get(2).text());
                courseInfo.setCourseCredit(elements1.get(3).text());
                courseInfo.setCourseSort(elements1.get(4).text());
                courseInfo.setCourseScore(elements1.get(5).text());
                courseInfo.setCourseGotCredit(elements1.get(6).text());
                courseInfo.setPs(elements1.get(7).text());
                courseInfos.add(courseInfo);
                //System.out.println(courseInfo.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        gradeInfo.setCourseInfos(courseInfos);
        userInfo.setGradeInfo(gradeInfo);

        return true;

    }


}
