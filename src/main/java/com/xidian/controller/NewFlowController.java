package com.xidian.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.flow.Main;
import com.xidian.flow.Result;
import com.xidian.flow.UserManager;
import com.xidian.newflow.HttpClientManager;
import com.xidian.newflow.HttpOperate;
import com.xidian.newflow.UserInfo;
import com.xidian.service.api.FlowStatisticsService;
import com.xidian.util.CookieTool;
import com.xidian.util.FlowUtil;
import com.xidian.util.QuerySecret;

@Controller
public class NewFlowController {
	@Resource(name = "userManager")
	UserManager userManager;
	@Resource(name = "flowStatisticsServiceImpl")
	FlowStatisticsService flowStatisticsService;
	@Autowired
	HttpServletRequest request;
	//验证码尝试次数
	private final int TRY_NUM = 10;

	@RequestMapping(value = "queryFlow.htm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView queryFlow(HttpServletResponse response) throws IOException {
		ModelAndView tempView = new ModelAndView("flow/queryFlow");
		try {
			Cookie[] cookies = request.getCookies();
			Map<String, String> flowInfo = new HashMap<String, String>(){
				{
					put("usernameFlow", null);
					put("passwordFlow", null);
					put("flowTag", null);
					put("allFlow",null);
					put("leftFlow",null);
					put("usedFlow",null);
					put("percentFlow",null);
				}
			};
			if(cookies != null){
				for (Cookie cookie : cookies) {
					if(flowInfo.containsKey(cookie.getName()))
						flowInfo.put(cookie.getName(), cookie.getValue());
				}
			}
			System.out.println(flowInfo);
			String usernameFlow = flowInfo.get("usernameFlow");
			String passwordFlow = flowInfo.get("passwordFlow");
			String flowTag = flowInfo.get("flowTag");
			
			String allFlow = flowInfo.get("allFlow");
			String leftFlow = flowInfo.get("leftFlow");
			String usedFlow = flowInfo.get("usedFlow");
			String percentFlow = flowInfo.get("percentFlow");
			
			// 查询时间已经超过两小时，且用户名密码存在
			if (flowTag == null && usernameFlow != null && passwordFlow != null) {
				HttpClientManager.init();
				UserInfo user = new UserInfo(usernameFlow,passwordFlow);
				boolean loginOk=false;
				//验证码尝试TRY_NUM次，cc用于计数
				int cc=0;
				do{
					cc++;
					if(cc>TRY_NUM)
					{
						tempView.addObject("status", "fail");
						tempView.addObject("info", "验证码自动识别失败，请稍后重试");
						// 退出，清cookie
						tempView.setViewName("flow/queryFlow");
						flowStatisticsService.addNum();
						//删除文件
						File f1 = new File(user.imagePath+".png");
						if(f1.exists())
							f1.deleteOnExit();
						return tempView;
					}
					if(HttpOperate.getLoginInfo(user)){
						loginOk=HttpOperate.loginFlowQuery(user);
						if(!loginOk&&!user.codeError.equals("")){
							tempView.addObject("status", "fail");
							tempView.addObject("info", user.userError);
							// 退出，清cookie
							tempView.setViewName("flow/queryFlow");
							flowStatisticsService.addNum();
							//删除文件
							File f1 = new File(user.imagePath+".png");
							if(f1.exists())
								f1.deleteOnExit();
							return tempView;
						}
					}
					else{
						tempView.addObject("status", "fail");
						tempView.addObject("info", "获取登录信息失败");
						// 退出，清cookie
						tempView.setViewName("flow/queryFlow");
						flowStatisticsService.addNum();
						return tempView;
					}
					
				}while(!loginOk);
				if(loginOk&&HttpOperate.getFlowInfo(user)){
					user.printFlowInfo();
					double newAllFlow = 0;
					double newUsedFlow = 0;
					double newLeftFlow = 0;
					
					Pattern pattern = Pattern.compile("\\d+");
					Matcher matcher = pattern.matcher(user.fInfo.groupName);
					if(matcher.find()){
						int mealNum = Integer.parseInt(matcher.group());
						//判断是不是包月用户
						if(user.fInfo.flow2 == null || user.fInfo.flow2.size() < 1){
							newAllFlow = mealNum;
							newUsedFlow = FlowUtil.getGbView(user.fInfo.usedFlow);
							newLeftFlow = newAllFlow - newUsedFlow;
						}
						else{
							List<UserInfo.FlowInfo.Flow2> flowList = user.fInfo.flow2;
							
							if(flowList != null && flowList.size() > 0){//防止list为空
								//因为流量只会保留2个月，最多只处理最近的2条数据
								//处理上个月的流量
								if(flowList.size() > 1){
									UserInfo.FlowInfo.Flow2 flow = flowList.get(flowList.size() - 2);
									//如果剩余流量小于等于0，就不计入统计中
									if(FlowUtil.getGbView(flow.left) > 0){
										newAllFlow += FlowUtil.getGbView(flow.allFlow);
										newUsedFlow += FlowUtil.getGbView(flow.used);
										newLeftFlow += FlowUtil.getGbView(flow.left);
									}
								}
								//现在这个月的要记入统计
								newAllFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).allFlow);
								newUsedFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).used);
								newLeftFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).left);
							}
							//记入套餐外流量
							newAllFlow += (FlowUtil.getGbView(user.fInfo.extraFlow)+FlowUtil.getGbView(user.fInfo.usedFlow));
							newUsedFlow += FlowUtil.getGbView(user.fInfo.usedFlow);
							newLeftFlow += FlowUtil.getGbView(user.fInfo.extraFlow);
						}
							
					}
					else{
						flowStatisticsService.addNum();
						tempView.setViewName("flow/queryFlow");
						tempView.addObject("status", "fail");
						tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
						return tempView;
					}
					
					int newPercentFlow = (int)(newUsedFlow/newAllFlow * 100);
					tempView.addObject("allFlow", FlowUtil.getGoodViewByGb(newAllFlow));
					tempView.addObject("leftFlow", FlowUtil.getGoodViewByGb(newLeftFlow));
					tempView.addObject("usedFlow", FlowUtil.getGoodViewByGb(newUsedFlow));
					tempView.addObject("percentFlow", newPercentFlow);
					tempView.addObject("username", user.getUserName());
					//写入cookie
					//将查询结果写入cookie，三小时有效，以此来减轻学校服务器的压力
					CookieTool.addCookie(response, "allFlow", FlowUtil.getGoodViewByGb(newAllFlow), 3600 * 3);
					CookieTool.addCookie(response, "leftFlow", FlowUtil.getGoodViewByGb(newLeftFlow), 3600 * 3);
					CookieTool.addCookie(response, "usedFlow", FlowUtil.getGoodViewByGb(newUsedFlow), 3600 * 3);
					CookieTool.addCookie(response, "percentFlow", Integer.toString(newPercentFlow), 3600 * 3);
					// 存上次查询的时间，用来计算几小时前更新的那个字段，两小时有效
					Date now = new Date();
					CookieTool.addCookie(response, "flowTag", String.valueOf(now.getTime()), 3600 * 2);
					tempView.addObject("hours", 0);
					tempView.addObject("minutes", 1);
					flowStatisticsService.addOkNum();
					tempView.setViewName("flow/queryFlowPage");
					//删除文件
					File f1 = new File(user.imagePath+".png");
					if(f1.exists())
						f1.deleteOnExit();
					
					return tempView;
				}
				
				
			}
			// 不足两小时，走cookie
			if (flowTag != null) {
				tempView.addObject("allFlow", allFlow);
				tempView.addObject("leftFlow", leftFlow);
				tempView.addObject("usedFlow", usedFlow);
				tempView.addObject("percentFlow", percentFlow);
				tempView.addObject("username", usernameFlow);
				// 计算时间差
				Date nowTime = new Date();
				long diff = nowTime.getTime() - Long.parseLong(flowTag);
				long hours = (diff) / (1000 * 60 * 60);
				long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
				tempView.addObject("hours", hours);
				tempView.addObject("minutes", minutes + 1);
				// 返回查询页面
				tempView.setViewName("flow/queryFlowPage");
				flowStatisticsService.addOkNum();
			}
			return tempView;

		} catch (Exception e) {
			flowStatisticsService.addNum();
			tempView.setViewName("flow/queryFlow");
			tempView.addObject("status", "fail");
			tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
			return tempView;
		}
	}

	@RequestMapping(value = "queryFlow", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView queryFlowAction(String username, String password, HttpServletResponse response) throws IOException {
		// 首次登陆
		ModelAndView tempView = new ModelAndView("flow/queryFlowPage");

		try {

			//Cookie userCookie = new Cookie("usernameFlow", username);
			//Cookie passCookie = new Cookie("passwordFlow", password);

			HttpClientManager.init();
			UserInfo user = new UserInfo(username, password);
			boolean loginOk=false;
			//验证码尝试5次，cc用于计数
			int cc=0;
			do{
				cc++;
				if(cc>TRY_NUM)
				{
					tempView.addObject("status", "fail");
					tempView.addObject("info", "验证码自动识别失败，请稍后重试");
					// 退出，清cookie
					tempView.setViewName("flow/queryFlow");
					flowStatisticsService.addNum();
					//删除文件
					File f1 = new File(user.imagePath+".png");
					if(f1.exists())
						f1.deleteOnExit();
					return tempView;
				}
				if(HttpOperate.getLoginInfo(user)){
					loginOk=HttpOperate.loginFlowQuery(user);
					if(!loginOk&&!user.codeError.equals("")){
						tempView.addObject("status", "fail");
						tempView.addObject("info", user.userError);
						// 退出，清cookie
						tempView.setViewName("flow/queryFlow");
						flowStatisticsService.addNum();
						//删除文件
						File f1 = new File(user.imagePath+".png");
						if(f1.exists())
							f1.deleteOnExit();
						return tempView;
					}
				}
				else{
					tempView.addObject("status", "fail");
					tempView.addObject("info", "获取登录信息失败");
					// 退出，清cookie
					tempView.setViewName("flow/queryFlow");
					flowStatisticsService.addNum();
					return tempView;
				}
				
			}while(!loginOk);
			if(loginOk&&HttpOperate.getFlowInfo(user)){
				user.printFlowInfo();
				double newAllFlow = 0;
				double newUsedFlow = 0;
				double newLeftFlow = 0;
				
				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(user.fInfo.groupName);
				if(matcher.find()){
					int mealNum = Integer.parseInt(matcher.group());
					//判断是不是包月用户
					if(mealNum == 40){
						newAllFlow = 40;
						newUsedFlow = FlowUtil.getGbView(user.fInfo.usedFlow);
						newLeftFlow = newAllFlow - newUsedFlow;
					}
					else{
						List<UserInfo.FlowInfo.Flow2> flowList = user.fInfo.flow2;
						
						if(flowList != null && flowList.size() > 0){//防止list为空
							//因为流量只会保留2个月，最多只处理最近的2条数据
							//处理上个月的流量
							if(flowList.size() > 1){
								UserInfo.FlowInfo.Flow2 flow = flowList.get(flowList.size() - 2);
								//如果剩余流量小于等于0，就不计入统计中
								if(FlowUtil.getGbView(flow.left) > 0){
									newAllFlow += FlowUtil.getGbView(flow.allFlow);
									newUsedFlow += FlowUtil.getGbView(flow.used);
									newLeftFlow += FlowUtil.getGbView(flow.left);
								}
							}
							//现在这个月的要记入统计
							newAllFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).allFlow);
							newUsedFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).used);
							newLeftFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).left);
						}
						//记入套餐外流量
						newAllFlow += (FlowUtil.getGbView(user.fInfo.extraFlow)+FlowUtil.getGbView(user.fInfo.usedFlow));
						newUsedFlow += FlowUtil.getGbView(user.fInfo.usedFlow);
						newLeftFlow += FlowUtil.getGbView(user.fInfo.extraFlow);
					}
						
				}
				else{
					flowStatisticsService.addNum();
					tempView.setViewName("flow/queryFlow");
					tempView.addObject("status", "fail");
					tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
					return tempView;
				}
				
				int newPercentFlow = (int)(newUsedFlow/newAllFlow * 100);
				tempView.addObject("allFlow", FlowUtil.getGoodViewByGb(newAllFlow));
				tempView.addObject("leftFlow", FlowUtil.getGoodViewByGb(newLeftFlow));
				tempView.addObject("usedFlow", FlowUtil.getGoodViewByGb(newUsedFlow));
				tempView.addObject("percentFlow", newPercentFlow);
				tempView.addObject("username", user.getUserName());
				//写入cookie
				//将查询结果写入cookie，三小时有效，以此来减轻学校服务器的压力
				//userCookie.setMaxAge(Integer.MAX_VALUE);
				//passCookie.setMaxAge(Integer.MAX_VALUE);
				// 用户名密码写入cookie
				CookieTool.addCookie(response, "usernameFlow", username, Integer.MAX_VALUE);
				CookieTool.addCookie(response, "passwordFlow", password, Integer.MAX_VALUE);
				//response.addCookie(userCookie);
				//response.addCookie(passCookie);
				CookieTool.addCookie(response, "allFlow", FlowUtil.getGoodViewByGb(newAllFlow), 3600 * 3);
				CookieTool.addCookie(response, "leftFlow", FlowUtil.getGoodViewByGb(newLeftFlow), 3600 * 3);
				CookieTool.addCookie(response, "usedFlow", FlowUtil.getGoodViewByGb(newUsedFlow), 3600 * 3);
				CookieTool.addCookie(response, "percentFlow", Integer.toString(newPercentFlow), 3600 * 3);
				// 存上次查询的时间，用来计算几小时前更新的那个字段，两小时有效
				Date now = new Date();
				CookieTool.addCookie(response, "flowTag", String.valueOf(now.getTime()), 3600 * 2);
				tempView.addObject("hours", 0);
				tempView.addObject("minutes", 1);
				flowStatisticsService.addOkNum();
				tempView.setViewName("flow/queryFlowPage");
				//删除文件
				File f1 = new File(user.imagePath+".png");
				if(f1.exists())
					f1.deleteOnExit();
				
				return tempView;
			}
			else
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

	@RequestMapping(value = "queryOut", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView queryOut(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView tempView = new ModelAndView("flow/queryFlow");
		// 清cookie
		CookieTool.addCookie(response, "usernameFlow", null, 0);
		CookieTool.addCookie(response, "passwordFlow", null, 0);
		CookieTool.addCookie(response, "flowTag", null, 0);
		
		CookieTool.addCookie(response, "allFlow", null, 0);
		CookieTool.addCookie(response, "leftFlow", null, 0);
		CookieTool.addCookie(response, "usedFlow", null, 0);
		CookieTool.addCookie(response, "percentFlow", null, 0);
		return tempView;
	}
}
