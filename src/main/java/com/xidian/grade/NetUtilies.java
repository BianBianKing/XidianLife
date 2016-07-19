package com.xidian.grade;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WJ on 2015/7/25.
 */
public class NetUtilies {

    public static boolean getLoginWebCookie(UserInfo userInfo){

        HttpGet httpGet = new HttpGet(NetConstans.LOGIN_WEB_URL);

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

           // System.out.println(userInfo.toString());

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }


    public static boolean doLoginAction(UserInfo userInfo){

        HttpPost httpPost = new HttpPost(NetConstans.LOGIN_URL);
        httpPost.setHeader("Host", "ids.xidian.edu.cn");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Referer", "http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fjwxt.xidian.edu.cn%2Fcaslogin.jsp");
        httpPost.setHeader("Connection", "keep-alive");

        // set param
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("_eventId", userInfo.get_eventId()));
        formparams.add(new BasicNameValuePair("execution", userInfo.getExecution()));
        formparams.add(new BasicNameValuePair("lt", userInfo.getLt()));
        formparams.add(new BasicNameValuePair("password", userInfo.password));
        formparams.add(new BasicNameValuePair("rmShown", userInfo.getRmShown()));
        formparams.add(new BasicNameValuePair("submit", ""));
        formparams.add(new BasicNameValuePair("username", userInfo.userName));
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
            Header[] headers = response.getHeaders("Location");
            userInfo.setJwxt_url(headers[0].toString().split(" ")[1]);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    public static boolean getGradeCookie(UserInfo userInfo) throws NoHttpResponseException{

        HttpGet httpGet = new HttpGet(userInfo.getJwxt_url());

        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpGet.setHeader("Referer", NetConstans.LOGIN_URL);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "jwxt.xidian.edu.cn");

        try {
            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
        //    System.out.println(EntityUtils.toString(response.getEntity()));
            return true;
        }catch(NoHttpResponseException nhe){
        	throw nhe;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean getUserInfo(UserInfo userInfo){

        HttpGet httpGet = new HttpGet(NetConstans.USERINFO_URL);
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "jwxt.xidian.edu.cn");
        httpGet.setHeader("Referer", "http://jwxt.xidian.edu.cn/caslogin.jsp");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

        try {
            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
            Document document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
            Element element = document.getElementsByAttributeStarting("nowrap").get(0);
            userInfo.setUserDesc(element.text().split(" ")[1].split("\\)")[0]+")");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }


    public static boolean getSemesterInfo(UserInfo userInfo){
        HttpGet httpGet = new HttpGet(NetConstans.SEMESTER_URL);

        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "jwxt.xidian.edu.cn");
        httpGet.setHeader("Referer", "http://jwxt.xidian.edu.cn/menu/menu.jsp");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

        try {
            HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
            //System.out.println(EntityUtils.toString(response.getEntity()));
            Document document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
            Elements elements = document.getElementsByTag("a");
            parseSemester(userInfo, elements);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }


    private static void parseSemester(UserInfo userInfo, Elements elements){
        List<SemesterInfo> semesterInfos = new ArrayList<SemesterInfo>();
        for(int i = 3; i < elements.size(); i++){
            SemesterInfo semesterInfo = new SemesterInfo();
                semesterInfo.setUrl(elements.get(i).attr("href"));
                semesterInfo.setSemesterName(elements.get(i).text());
                semesterInfos.add(semesterInfo);
               // System.out.println(semesterInfo.toString());

        }
        userInfo.setSemesterInfos(semesterInfos);
    }


    public static boolean getGradeList(UserInfo userInfo){

        SemesterInfo semesterInfo;
        for (int i =0; i < 1; i++){
            semesterInfo = userInfo.getSemesterInfos().get(i);
            HttpGet httpGet = new HttpGet(NetConstans.GET_SCORE_ROOT + semesterInfo.getUrl());
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            httpGet.setHeader("Connection", "keep-alive");
            httpGet.setHeader("Host", "jwxt.xidian.edu.cn");
            httpGet.setHeader("Referer", "http://jwxt.xidian.edu.cn/gradeLnAllAction.do?type=ln&oper=qb");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

            try {
                HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
                //System.out.println(EntityUtils.toString(response.getEntity()));
                parseSemesterGrade(userInfo, response);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return false;
    }

    public static boolean getFailGradeInfo(UserInfo userInfo){

        for (int i =0; i < 1; i++){
            HttpGet httpGet = new HttpGet(NetConstans.FAIL_GRADE_URL);
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            httpGet.setHeader("Connection", "keep-alive");
            httpGet.setHeader("Host", "jwxt.xidian.edu.cn");
            httpGet.setHeader("Referer", "http://jwxt.xidian.edu.cn/gradeLnAllAction.do?type=ln&oper=qb");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

            try {
                HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
                //System.out.println(EntityUtils.toString(response.getEntity()));
                parseFailGradeInfo(userInfo, response);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return false;
    }

    private static void parseSemesterGrade(UserInfo userInfo, HttpResponse response){
        Document document = null;
        Elements elements = null;
        List<SemesterGrade> semesterGrades = new ArrayList<SemesterGrade>();
        try {
            document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
            elements = document.getElementsByClass("pageAlign");
            //System.out.println(elements.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for(int i = 0; i < userInfo.getSemesterInfos().size(); i++){
            SemesterGrade semesterGrade = new SemesterGrade();
            Element element = elements.get(i);
            //System.out.println(element.toString());
            semesterGrade.setSemesterName(userInfo.getSemesterInfos().get(i).getSemesterName());
            parseSemesterGradeInfo(semesterGrade, element);
            semesterGrades.add(semesterGrade);
        }

        userInfo.setSemesterGrades(semesterGrades);
    }

    private static void parseSemesterGradeInfo(SemesterGrade semesterGrade, Element element){
        List<GradeInfo> gradeInfos = new ArrayList<GradeInfo>();
        Elements courseElements = element.getElementsByClass("odd");
        for(int i = 0; i < courseElements.size(); i++){
            GradeInfo gradeInfo = new GradeInfo();
            Element courseElement = courseElements.get(i);
            //System.out.println(courseElement.toString());
            gradeInfo.setCourseCode(courseElement.getElementsByTag("td").get(0).text());
            gradeInfo.setCourseOrderCode(courseElement.getElementsByTag("td").get(1).text());
            gradeInfo.setCourseName(courseElement.getElementsByTag("td").get(2).text());
            gradeInfo.setCourseEnglistName(courseElement.getElementsByTag("td").get(3).text());
            gradeInfo.setCredit(courseElement.getElementsByTag("td").get(4).text());
            gradeInfo.setCourseSort(courseElement.getElementsByTag("td").get(5).text());
            gradeInfo.setCourseScore(courseElement.getElementsByTag("td").get(6).text());
            //System.out.println(gradeInfo.toString());
            gradeInfos.add(gradeInfo);
        }
        semesterGrade.setGradeInfos(gradeInfos);
    }


    private static void parseFailGradeInfo(UserInfo userInfo, HttpResponse response){

        FailGradeInfo failGradeInfo = new FailGradeInfo();
        List<GradeInfo> gradeInfos = new ArrayList<GradeInfo>();
        try {
            Document document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
            Element element = document.getElementsByClass("pageAlign").get(0);
            Elements elements = element.getElementsByClass("odd");
            for(int i = 0; i < elements.size(); i++){
                GradeInfo gradeInfo = new GradeInfo();
                Element courseElement = elements.get(i);
                gradeInfo.setCourseCode(courseElement.getElementsByTag("td").get(0).text());
                gradeInfo.setCourseOrderCode(courseElement.getElementsByTag("td").get(1).text());
                gradeInfo.setCourseName(courseElement.getElementsByTag("td").get(2).text());
                gradeInfo.setCourseEnglistName(courseElement.getElementsByTag("td").get(3).text());
                gradeInfo.setCredit(courseElement.getElementsByTag("td").get(4).text());
                gradeInfo.setCourseSort(courseElement.getElementsByTag("td").get(5).text());
                gradeInfo.setCourseScore(courseElement.getElementsByTag("td").get(6).text());
                gradeInfos.add(gradeInfo);
            }
        failGradeInfo.setFailGradeInfos(gradeInfos);
        userInfo.setFailGradeInfo(failGradeInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static boolean getCurrentGradeInfo(UserInfo userInfo){

        for (int i =0; i < 1; i++){
            HttpGet httpGet = new HttpGet(NetConstans.CURRENT_SEMESTER_URL);
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            httpGet.setHeader("Connection", "keep-alive");
            httpGet.setHeader("Host", "jwxt.xidian.edu.cn");
            httpGet.setHeader("Referer", NetConstans.FAIL_GRADE_URL);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

            try {
                HttpResponse response = HttpClientManager.getInstance().execute(httpGet);
                parseCurrentGradeInfos(userInfo, response);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return false;
    }

    private static void parseCurrentGradeInfos(UserInfo userInfo, HttpResponse response){
        try {
            Document document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
            List<GradeInfo> currentGradeInfos = new ArrayList<GradeInfo>();
            Elements currentElements = document.getElementsByClass("odd");
            for(int i = 0; i < currentElements.size(); i++){
                GradeInfo gradeInfo = new GradeInfo();
                Element element = currentElements.get(i);
                gradeInfo.setCourseCode(element.getElementsByTag("td").get(0).text());
                gradeInfo.setCourseOrderCode(element.getElementsByTag("td").get(1).text());
                gradeInfo.setCourseName(element.getElementsByTag("td").get(2).text());
                gradeInfo.setCourseEnglistName(element.getElementsByTag("td").get(3).text());
                gradeInfo.setCredit(element.getElementsByTag("td").get(4).text());
                gradeInfo.setCourseSort(element.getElementsByTag("td").get(5).text());
                gradeInfo.setCourseScore(element.getElementsByTag("td").get(9).text());
                currentGradeInfos.add(gradeInfo);
            }
            userInfo.setCurrentGradeInfos(currentGradeInfos);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
