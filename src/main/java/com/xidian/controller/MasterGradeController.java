package com.xidian.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NoHttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.forms.GradeTemp;
import com.xidian.grade.UserInfo;
import com.xidian.mastergrade.MasterMain;
import com.xidian.mastergrade.MasterUserInfo;
import com.xidian.service.api.GradeStatisticsService;
import com.xidian.service.api.GradeTempService;
import com.xidian.util.QuerySecret;

@Controller
public class MasterGradeController {
	@Resource(name="gradeTempServiceImpl")
	GradeTempService gradeTempService;
	
	@Resource(name="gradeStatisticsServiceImpl")
	GradeStatisticsService gradeStatisticsService;
	
	@RequestMapping(value="queryMasterGrade",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryGradeAction(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "refresh", required = false) boolean refresh) throws IOException{
		ModelAndView tempView = new ModelAndView("grade/queryMasterGradePage");
		try{
		if(!refresh){
		
			System.out.println("首次");
			//首次登陆
		   
		    
		    try {
		    
			    String username = request.getParameter("username");
			    String password = request.getParameter("password");
			    Cookie userCookie = new Cookie("usernameGrade", URLEncoder.encode(QuerySecret.addSecret(username), "utf-8"));
			    Cookie passCookie = new Cookie("passwordGrade", URLEncoder.encode(QuerySecret.addSecret(password), "utf-8"));
			    MasterUserInfo masterUserInfo = null;
			    try {
			    	masterUserInfo = MasterMain.test(username,password);
			    	if (masterUserInfo.getErrorCode() == 3) {
			    		throw new Exception("亲们～由于教务系统自身问题，小蘑菇暂时无法与成绩查询系统取得联系，请耐心等待！");
			    	} else if(masterUserInfo.getErrorCode() == 1) {
			    		throw new Exception("网络错误");
			    	} else if(masterUserInfo.getErrorCode() == 2) {
			    		throw new Exception("用户名或密码错误");
			    	} 
			    }catch(RuntimeException e){
			    	e.printStackTrace();
			    	gradeStatisticsService.addMasterNum();
			    	tempView.addObject("status", "fail");
			    	tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
			    	tempView.setViewName("grade/queryGrade");
			    	return tempView;
			    }
			    catch (Exception e) {
			    	e.printStackTrace();
			    	gradeStatisticsService.addMasterNum();
			    	tempView.addObject("status", "fail");
			    	tempView.addObject("info", e.getMessage());
			    	tempView.setViewName("grade/queryGrade");
			    	return tempView;
			    }
			    if (masterUserInfo == null) {
			    	tempView.addObject("status", "fail");
			    	tempView.addObject("info", "用户名或密码错误！");
			    	tempView.setViewName("grade/queryGrade");
			    	Cookie cookie = new Cookie("usernameGrade", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					cookie = new Cookie("passwordGrade", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					cookie = new Cookie("gradeTest", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					cookie = new Cookie("level", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
			    	gradeStatisticsService.addMasterNum();
			    } else {
			    	gradeStatisticsService.addMasterOkNum();
			    	//标志时间
			    	Date now = new Date(); 
			    	Cookie tempCookie= new Cookie("gradeTest",String.valueOf(now.getTime()));
			    	tempCookie.setMaxAge(3600);
			    	response.addCookie(tempCookie);
			    	tempView.addObject("hours", 0);
			    	tempView.addObject("minutes", 1);
			    	tempCookie = new Cookie("level", "master");
			    	tempCookie.setMaxAge(Integer.MAX_VALUE);
			    	response.addCookie(tempCookie);
			    	
				    //用户名密码写入cookie
			    	userCookie.setMaxAge(Integer.MAX_VALUE);
				    passCookie.setMaxAge(Integer.MAX_VALUE);
				    response.addCookie(userCookie);
				    response.addCookie(passCookie);
				    tempView.addObject("userinfo", masterUserInfo.getUserDesc());
			    	tempView.addObject("grade", masterUserInfo.getGradeInfo());
				    //序列化
			    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
			        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
			        objectOutputStream.writeObject(masterUserInfo);    
			        String serStr = byteArrayOutputStream.toString("ISO-8859-1");  
			        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");  
			        objectOutputStream.close();  
			        byteArrayOutputStream.close();  
			        //写入数据库
			        GradeTemp gradeTemp = new GradeTemp();
			        gradeTemp.setContent(serStr);
			        gradeTemp.setUsername(username);
			        gradeTempService.addGradeTemp(gradeTemp);
			    }
			    return tempView;
		    
		    }
		    catch (Exception e) {
		    	e.printStackTrace();
				tempView.setViewName("grade/queryGrade");
		    	tempView.addObject("status", "fail");
		    	tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
		    	return tempView;
			}
		}
		else{
			System.out.println("第二次");
			tempView = new ModelAndView("grade/queryMasterGradePage");
		    
			Cookie[] cookies = request.getCookies();
			String username = null;
			String password = null;
			String gradeTest = null;
			String level = null;
			//count用来判断用户名和密码是否存在，以及上次查询时间是否已过期
			int count = 0;
			//减少循环时间，其实这么写不是很好，代码很冗余
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("usernameGrade")) {
						username = QuerySecret.deleteSecret(URLDecoder.decode(cookie.getValue(),"utf-8"));
						System.out.println(cookie.getValue());
						count++;
					}
					if (cookie.getName().equals("passwordGrade")) {
						password = QuerySecret.deleteSecret(URLDecoder.decode(cookie.getValue(),"utf-8"));
						System.out.println(cookie.getValue());
						count++;
					}
					if (cookie.getName().equals("gradeTest")) {
						gradeTest = cookie.getValue();
						count++;
					}
					if (cookie.getName().equals("level")) {
						level = cookie.getValue();
						count++;
					}
					if (count == 4) {
						break;
					}
				}
			}
		    try {
		    	MasterUserInfo masterUserInfo = null;
		    	UserInfo userInfo = null;
		    	masterUserInfo = MasterMain.test(username, password);
	    		if (masterUserInfo == null ) {
	    			tempView.setViewName("grade/queryGrade");
			    	tempView.addObject("status", "fail");
			    	tempView.addObject("info", "查询失败！");
			    	Cookie cookie = new Cookie("usernameGrade", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					cookie = new Cookie("passwordGrade", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					cookie = new Cookie("gradeTest", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					cookie = new Cookie("level", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
			    	return tempView;
			    } else {
			    	tempView.setViewName("grade/queryMasterGradePage");
			    	tempView.addObject("grade", masterUserInfo.getGradeInfo());
			    	tempView.addObject("userinfo", masterUserInfo.getUserDesc());
			    	//序列化
			    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
			        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
			        objectOutputStream.writeObject(masterUserInfo);    
			        String serStr = byteArrayOutputStream.toString("ISO-8859-1");  
			        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");  
			        objectOutputStream.close();  
			        byteArrayOutputStream.close();  
			        //写入数据库
			        GradeTemp gradeTemp = new GradeTemp();
			        gradeTemp.setContent(serStr);
			        gradeTemp.setUsername(username);
			        gradeTempService.addGradeTemp(gradeTemp);
			        //设置1小时有效，用于显示刷新时间
			        Date now = new Date(); 
			    	Cookie tempCookie= new Cookie("gradeTest",String.valueOf(now.getTime()));
			    	tempCookie.setMaxAge(3600);
			    	response.addCookie(tempCookie);
			    	tempView.addObject("hours", 0);
			    	tempView.addObject("minutes", 1);
			    	
			    	return tempView;
			    }
		    } catch (Exception e) {
				tempView.setViewName("grade/queryGrade");
		    	tempView.addObject("status", "fail");
		    	tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
		    	return tempView;
			}
		}
	}
		catch (Exception e) {
			tempView.setViewName("grade/queryGrade");
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
	    	return tempView;
		}}
}
