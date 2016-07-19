package com.xidian.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.flow.Main;
import com.xidian.flow.Result;
import com.xidian.flow.UserManager;
import com.xidian.forms.GradeTemp;
import com.xidian.forms.RunningTemp;
import com.xidian.mastergrade.MasterUserInfo;
import com.xidian.running.LoginException;
import com.xidian.running.MainRunning;
import com.xidian.running.RunningInfo;
import com.xidian.running.RunningUserInfo;
import com.xidian.service.api.RunningService;
import com.xidian.util.QuerySecret;


@Controller
public class RunningController {
	@Resource(name="runningServiceImpl")
	RunningService runningService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("queryRunning.htm")
	public String queryRunning(Model model, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		Map<String, String> runningInfo = new HashMap<String, String>(){
			{
				put("usernameRun", null);
				put("passwordRun", null);
				put("runningTag", null);
			}
		};
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if(runningInfo.containsKey(cookie.getName()))
					try {
						if(!cookie.getName().equals("runningTag"))
							runningInfo.put(cookie.getName(), QuerySecret.deleteSecret(URLDecoder.decode(cookie.getValue(),"utf-8")));
						else {
							System.out.println("\n"+cookie.getName()+" " + cookie.getValue());
							runningInfo.put(cookie.getName(), cookie.getValue());
						}
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException(e);
					}
			}
		}
		System.out.println(runningInfo);
		String username = runningInfo.get("usernameRun");
		String password = runningInfo.get("passwordRun");
		String runningTag = runningInfo.get("runningTag");
		//不到1小时，直接在数据库中取
		if(runningTag != null && username != null && password != null){
			String redStr;
			try {
				// 计算刷新时间
				Date nowTime = new Date();
				long diff = nowTime.getTime() - Long.parseLong(runningTag);
				System.out.println(diff);
				long hours = (diff) / (1000 * 60 * 60);
				long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
				model.addAttribute("hours", hours);
				model.addAttribute("minutes", minutes + 1);
				
				redStr = java.net.URLDecoder.decode(runningService.getRunningTempByUser(username).getContent(), "UTF-8");
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
				RunningUserInfo runningUserInfo = (RunningUserInfo) objectInputStream.readObject();
				objectInputStream.close();
				byteArrayInputStream.close();
				System.out.println(runningUserInfo);
				model.addAttribute("runningUserInfo",runningUserInfo);
				List<RunningInfo> runningInfoList = runningUserInfo.getRunningInfos();
				List<Integer> dayList = getDateStringList(runningInfoList);
				List<String> dateList = getComDateStringList(runningInfoList);
				System.out.println(dateList);
				model.addAttribute("dayList", dayList);
				model.addAttribute("dateList", dateList);
				//model.addAttribute("infoList", runningUserInfo.getRunningInfos());
				return "running/runningResult";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//超过1小时，重新查询
		else if(runningTag == null && username != null && password != null){
			try{
				RunningUserInfo runningUserInfo = MainRunning.test(username, password);
				if(runningUserInfo == null){
					model.addAttribute("status", "fail");
					model.addAttribute("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
					return "running/runningCheck";
				}
				System.out.println(runningUserInfo);
				model.addAttribute("runningUserInfo", runningUserInfo);
				//处理具体的长跑信息，生成字符串的形式
				List<RunningInfo> runningInfoList = runningUserInfo.getRunningInfos();
				List<Integer> dayList = getDateStringList(runningInfoList);
				List<String> dateList = getComDateStringList(runningInfoList);
				System.out.println(dateList);
				model.addAttribute("dayList", dayList);
				model.addAttribute("dateList", dateList);
				//model.addAttribute("infoList", runningUserInfo.getRunningInfos());
				// 序列化
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(runningUserInfo);
				String serStr = byteArrayOutputStream.toString("ISO-8859-1");
				serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
				objectOutputStream.close();
				byteArrayOutputStream.close();
				// 写入数据库
				RunningTemp runningTemp = new RunningTemp(username,serStr);
				runningService.addRunningTemp(runningTemp);
				// 设置1小时有效，用于显示刷新时间
				Date now = new Date();
				Cookie tempCookie = new Cookie("runningTag",String.valueOf(now.getTime()));
				tempCookie.setMaxAge(3600);
				response.addCookie(tempCookie);
				model.addAttribute("hours", 0);
				model.addAttribute("minutes", 1);
				return "running/runningResult";
			//测试是否获取到，判断是用户名密码错误还是连接不上服务器
			//存数据库，写cookie
			}catch(RuntimeException e){
				try {
					throw e.getCause();
				} catch (ConnectTimeoutException e1) {
					System.out.println("连接不上学校服务器");
					model.addAttribute("status", "fail");
					model.addAttribute("info", "学校查询服务器无法连接");
					Cookie cookie = new Cookie("runningTag", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				} catch (HttpHostConnectException e1) {
					System.out.println("连接不上学校服务器");
					model.addAttribute("status", "fail");
					model.addAttribute("info", "学校查询服务器无法连接");
					Cookie cookie = new Cookie("runningTag", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					e1.printStackTrace();
				} catch (LoginException e1) {
					System.out.println("用户名密码错误");
					model.addAttribute("status", "fail");
					model.addAttribute("info", "用户名密码错误");
					e1.printStackTrace();
				} catch (Throwable e1) {
					model.addAttribute("status", "fail");
					model.addAttribute("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
					Cookie cookie = new Cookie("runningTag", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					e1.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "running/runningCheck";
	}
	
	@RequestMapping("runningResult")
	public String runningResult(Model model, String username, String password, HttpServletResponse response){
		try{
			RunningUserInfo runningUserInfo = MainRunning.test(username, password);
			if(runningUserInfo == null){
				model.addAttribute("status", "fail");
				model.addAttribute("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
				return "running/runningCheck";
			}
			Cookie userCookie = new Cookie("usernameRun", URLEncoder.encode(QuerySecret.addSecret(username), "utf-8"));
			Cookie passCookie = new Cookie("passwordRun", URLEncoder.encode(QuerySecret.addSecret(password), "utf-8"));
			System.out.println(runningUserInfo);
			model.addAttribute("runningUserInfo", runningUserInfo);
			//处理具体的长跑信息，生成字符串的形式
			List<RunningInfo> runningInfoList = runningUserInfo.getRunningInfos();
			List<Integer> dayList = getDateStringList(runningInfoList);
			List<String> dateList = getComDateStringList(runningInfoList);
			System.out.println(dateList);
			model.addAttribute("dayList", dayList);
			model.addAttribute("dateList", dateList);
			// 序列化
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(runningUserInfo);
			String serStr = byteArrayOutputStream.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
			objectOutputStream.close();
			byteArrayOutputStream.close();
			// 写入数据库
			RunningTemp runningTemp = new RunningTemp(username,serStr);
			runningService.addRunningTemp(runningTemp);
			// 设置1小时有效，用于显示刷新时间
			Date now = new Date();
			Cookie tempCookie = new Cookie("runningTag",String.valueOf(now.getTime()));
			System.out.println(String.valueOf(now.getTime()));
			tempCookie.setMaxAge(3600);
			response.addCookie(tempCookie);
			userCookie.setMaxAge(Integer.MAX_VALUE);
			passCookie.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(userCookie);
			response.addCookie(passCookie);
			model.addAttribute("hours", 0);
			model.addAttribute("minutes", 1);
			return "running/runningResult";
		//测试是否获取到，判断是用户名密码错误还是连接不上服务器
		//存数据库，写cookie
		}catch(RuntimeException e){
			try {
				Throwable t = e.getCause();
				if(t != null)
					throw t;
				e.printStackTrace();
			} catch (ConnectTimeoutException e1) {
				System.out.println("连接不上学校服务器");
				model.addAttribute("status", "fail");
				model.addAttribute("info", "学校查询服务器无法连接");
				Cookie cookie = new Cookie("runningTag", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				e1.printStackTrace();
			}catch (HttpHostConnectException e1) {
				System.out.println("连接不上学校服务器");
				model.addAttribute("status", "fail");
				model.addAttribute("info", "学校查询服务器无法连接");
				Cookie cookie = new Cookie("runningTag", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				e1.printStackTrace();
			}
			catch (LoginException e1) {
				System.out.println("用户名密码错误");
				model.addAttribute("status", "fail");
				model.addAttribute("info", "用户名密码错误");
				e1.printStackTrace();
			} catch (Throwable e1) {
				model.addAttribute("status", "fail");
				model.addAttribute("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
				Cookie cookie = new Cookie("runningTag", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "running/runningCheck";
	}
	@RequestMapping("queryRunningOut")
	public String queryRunningOut(HttpServletResponse response) {
		Cookie cookie = new Cookie("usernameRun", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("passwordRun", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("runningTag", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "running/runningCheck";
	}
	public void distance(){
		/*List<String> dateList = new LinkedList<String>();
		List<String> distanceList = new LinkedList<String>();
		Pattern pattern = Pattern.compile("\\d(\\.\\d+)?");
		Matcher matcher2 = pattern.matcher("4米");
		if(matcher2.find())
			distanceList.add(matcher2.group());
		if(runningInfoList.size() <= 7)
			for(RunningInfo ri : runningInfoList){
				dateList.add(ri.getDate());
				Matcher matcher = pattern.matcher(ri.getDistance());
				if(matcher.find())
					distanceList.add(matcher.group());
			}
		else
			for(int i = runningInfoList.size() - 7; i < runningInfoList.size(); i++){
				RunningInfo ri = runningInfoList.get(i);
				dateList.add(ri.getDate());
				Matcher matcher = pattern.matcher(ri.getDistance());
				if(matcher.find())
					distanceList.add(matcher.group());
			}
		//System.out.println(distanceList);
		model.addAttribute("dateArray", dateList.toString());
		model.addAttribute("distanceArray", distanceList.toString());*/
	}
	public List<Integer> getDateStringList(List<RunningInfo> runningInfoList){
		List<Integer> dayList = new LinkedList<Integer>();
		for(RunningInfo ri : runningInfoList){
			String date = ri.getDate();
			Calendar calendar = Calendar.getInstance();
			int curMonth = calendar.get(Calendar.MONTH);
			int curYear = calendar.get(Calendar.YEAR);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				calendar.setTime(sdf.parse(date));
				if((curYear == calendar.get(Calendar.YEAR)) && (curMonth == calendar.get(Calendar.MONTH))){
					dayList.add(calendar.get(Calendar.DAY_OF_MONTH));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			/*System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
			System.out.println(calendar.get(Calendar.YEAR));
			System.out.println(calendar.get(Calendar.MONTH));*/
		}
		return dayList;
	}
	public List<String> getComDateStringList(List<RunningInfo> runningInfoList){
		List<String> dateList = new LinkedList<String>();
		for(RunningInfo ri : runningInfoList){
			String date = ri.getDate();
			date = date.replace("-", "/");
			dateList.add(date);
		}
		return dateList;
	}
}
