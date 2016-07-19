package com.xidian.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.forms.Feedback;
import com.xidian.forms.NewInfo;
import com.xidian.service.api.FeedbackService;

@Controller
@RequestMapping("/admin")
public class FeedbackController {
	@Resource(name="feedbackServiceImpl")
	FeedbackService feedbackService;
	
	@RequestMapping(value="feedbackList.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView feedbackList(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView = new ModelAndView("feedback/feedbackList");
		tempView.addObject("feedbackList", feedbackService.getFeedbacks());
		return tempView;
	}
	
	@RequestMapping(value="deleteFeedback.action",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView deleteFeedback(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("feedback/feedbackList");
	    
	    try {
	    	feedbackService.deleteFeedbackById(id);
	    	tempView.addObject("status", "success");
	    	tempView.addObject("info", "É¾³ý³É¹¦£¡");
	    } catch (Exception e) {
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "É¾³ýÊ§°Ü£¡");
	    	e.printStackTrace();
	    }
	    tempView.addObject("feedbackList", feedbackService.getFeedbacks());
	    return tempView;
	}
}
