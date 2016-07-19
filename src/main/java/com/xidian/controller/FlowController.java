package com.xidian.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.flow.Main;
import com.xidian.flow.Result;
import com.xidian.flow.UserManager;
import com.xidian.service.api.FlowStatisticsService;
import com.xidian.util.QuerySecret;

//@Controller
public class FlowController {
	@Resource(name="userManager")
	UserManager userManager;
	@Resource(name="flowStatisticsServiceImpl")
	FlowStatisticsService flowStatisticsService;
	
	@RequestMapping(value="queryFlow.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryFlow(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView tempView = new ModelAndView("flow/queryFlow");
		System.out.println("hello world");
		try 
		{
		Cookie[] cookies = request.getCookies();
		String username = null;
		String password = null;
		String searchTag = null;
		String used = null;
		String remain = null;
		String userinfo = null;
		//count用来判断用户名和密码是否存在，以及上次查询时间是否已过期
		int count = 0;
		//减少循环时间，其实这么写不是很好，代码很冗余
		int timeTest = 0;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = QuerySecret.deleteSecret(URLDecoder.decode(cookie.getValue(),"utf-8"));
					System.out.println(cookie.getValue());
					count++;
				}
				if (cookie.getName().equals("password")) {
					password = QuerySecret.deleteSecret(URLDecoder.decode(cookie.getValue(),"utf-8"));
					System.out.println(cookie.getValue());
					count++;
				}
				if (cookie.getName().equals("searchTag")) {
					searchTag = cookie.getValue();
					count++;
				}
				if (cookie.getName().equals("Used")) {
					used = cookie.getValue();
					timeTest++;
				}
				if (cookie.getName().equals("Remain")) {
					remain = cookie.getValue();
					timeTest++;
				}
				if (cookie.getName().equals("UserInfo")) {
					userinfo = URLDecoder.decode( cookie.getValue(),"utf-8");
					timeTest++;
				}
				//如果已经
				if (count == 3 && timeTest == 3) {
					break;
				}
			}
		}
		//查询时间已经超过两小时，且用户名密码存在
	    if (count == 2 && username!=null && password!=null) {
	    	Result result = Main.test(username,password);
		    if (result == null) {
		    	tempView.addObject("status", "fail");
		    	tempView.addObject("info", "用户名或密码错误！");
		    	//退出，清cookie
		    	tempView.setViewName("flow/queryFlow");
		    	flowStatisticsService.addNum();
		    } else {
		    	tempView.addObject("used", result.getUsedString());
		    	if (result.getRemainderDouble() >= 1024) {
		    		DecimalFormat df = new DecimalFormat("#.00");   
		    		result.setRemainderString(df.format(result.getRemainderDouble()/1024) + "G");
		    		tempView.addObject("remainder", df.format(result.getRemainderDouble()/1024) + "G");
		    	} else {
		    		tempView.addObject("remainder", result.getRemainderString());
		    	}
		    	tempView.addObject("persent", (int)(result.getUsedDouble()/(result.getRemainderDouble()+result.getUsedDouble())*100));
		    	
		    	tempView.addObject("userinfo", result.getUserinfo());
		    	//将查询结果写入cookie，三小时有效，以此来减轻学校服务器的压力
		    	Cookie tempCookie = new Cookie("Used", result.getUsedString());
		    	tempCookie.setMaxAge(3600*3);
		    	response.addCookie(tempCookie);
		    	tempCookie = new Cookie("Remain", result.getRemainderString());
		    	tempCookie.setMaxAge(3600*3);
		    	response.addCookie(tempCookie);
		    	tempCookie = new Cookie("UserInfo", URLEncoder.encode(result.getUserinfo(), "utf-8"));
		    	tempCookie.setMaxAge(3600*3);
		    	response.addCookie(tempCookie);
		    	//存上次查询的时间，用来计算几小时前更新的那个字段，两小时有效
		    	Date now = new Date(); 
		    	tempCookie = new Cookie("searchTag",String.valueOf(now.getTime()));
		    	tempCookie.setMaxAge(3600*2);
		    	response.addCookie(tempCookie);
		    	tempView.addObject("hours", 0);
		    	tempView.addObject("minutes", 1);
		    	flowStatisticsService.addOkNum();
		    	tempView.setViewName("flow/queryFlowPage");
		    }
		    
			return tempView;
	    }
	    //不足两小时，走cookie
	    if (count == 3 && searchTag != null && used != null && remain != null) {
	    	Result result = new Result();
	    	result.setUsedString(used);
	    	result.setRemainderString(remain);
	    	tempView.addObject("used", used);
	    	tempView.addObject("remainder", remain);
	    	tempView.addObject("persent", (int)(result.getUsedDouble()/(result.getRemainderDouble()+result.getUsedDouble())*100));
	    	tempView.addObject("userinfo", userinfo);
	    	//计算时间差
	    	Date nowTime = new Date();
	    	long diff = nowTime.getTime() - Long.parseLong(searchTag);
	    	long hours = (diff)/(1000* 60 * 60);
	    	long minutes = (diff-hours*(1000* 60 * 60))/(1000* 60);
	    	tempView.addObject("hours", hours);
	    	tempView.addObject("minutes", minutes+1);
	    	//返回查询页面
	    	tempView.setViewName("flow/queryFlowPage");
	    	flowStatisticsService.addOkNum();
	    }
		return tempView;
		
		} 
		catch (Exception e) {
			flowStatisticsService.addNum();
			tempView.setViewName("flow/queryFlow");
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
	    	return tempView;
		}
	}
	
	@RequestMapping(value="queryFlow",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryFlowAction(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//首次登陆
	    ModelAndView tempView = new ModelAndView("flow/queryFlowPage");
	    
	    try {
	    
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    Cookie userCookie = new Cookie("username", URLEncoder.encode(QuerySecret.addSecret(username), "utf-8"));
	    Cookie passCookie = new Cookie("password", URLEncoder.encode(QuerySecret.addSecret(password), "utf-8"));
	    
	    Result result = Main.test(username,password);
	    if (result == null) {
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "用户名或密码错误！");
	    	tempView.setViewName("flow/queryFlow");
	    	flowStatisticsService.addNum();
	    } else {
	    	userCookie.setMaxAge(Integer.MAX_VALUE);
		    passCookie.setMaxAge(Integer.MAX_VALUE);
		    //用户名密码写入cookie
		    response.addCookie(userCookie);
		    response.addCookie(passCookie);
		    tempView.addObject("userinfo", result.getUserinfo());
	    	tempView.addObject("used", result.getUsedString());
	    	if (result.getRemainderDouble() >= 1024) {
	    		DecimalFormat df = new DecimalFormat("#.00");   
	    		result.setRemainderString(df.format(result.getRemainderDouble()/1024) + "G");
	    		tempView.addObject("remainder", df.format(result.getRemainderDouble()/1024) + "G");
	    	} else {
	    		tempView.addObject("remainder", result.getRemainderString());
	    	}
	    	tempView.addObject("persent", (int)(result.getUsedDouble()/(result.getRemainderDouble()+result.getUsedDouble())*100));
	    	//写入cookie
	    	Cookie tempCookie = new Cookie("Used", result.getUsedString());
	    	tempCookie.setMaxAge(3600*3);
	    	response.addCookie(tempCookie);
	    	tempCookie = new Cookie("Remain", result.getRemainderString());
	    	tempCookie.setMaxAge(3600*3);
	    	response.addCookie(tempCookie);
	    	//System.out.println("userInfo\""+result.getUserinfo()+"\"");
	    	tempCookie = new Cookie("UserInfo", URLEncoder.encode(result.getUserinfo(), "utf-8"));
	    	tempCookie.setMaxAge(3600*3);
	    	response.addCookie(tempCookie);
	    	//存上次查询的时间，用来计算几小时前更新的那个字段，两小时有效
	    	Date now = new Date(); 
	    	tempCookie = new Cookie("searchTag",String.valueOf(now.getTime()));
	    	tempCookie.setMaxAge(3600*2);
	    	response.addCookie(tempCookie);
	    	tempView.addObject("hours", 0);
	    	tempView.addObject("minutes", 1);
	    	flowStatisticsService.addOkNum();
	    }
	    return tempView;
	    
	    } catch (Exception e) {
	    	flowStatisticsService.addNum();
	    	e.printStackTrace();
			tempView.setViewName("flow/queryFlow");
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
	    	return tempView;
		}
	}
	
	@RequestMapping(value="queryOut",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryOut(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView = new ModelAndView("flow/queryFlow");
		//清cookie
		Cookie cookie = new Cookie("username", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("password", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("searchTag", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("Used", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("Remain", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("UserInfo", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return tempView;
	}
}
