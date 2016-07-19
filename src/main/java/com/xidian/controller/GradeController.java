package com.xidian.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NoHttpResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.dao.api.GradeStatisticsDao;
import com.xidian.forms.GradeTemp;
import com.xidian.grade.Main;
import com.xidian.grade.UserInfo;
import com.xidian.mastergrade.MasterMain;
import com.xidian.mastergrade.MasterUserInfo;
import com.xidian.service.api.GradeTempService;
import com.xidian.util.QuerySecret;
import com.xidian.service.api.GradeStatisticsService;

@Controller
public class GradeController {
	@Resource(name = "gradeTempServiceImpl")
	GradeTempService gradeTempService;
	@Resource(name = "gradeStatisticsServiceImpl")
	GradeStatisticsService gradeStatisticsService;

	@RequestMapping(value = "queryGrade.htm")
	public ModelAndView queryGrade(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "refresh", required = false) boolean refresh)
			throws IOException, ClassNotFoundException {
		ModelAndView tempView = new ModelAndView("grade/queryGrade");
		try {
			Cookie[] cookies = request.getCookies();
			String username = null;
			String password = null;
			String gradeTest = null;
			String level = null;
			// count用来判断用户名和密码是否存在，以及上次查询时间是否已过期
			int count = 0;
			// 减少循环时间，其实这么写不是很好，代码很冗余
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("usernameGrade")) {
						username = QuerySecret.deleteSecret(URLDecoder.decode(
								cookie.getValue(), "utf-8"));
						System.out.println(cookie.getValue());
						count++;
					}
					if (cookie.getName().equals("passwordGrade")) {
						password = QuerySecret.deleteSecret(URLDecoder.decode(
								cookie.getValue(), "utf-8"));
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
			MasterUserInfo masterUserInfo = null;
			UserInfo userInfo = null;
			System.out.println("\ngradeTest:"+gradeTest);
			// 用户名密码存在,且已过了1小时
			if (username != null && password != null && level != null
					&& (gradeTest == null || refresh)) {
				// System.out.println("进来了");
				if (level.equals("master")) {
					masterUserInfo = MasterMain.test(username, password);
					if (masterUserInfo == null) {
						gradeStatisticsService.addMasterNum();
						tempView.setViewName("grade/queryGrade");
						tempView.addObject("status", "fail");
						tempView.addObject("info", "用户名或密码错误！");
						
					} else {
						gradeStatisticsService.addMasterOkNum();
						tempView.setViewName("grade/queryMasterGradePage");
						tempView.addObject("grade",
								masterUserInfo.getGradeInfo());
						tempView.addObject("userinfo",
								masterUserInfo.getUserDesc());
						// 序列化
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(
								byteArrayOutputStream);
						objectOutputStream.writeObject(masterUserInfo);
						String serStr = byteArrayOutputStream
								.toString("ISO-8859-1");
						serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
						objectOutputStream.close();
						byteArrayOutputStream.close();
						// 写入数据库
						GradeTemp gradeTemp = new GradeTemp();
						gradeTemp.setContent(serStr);
						gradeTemp.setUsername(username);
						gradeTempService.addGradeTemp(gradeTemp);
						// 设置1小时有效，用于显示刷新时间
						Date now = new Date();
						Cookie tempCookie = new Cookie("gradeTest",
								String.valueOf(now.getTime()));
						tempCookie.setMaxAge(3600);
						response.addCookie(tempCookie);
						tempView.addObject("hours", 0);
						tempView.addObject("minutes", 1);

						return tempView;
					}
				} else if (level.equals("bachelor")) {
					userInfo = Main.test(username, password);
					if (userInfo == null) {
						gradeStatisticsService.addBacherlorNum();
						tempView.setViewName("grade/queryGrade");
						tempView.addObject("status", "fail");
						tempView.addObject("info", "用户名或密码错误！");
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
					} else {
						gradeStatisticsService.addBacherlorOkNum();
						tempView.setViewName("grade/queryGradePage");
						tempView.addObject("gradeOne",
								userInfo.getCurrentGradeInfos());
						tempView.addObject("gradeTwo",
								userInfo.getSemesterGrades());
						tempView.addObject("gradeThree",
								userInfo.getFailGradeInfo());
						tempView.addObject("userinfo", userInfo.getUserDesc());
						// 序列化
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(
								byteArrayOutputStream);
						objectOutputStream.writeObject(userInfo);
						String serStr = byteArrayOutputStream
								.toString("ISO-8859-1");
						serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
						objectOutputStream.close();
						byteArrayOutputStream.close();
						// 写入数据库
						GradeTemp gradeTemp = new GradeTemp();
						gradeTemp.setContent(serStr);
						gradeTemp.setUsername(username);
						gradeTempService.addGradeTemp(gradeTemp);
						// 设置1小时有效，用于显示刷新时间
						Date now = new Date();
						Cookie tempCookie = new Cookie("gradeTest",
								String.valueOf(now.getTime()));
						tempCookie.setMaxAge(3600);
						response.addCookie(tempCookie);
						tempView.addObject("hours", 0);
						tempView.addObject("minutes", 1);

						return tempView;
					}
				}

			} else if (username != null && password != null
					&& gradeTest != null && level != null) {
				System.out.println("success");
				// 计算刷新时间
				Date nowTime = new Date();
				long diff = nowTime.getTime() - Long.parseLong(gradeTest);
				System.out.println(diff);
				long hours = (diff) / (1000 * 60 * 60);
				long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
				tempView.addObject("hours", hours);
				tempView.addObject("minutes", minutes + 1);
				if (level.equals("master")) {
					// 反序列化
					String redStr = java.net.URLDecoder
							.decode(gradeTempService.getGradeTempByUser(
									username).getContent(), "UTF-8");
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
							redStr.getBytes("ISO-8859-1"));
					ObjectInputStream objectInputStream = new ObjectInputStream(
							byteArrayInputStream);
					masterUserInfo = (MasterUserInfo) objectInputStream
							.readObject();
					objectInputStream.close();
					byteArrayInputStream.close();
					gradeStatisticsService.addMasterOkNum();
					tempView.setViewName("grade/queryMasterGradePage");
					tempView.addObject("grade", masterUserInfo.getGradeInfo());
					tempView.addObject("userinfo", masterUserInfo.getUserDesc());
				} else if (level.equals("bachelor")) {
					// 反序列化
					System.out.println(username);
					String redStr = java.net.URLDecoder
							.decode(gradeTempService.getGradeTempByUser(
									username).getContent(), "UTF-8");
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
							redStr.getBytes("ISO-8859-1"));
					ObjectInputStream objectInputStream = new ObjectInputStream(
							byteArrayInputStream);
					userInfo = (UserInfo) objectInputStream.readObject();
					objectInputStream.close();
					byteArrayInputStream.close();
					gradeStatisticsService.addBacherlorOkNum();
					tempView.setViewName("grade/queryGradePage");
					tempView.addObject("gradeOne",
							userInfo.getCurrentGradeInfos());
					tempView.addObject("gradeTwo", userInfo.getSemesterGrades());
					tempView.addObject("gradeThree",
							userInfo.getFailGradeInfo());
					tempView.addObject("userinfo", userInfo.getUserDesc());
				}
				return tempView;
			}
			return tempView;

		} catch (NoHttpResponseException nhe) {
			nhe.printStackTrace();
			tempView.addObject("status", "fail");
			tempView.addObject("info", "学校成绩查询系统无法连接！");
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
			return tempView;
		} catch (Exception e) {
			e.printStackTrace();
			tempView.setViewName("grade/queryGrade");
			tempView.addObject("status", "fail");
			tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
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
		}

	}

	@RequestMapping(value = "queryGrade", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView queryGradeAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 首次登陆
		ModelAndView tempView = new ModelAndView("grade/queryGradePage");

		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Cookie userCookie = new Cookie("usernameGrade", URLEncoder.encode(
					QuerySecret.addSecret(username), "utf-8"));
			Cookie passCookie = new Cookie("passwordGrade", URLEncoder.encode(
					QuerySecret.addSecret(password), "utf-8"));
			UserInfo userInfo = Main.test(username, password);
			if (userInfo == null) {
				gradeStatisticsService.addBacherlorNum();
				tempView.setViewName("grade/queryGrade");
				tempView.addObject("status", "fail");
				tempView.addObject("info", "用户名或密码错误！");
			} else {
				gradeStatisticsService.addBacherlorOkNum();
				// 标志时间
				Date now = new Date();
				Cookie tempCookie = new Cookie("gradeTest", String.valueOf(now
						.getTime()));
				tempCookie.setMaxAge(3600);
				response.addCookie(tempCookie);
				tempView.addObject("hours", 0);
				tempView.addObject("minutes", 1);
				// 本科研究生区分标志
				tempCookie = new Cookie("level", "bachelor");
				tempCookie.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(tempCookie);

				// 用户名密码写入cookie
				userCookie.setMaxAge(Integer.MAX_VALUE);
				passCookie.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(userCookie);
				response.addCookie(passCookie);
				tempView.addObject("userinfo", userInfo.getUserDesc());
				tempView.addObject("gradeOne", userInfo.getCurrentGradeInfos());
				tempView.addObject("gradeTwo", userInfo.getSemesterGrades());
				tempView.addObject("gradeThree", userInfo.getFailGradeInfo());
				// 序列化
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						byteArrayOutputStream);
				objectOutputStream.writeObject(userInfo);
				String serStr = byteArrayOutputStream.toString("ISO-8859-1");
				serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
				objectOutputStream.close();
				byteArrayOutputStream.close();
				// 写入数据库
				GradeTemp gradeTemp = new GradeTemp();
				gradeTemp.setContent(serStr);
				gradeTemp.setUsername(username);
				gradeTempService.addGradeTemp(gradeTemp);
			}
			return tempView;

		} catch (NoHttpResponseException nhe) {
			tempView.addObject("status", "fail");
			tempView.addObject("info", "学校成绩查询系统无法连接！");
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
			return tempView;
		} catch (Exception e) {
			tempView.setViewName("grade/queryGrade");
			tempView.addObject("status", "fail");
			tempView.addObject("info", "抱歉，请稍后重试，或直接通过微信与小蘑菇联系。");
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
		}
	}

	@RequestMapping(value = "queryGradeOut", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView queryGradeOut(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView tempView = new ModelAndView("grade/queryGrade");
		// 清cookie
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
	}
}
