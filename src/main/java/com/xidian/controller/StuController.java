package com.xidian.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.forms.Cards;
import com.xidian.forms.Competition;
import com.xidian.forms.Courses;
import com.xidian.forms.Feedback;
import com.xidian.forms.NewInfo;
import com.xidian.forms.SchoolBus;
import com.xidian.service.api.CardsService;
import com.xidian.service.api.CompetitionService;
import com.xidian.service.api.CoursesService;
import com.xidian.service.api.FeedbackService;
import com.xidian.service.api.NewInfoService;
import com.xidian.service.api.SchoolBusService;

@Controller
public class StuController {
	@Resource(name="cardsServiceImpl")
	CardsService cardsService;
	@Resource(name="coursesServiceImpl")
	CoursesService coursesService;
	@Resource(name="schoolBusServiceImpl")
	SchoolBusService schoolBusService;
	@Resource(name="competitionServiceImpl")
	CompetitionService competitionService;
	@Resource(name="newInfoServiceImpl")
	NewInfoService newInfoService;
	@Resource(name="feedbackServiceImpl")
	FeedbackService feedbackService;
	
	@RequestMapping(value="stuCards.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuCards(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("stu/stuCards");
	}
	
	@RequestMapping(value="stuCourses.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuCourses(HttpServletRequest request,HttpServletResponse response) {
		List<Courses> tempList = coursesService.getCourses();
		ModelAndView tempView = new ModelAndView("stu/stuCourses");
		tempView.addObject("coursesList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="stuCompetition.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuCompetition(HttpServletRequest request,HttpServletResponse response) {
		List<Competition> tempList = competitionService.getCompetition();
		ModelAndView tempView = new ModelAndView("stu/stuCompetition");
		tempView.addObject("competitionList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="stuBus.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuBus(HttpServletRequest request,HttpServletResponse response) {
		List<SchoolBus> tempList = schoolBusService.getSchoolBus();
		ModelAndView tempView = new ModelAndView("stu/stuBus");
		tempView.addObject("busList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="stuAddCards.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuAddCards(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("stu/stuAddCards");
	}
	
	@RequestMapping(value="addCards",method={RequestMethod.POST,RequestMethod.GET})
	public void addCards(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String lostNum = request.getParameter("lostNum");
	    String lostName = request.getParameter("lostName");
	    String keeperName = request.getParameter("keeperName");
	    String keeperPhone = request.getParameter("keeperPhone");
	    JSONObject ret=new JSONObject();
	    try {
	    	cardsService.addCards(lostNum, lostName, keeperName, keeperPhone);
	    	ret.put("status", "success");
	    	ret.put("info", "添加信息成功！");
	    	ret.put("url", "stuCards.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "添加信息失败!");
	    	e.printStackTrace();
	    }
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="stuCardsList.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView cardsList(HttpServletRequest request,HttpServletResponse response) {
		List<Cards> tempList = cardsService.getCards();
		ModelAndView tempView = new ModelAndView("stu/stuCardsList");
		tempView.addObject("cardsList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="stuSearchCards",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuSearchCards(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String numString = request.getParameter("num");
		String nameString = request.getParameter("name");
		List<Cards> tempList;
		System.out.println("??");
		if (numString != null) {
			System.out.println("num:"+numString);
			tempList = cardsService.getCardsByNum(numString);
		} else {
			System.out.println("name:"+nameString);
			tempList = cardsService.getCardsByLostName(nameString);
		}
		ModelAndView tempView = new ModelAndView("stu/stuCardsList");
		tempView.addObject("cardsList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="stuDetailCourses.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuDetailCourses(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("stu/stuDetailCourses");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		tempView.addObject("courses", newInfoService.getNewInfoById(id));
		return tempView;
	}
	
	@RequestMapping(value="stuDetailCompetition.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuDetailCompetition(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("stu/stuDetailCompetition");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		tempView.addObject("competition", newInfoService.getNewInfoById(id));
		return tempView;
	}
	
	@RequestMapping(value="stuDetailBus.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuDetailBus(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("stu/stuDetailBus");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		tempView.addObject("bus", schoolBusService.getSchoolBusById(id));
		return tempView;
	}
	
	@RequestMapping(value="stuDetailOthers.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuDetailOthers(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("stu/stuDetailOthers");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		tempView.addObject("others", newInfoService.getNewInfoById(id));
		return tempView;
	}
	
	@RequestMapping(value="stuNewInfo.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuNewInfo(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("stu/stuNewInfo");
		List<NewInfo> tempNewInfos = newInfoService.getNewInfo();
		tempView.addObject("list", tempNewInfos);
		return tempView;
	}
	@RequestMapping(value="stuNowBus.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView stuNowBus(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("stu/stuNowBus");
		SchoolBus schoolBus = schoolBusService.getNowSchoolBus();
		tempView.addObject("bus", schoolBus);
		return tempView;
	}
	
	@RequestMapping(value="stuAddFeedback.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView addFeedback(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView = new ModelAndView("feedback/stuAddFeedback");
		return tempView;
	}
	@RequestMapping(value="addFeedback",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView addFeedbackForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String tempContent = request.getParameter("content");
	    //System.out.println(tempContent);
	    ModelAndView tempView = new ModelAndView("feedback/stuAddFeedback");
	    
	    try {
	    	Feedback tempFeedback = new Feedback();
	    	tempFeedback.setContent(tempContent);
	    	tempFeedback.setDate(new Date());
	    	feedbackService.addFeedback(tempFeedback);
	    	tempView.addObject("status", "success");
	    	tempView.addObject("info", "提交成功！");
	    } catch (Exception e) {
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "提交失败！");
	    	e.printStackTrace();
	    }
	    return tempView;
	}
}
