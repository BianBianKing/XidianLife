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
	//��֤�볢�Դ���
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
			
			// ��ѯʱ���Ѿ�������Сʱ�����û����������
			if (flowTag == null && usernameFlow != null && passwordFlow != null) {
				HttpClientManager.init();
				UserInfo user = new UserInfo(usernameFlow,passwordFlow);
				boolean loginOk=false;
				//��֤�볢��TRY_NUM�Σ�cc���ڼ���
				int cc=0;
				do{
					cc++;
					if(cc>TRY_NUM)
					{
						tempView.addObject("status", "fail");
						tempView.addObject("info", "��֤���Զ�ʶ��ʧ�ܣ����Ժ�����");
						// �˳�����cookie
						tempView.setViewName("flow/queryFlow");
						flowStatisticsService.addNum();
						//ɾ���ļ�
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
							// �˳�����cookie
							tempView.setViewName("flow/queryFlow");
							flowStatisticsService.addNum();
							//ɾ���ļ�
							File f1 = new File(user.imagePath+".png");
							if(f1.exists())
								f1.deleteOnExit();
							return tempView;
						}
					}
					else{
						tempView.addObject("status", "fail");
						tempView.addObject("info", "��ȡ��¼��Ϣʧ��");
						// �˳�����cookie
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
						//�ж��ǲ��ǰ����û�
						if(user.fInfo.flow2 == null || user.fInfo.flow2.size() < 1){
							newAllFlow = mealNum;
							newUsedFlow = FlowUtil.getGbView(user.fInfo.usedFlow);
							newLeftFlow = newAllFlow - newUsedFlow;
						}
						else{
							List<UserInfo.FlowInfo.Flow2> flowList = user.fInfo.flow2;
							
							if(flowList != null && flowList.size() > 0){//��ֹlistΪ��
								//��Ϊ����ֻ�ᱣ��2���£����ֻ���������2������
								//�����ϸ��µ�����
								if(flowList.size() > 1){
									UserInfo.FlowInfo.Flow2 flow = flowList.get(flowList.size() - 2);
									//���ʣ������С�ڵ���0���Ͳ�����ͳ����
									if(FlowUtil.getGbView(flow.left) > 0){
										newAllFlow += FlowUtil.getGbView(flow.allFlow);
										newUsedFlow += FlowUtil.getGbView(flow.used);
										newLeftFlow += FlowUtil.getGbView(flow.left);
									}
								}
								//��������µ�Ҫ����ͳ��
								newAllFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).allFlow);
								newUsedFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).used);
								newLeftFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).left);
							}
							//�����ײ�������
							newAllFlow += (FlowUtil.getGbView(user.fInfo.extraFlow)+FlowUtil.getGbView(user.fInfo.usedFlow));
							newUsedFlow += FlowUtil.getGbView(user.fInfo.usedFlow);
							newLeftFlow += FlowUtil.getGbView(user.fInfo.extraFlow);
						}
							
					}
					else{
						flowStatisticsService.addNum();
						tempView.setViewName("flow/queryFlow");
						tempView.addObject("status", "fail");
						tempView.addObject("info", "��Ǹ�����Ժ����ԣ���ֱ��ͨ��΢����СĢ����ϵ��");
						return tempView;
					}
					
					int newPercentFlow = (int)(newUsedFlow/newAllFlow * 100);
					tempView.addObject("allFlow", FlowUtil.getGoodViewByGb(newAllFlow));
					tempView.addObject("leftFlow", FlowUtil.getGoodViewByGb(newLeftFlow));
					tempView.addObject("usedFlow", FlowUtil.getGoodViewByGb(newUsedFlow));
					tempView.addObject("percentFlow", newPercentFlow);
					tempView.addObject("username", user.getUserName());
					//д��cookie
					//����ѯ���д��cookie����Сʱ��Ч���Դ�������ѧУ��������ѹ��
					CookieTool.addCookie(response, "allFlow", FlowUtil.getGoodViewByGb(newAllFlow), 3600 * 3);
					CookieTool.addCookie(response, "leftFlow", FlowUtil.getGoodViewByGb(newLeftFlow), 3600 * 3);
					CookieTool.addCookie(response, "usedFlow", FlowUtil.getGoodViewByGb(newUsedFlow), 3600 * 3);
					CookieTool.addCookie(response, "percentFlow", Integer.toString(newPercentFlow), 3600 * 3);
					// ���ϴβ�ѯ��ʱ�䣬�������㼸Сʱǰ���µ��Ǹ��ֶΣ���Сʱ��Ч
					Date now = new Date();
					CookieTool.addCookie(response, "flowTag", String.valueOf(now.getTime()), 3600 * 2);
					tempView.addObject("hours", 0);
					tempView.addObject("minutes", 1);
					flowStatisticsService.addOkNum();
					tempView.setViewName("flow/queryFlowPage");
					//ɾ���ļ�
					File f1 = new File(user.imagePath+".png");
					if(f1.exists())
						f1.deleteOnExit();
					
					return tempView;
				}
				
				
			}
			// ������Сʱ����cookie
			if (flowTag != null) {
				tempView.addObject("allFlow", allFlow);
				tempView.addObject("leftFlow", leftFlow);
				tempView.addObject("usedFlow", usedFlow);
				tempView.addObject("percentFlow", percentFlow);
				tempView.addObject("username", usernameFlow);
				// ����ʱ���
				Date nowTime = new Date();
				long diff = nowTime.getTime() - Long.parseLong(flowTag);
				long hours = (diff) / (1000 * 60 * 60);
				long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
				tempView.addObject("hours", hours);
				tempView.addObject("minutes", minutes + 1);
				// ���ز�ѯҳ��
				tempView.setViewName("flow/queryFlowPage");
				flowStatisticsService.addOkNum();
			}
			return tempView;

		} catch (Exception e) {
			flowStatisticsService.addNum();
			tempView.setViewName("flow/queryFlow");
			tempView.addObject("status", "fail");
			tempView.addObject("info", "��Ǹ�����Ժ����ԣ���ֱ��ͨ��΢����СĢ����ϵ��");
			return tempView;
		}
	}

	@RequestMapping(value = "queryFlow", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView queryFlowAction(String username, String password, HttpServletResponse response) throws IOException {
		// �״ε�½
		ModelAndView tempView = new ModelAndView("flow/queryFlowPage");

		try {

			//Cookie userCookie = new Cookie("usernameFlow", username);
			//Cookie passCookie = new Cookie("passwordFlow", password);

			HttpClientManager.init();
			UserInfo user = new UserInfo(username, password);
			boolean loginOk=false;
			//��֤�볢��5�Σ�cc���ڼ���
			int cc=0;
			do{
				cc++;
				if(cc>TRY_NUM)
				{
					tempView.addObject("status", "fail");
					tempView.addObject("info", "��֤���Զ�ʶ��ʧ�ܣ����Ժ�����");
					// �˳�����cookie
					tempView.setViewName("flow/queryFlow");
					flowStatisticsService.addNum();
					//ɾ���ļ�
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
						// �˳�����cookie
						tempView.setViewName("flow/queryFlow");
						flowStatisticsService.addNum();
						//ɾ���ļ�
						File f1 = new File(user.imagePath+".png");
						if(f1.exists())
							f1.deleteOnExit();
						return tempView;
					}
				}
				else{
					tempView.addObject("status", "fail");
					tempView.addObject("info", "��ȡ��¼��Ϣʧ��");
					// �˳�����cookie
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
					//�ж��ǲ��ǰ����û�
					if(mealNum == 40){
						newAllFlow = 40;
						newUsedFlow = FlowUtil.getGbView(user.fInfo.usedFlow);
						newLeftFlow = newAllFlow - newUsedFlow;
					}
					else{
						List<UserInfo.FlowInfo.Flow2> flowList = user.fInfo.flow2;
						
						if(flowList != null && flowList.size() > 0){//��ֹlistΪ��
							//��Ϊ����ֻ�ᱣ��2���£����ֻ���������2������
							//�����ϸ��µ�����
							if(flowList.size() > 1){
								UserInfo.FlowInfo.Flow2 flow = flowList.get(flowList.size() - 2);
								//���ʣ������С�ڵ���0���Ͳ�����ͳ����
								if(FlowUtil.getGbView(flow.left) > 0){
									newAllFlow += FlowUtil.getGbView(flow.allFlow);
									newUsedFlow += FlowUtil.getGbView(flow.used);
									newLeftFlow += FlowUtil.getGbView(flow.left);
								}
							}
							//��������µ�Ҫ����ͳ��
							newAllFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).allFlow);
							newUsedFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).used);
							newLeftFlow += FlowUtil.getGbView(flowList.get(flowList.size() - 1).left);
						}
						//�����ײ�������
						newAllFlow += (FlowUtil.getGbView(user.fInfo.extraFlow)+FlowUtil.getGbView(user.fInfo.usedFlow));
						newUsedFlow += FlowUtil.getGbView(user.fInfo.usedFlow);
						newLeftFlow += FlowUtil.getGbView(user.fInfo.extraFlow);
					}
						
				}
				else{
					flowStatisticsService.addNum();
					tempView.setViewName("flow/queryFlow");
					tempView.addObject("status", "fail");
					tempView.addObject("info", "��Ǹ�����Ժ����ԣ���ֱ��ͨ��΢����СĢ����ϵ��");
					return tempView;
				}
				
				int newPercentFlow = (int)(newUsedFlow/newAllFlow * 100);
				tempView.addObject("allFlow", FlowUtil.getGoodViewByGb(newAllFlow));
				tempView.addObject("leftFlow", FlowUtil.getGoodViewByGb(newLeftFlow));
				tempView.addObject("usedFlow", FlowUtil.getGoodViewByGb(newUsedFlow));
				tempView.addObject("percentFlow", newPercentFlow);
				tempView.addObject("username", user.getUserName());
				//д��cookie
				//����ѯ���д��cookie����Сʱ��Ч���Դ�������ѧУ��������ѹ��
				//userCookie.setMaxAge(Integer.MAX_VALUE);
				//passCookie.setMaxAge(Integer.MAX_VALUE);
				// �û�������д��cookie
				CookieTool.addCookie(response, "usernameFlow", username, Integer.MAX_VALUE);
				CookieTool.addCookie(response, "passwordFlow", password, Integer.MAX_VALUE);
				//response.addCookie(userCookie);
				//response.addCookie(passCookie);
				CookieTool.addCookie(response, "allFlow", FlowUtil.getGoodViewByGb(newAllFlow), 3600 * 3);
				CookieTool.addCookie(response, "leftFlow", FlowUtil.getGoodViewByGb(newLeftFlow), 3600 * 3);
				CookieTool.addCookie(response, "usedFlow", FlowUtil.getGoodViewByGb(newUsedFlow), 3600 * 3);
				CookieTool.addCookie(response, "percentFlow", Integer.toString(newPercentFlow), 3600 * 3);
				// ���ϴβ�ѯ��ʱ�䣬�������㼸Сʱǰ���µ��Ǹ��ֶΣ���Сʱ��Ч
				Date now = new Date();
				CookieTool.addCookie(response, "flowTag", String.valueOf(now.getTime()), 3600 * 2);
				tempView.addObject("hours", 0);
				tempView.addObject("minutes", 1);
				flowStatisticsService.addOkNum();
				tempView.setViewName("flow/queryFlowPage");
				//ɾ���ļ�
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
			tempView.addObject("info", "��Ǹ�����Ժ����ԣ���ֱ��ͨ��΢����СĢ����ϵ��");
			return tempView;
		}
	}

	@RequestMapping(value = "queryOut", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView queryOut(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView tempView = new ModelAndView("flow/queryFlow");
		// ��cookie
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
