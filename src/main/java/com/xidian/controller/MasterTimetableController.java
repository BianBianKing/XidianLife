package com.xidian.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xidian.forms.MasterTimetable;
import com.xidian.forms.SemesterWeekNum;
import com.xidian.running.LoginException;
import com.xidian.service.api.MasterTimetableService;
import com.xidian.service.api.SemesterWeekNumService;
import com.xidian.timetable.Main;
import com.xidian.timetable.UserInfo;
import com.xidian.util.CookieTool;
import com.xidian.util.QuerySecret;

@Controller
public class MasterTimetableController {
	@Resource(name="masterTimetableServiceImpl")
	private MasterTimetableService masterTimetableService;
	@Resource(name="semesterWeekNumServiceImpl")
	SemesterWeekNumService semesterWeekNumService;
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("queryMasterTimeTable.htm")
	public String queryMasterTimeTable(Model model, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		Map<String, String> timetableInfo = new HashMap<String, String>(){
			{
				put("usernameMTimetable", null);
				put("passwordMTimetable", null);
				put("mTimetableTag", null);
			}
		};
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if(timetableInfo.containsKey(cookie.getName()))
					try {
						if(!cookie.getName().equals("mTimetableTag"))
							timetableInfo.put(cookie.getName(), QuerySecret.deleteSecret(URLDecoder.decode(cookie.getValue(),"utf-8")));
						else {
							System.out.println("\n"+cookie.getName()+" " + cookie.getValue());
							timetableInfo.put(cookie.getName(), cookie.getValue());
						}
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException(e);
					}
			}
		}
		System.out.println(timetableInfo);
		String username = timetableInfo.get("usernameMTimetable");
		String password = timetableInfo.get("passwordMTimetable");
		String mTimetableTag = timetableInfo.get("mTimetableTag");
		//不到1小时，直接在数据库中取
		if(mTimetableTag != null && username != null && password != null){
			String redStr;
			try {
				// 计算刷新时间
				Date nowTime = new Date();
				long diff = nowTime.getTime() - Long.parseLong(mTimetableTag);
				System.out.println(diff);
				long hours = (diff) / (1000 * 60 * 60);
				long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
				model.addAttribute("hours", hours);
				model.addAttribute("minutes", minutes + 1);
				//获取教学周
				SemesterWeekNum semesterWeekNum = semesterWeekNumService.getSemesterWeekNum();
				if(semesterWeekNum == null){
					model.addAttribute("semesterWeekNum", "当前未设置");
				}
				else
					model.addAttribute("semesterWeekNum", semesterWeekNum.getWeeknum());
				//
				redStr = java.net.URLDecoder.decode(masterTimetableService.getMasterTimetableByUser(username).getContent(), "UTF-8");
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
				UserInfo userInfo = (UserInfo) objectInputStream.readObject();
				objectInputStream.close();
				byteArrayInputStream.close();
				Main.printUserCourse(userInfo);
				//System.out.println(userInfo.getMyCourse());
				model.addAttribute("courses",exchangeArray(userInfo.getMyCourse()));
				System.out.println(Arrays.deepToString(exchangeArray(userInfo.getMyCourse())));
				//进行是否第一次的判断
				model.addAttribute("minutes", 1);
				Cookie firstCheckMT = CookieTool.getCookieByName(request, "firstCheckMT");
				if(firstCheckMT != null && firstCheckMT.getValue().equals("0")){
					model.addAttribute("firstCheck", "0");
				}
				else{
					model.addAttribute("firstCheck", "1");
					CookieTool.addCookie(response, "firstCheckMT", "0", Integer.MAX_VALUE);
				}
				return "masterTimetable/timetableResult";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//超过1小时，重新查询
		else if(mTimetableTag == null && username != null && password != null){
			try{
				UserInfo userInfo = Main.test(username, password);
				if(userInfo == null){
					model.addAttribute("status", "fail");
					model.addAttribute("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
				}
				Main.printUserCourse(userInfo);
				model.addAttribute("courses", exchangeArray(userInfo.getMyCourse()));
				//获取教学周
				SemesterWeekNum semesterWeekNum = semesterWeekNumService.getSemesterWeekNum();
				if(semesterWeekNum == null){
					model.addAttribute("semesterWeekNum", "当前未设置");
				}
				else
					model.addAttribute("semesterWeekNum", semesterWeekNum.getWeeknum());
				// 序列化
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(userInfo);
				String serStr = byteArrayOutputStream.toString("ISO-8859-1");
				serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
				objectOutputStream.close();
				byteArrayOutputStream.close();
				// 写入数据库
				MasterTimetable masterTimetable = new MasterTimetable(username,serStr);
				masterTimetableService.addMasterTimetable(masterTimetable);
				// 设置1小时有效，用于显示刷新时间
				Date now = new Date();
				Cookie tempCookie = new Cookie("mTimetableTag",String.valueOf(now.getTime()));
				tempCookie.setMaxAge(3600);
				response.addCookie(tempCookie);
				model.addAttribute("hours", 0);
				//进行是否第一次的判断
				model.addAttribute("minutes", 1);
				Cookie firstCheckMT = CookieTool.getCookieByName(request, "firstCheckMT");
				if(firstCheckMT != null && firstCheckMT.getValue().equals("0")){
					model.addAttribute("firstCheck", "0");
				}
				else{
					model.addAttribute("firstCheck", "1");
					CookieTool.addCookie(response, "firstCheckMT", "0", Integer.MAX_VALUE);
				}
				return "masterTimetable/timetableResult";
			//测试是否获取到，判断是用户名密码错误还是连接不上服务器
			//存数据库，写cookie
			}catch(RuntimeException e){
				try {
					throw e.getCause();
				} catch (ConnectTimeoutException e1) {
					System.out.println("连接不上学校服务器");
					model.addAttribute("status", "fail");
					model.addAttribute("info", "学校查询服务器无法连接");
					Cookie cookie = new Cookie("mTimetableTag", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				} catch (LoginException e1) {
					System.out.println("用户名密码错误");
					model.addAttribute("status", "fail");
					model.addAttribute("info", "用户名密码错误");
					e1.printStackTrace();
				} catch (Throwable e1) {
					model.addAttribute("status", "fail");
					model.addAttribute("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
					Cookie cookie = new Cookie("mTimetableTag", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					e1.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "masterTimetable/timetableCheck";
	}
	
	@RequestMapping("masterTimetableResult")
	public String masterTimetableResult(Model model, String username, String password, HttpServletResponse response){
		try{
			UserInfo userInfo = Main.test(username, password);
			if(userInfo == null){
				model.addAttribute("status", "fail");
				model.addAttribute("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
			}
			Cookie userCookie = new Cookie("usernameMTimetable", URLEncoder.encode(QuerySecret.addSecret(username), "utf-8"));
			Cookie passCookie = new Cookie("passwordMTimetable", URLEncoder.encode(QuerySecret.addSecret(password), "utf-8"));
			
			Main.printUserCourse(userInfo);
			model.addAttribute("courses", exchangeArray(userInfo.getMyCourse()));
			//获取教学周
			SemesterWeekNum semesterWeekNum = semesterWeekNumService.getSemesterWeekNum();
			if(semesterWeekNum == null){
				model.addAttribute("semesterWeekNum", "当前未设置");
			}
			else
				model.addAttribute("semesterWeekNum", semesterWeekNum.getWeeknum());
			// 序列化
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(userInfo);
			String serStr = byteArrayOutputStream.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
			objectOutputStream.close();
			byteArrayOutputStream.close();
			// 写入数据库
			MasterTimetable masterTimetable = new MasterTimetable(username,serStr);
			masterTimetableService.addMasterTimetable(masterTimetable);
			// 设置1小时有效，用于显示刷新时间
			Date now = new Date();
			Cookie tempCookie = new Cookie("mTimetableTag",String.valueOf(now.getTime()));
			System.out.println(String.valueOf(now.getTime()));
			tempCookie.setMaxAge(3600);
			response.addCookie(tempCookie);
			userCookie.setMaxAge(Integer.MAX_VALUE);
			passCookie.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(userCookie);
			response.addCookie(passCookie);
			model.addAttribute("hours", 0);
			model.addAttribute("minutes", 1);
			//进行是否第一次的判断
			Cookie firstCheckMT = CookieTool.getCookieByName(request, "firstCheckMT");
			if(firstCheckMT != null && firstCheckMT.getValue().equals("0")){
				model.addAttribute("firstCheck", "0");
			}
			else{
				model.addAttribute("firstCheck", "1");
				CookieTool.addCookie(response, "firstCheckMT", "0", Integer.MAX_VALUE);
			}
			return "masterTimetable/timetableResult";
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
				Cookie cookie = new Cookie("mTimetableTag", null);
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
				Cookie cookie = new Cookie("mTimetableTag", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "masterTimetable/timetableCheck";
	}
	
	@RequestMapping("queryMasterTimetableOut")
	public String queryMasterTimetableOut(HttpServletResponse response) {
		Cookie cookie = new Cookie("usernameMTimetable", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("passwordMTimetable", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("mTimetableTag", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "masterTimetable/timetableCheck";
	}
	private String[][] exchangeArray(String[][] in){
		int length1 = in.length - 1;
		int length2 = in[0].length - 1;
		String[][] out = new String[length2/2][length1];
		for(int i = 0; i < length2; i = i + 2){
			for(int j = 0; j < length1; j++){
				if(in[j+1][i+1] != null && in[j+1][i+1].length()>1){
					out[i/2][j] = in[j+1][i+1];//splitTimetalbe(in[j+1][i+1]).toString();
				}
			}
		}
		return out;
	}
	private StringBuilder splitTimetalbe(String str){
		StringBuilder sb = new StringBuilder("");
		Pattern pattern = Pattern.compile("[\\W&&[^\\-]&&[^\\,\\，]&&\\S].*");
		List<String> strList = new LinkedList<String>();
		while(true){
			Matcher matcher = pattern.matcher(str);
			if(matcher.find()){
				String str2 = matcher.group();
				strList.add(str2);
				//System.out.println(str2);
				String[] str3 = str2.split(" ", 3);
				str = str3[2];
			}
			else
				break;
		}
		if(strList.size() > 0){
			for(int i = 0; i < strList.size() - 1; i++){
				sb.append(strList.get(i).replace(strList.get(i+1), "") + "|");
			}
			sb.append(strList.get(strList.size()-1));
		}
		System.out.println(sb);
		return sb;
	}
}
