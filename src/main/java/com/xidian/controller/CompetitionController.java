package com.xidian.controller;

import java.io.IOException;
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

import com.xidian.forms.Competition;
import com.xidian.forms.NewInfo;
import com.xidian.service.api.NewInfoService;

@Controller
@RequestMapping("/admin")
public class CompetitionController {
	@Resource(name="newInfoServiceImpl")
	NewInfoService newInfoService;
	
	@RequestMapping(value="addCompetition.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView testReturnUser(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("competition/addCompetition");
	}
	
	@RequestMapping(value="addCompetition.action",method={RequestMethod.POST,RequestMethod.GET})
	public void addCoursesForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String content = request.getParameter("content");
	    JSONObject contentJsonObject = new JSONObject(content);
	    JSONObject ret=new JSONObject();
	    try {
	    	newInfoService.addNewInfo(contentJsonObject.getString("title"), contentJsonObject.getString("content"), "Competition");
	    	ret.put("status", "success");
	    	ret.put("info", "添加竞赛公告成功！");
	    	ret.put("url", "competitionList.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "添加竞赛公告失败!");
	    	e.printStackTrace();
	    }
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="competitionList.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView competitionList(HttpServletRequest request,HttpServletResponse response) {
		List<NewInfo> tempList = newInfoService.getNewInfoByType("Competition");
		ModelAndView tempView = new ModelAndView("competition/competitionList");
		tempView.addObject("competitionList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="deleteCompetition.action",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView deleteCompetition(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("competition/competitionList");
	    
	    try {
	    	newInfoService.deleteNewInfo(id);
	    	tempView.addObject("status", "success");
	    	tempView.addObject("info", "删除成功！");
	    } catch (Exception e) {
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "删除失败！");
	    	e.printStackTrace();
	    }
	    
	    List<NewInfo> tempList = newInfoService.getNewInfoByType("Competition");
	    tempView.addObject("competitionList", tempList);
	    return tempView;
	}
	
	
	@RequestMapping(value="modifCompetition.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView modifCompetition(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("competition/modifCompetition");
	    
	    try {
	    	NewInfo tempCompetition = newInfoService.getNewInfoById(id);
	    	tempView.addObject("title", tempCompetition.getTitle());
	    	tempView.addObject("content", tempCompetition.getContent());
	    	tempView.addObject("id", id);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return tempView;
	}
	@RequestMapping(value="modifCompetition.action",method={RequestMethod.POST,RequestMethod.GET})
	public void modifCompetitionToForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    String content = request.getParameter("content");
	    JSONObject contentJsonObject = new JSONObject(content);
	    NewInfo tempCompetition = new NewInfo();
	    tempCompetition.setContent(contentJsonObject.getString("content"));
	    tempCompetition.setTitle(contentJsonObject.getString("title"));
	    tempCompetition.setDate(new Date());
	    tempCompetition.setType("Competition");
	    JSONObject ret=new JSONObject();
	    try {
	    	newInfoService.updateNewInfo(id, tempCompetition);
	    	ret.put("status", "success");
	    	ret.put("info", "修改成功！");
	    	ret.put("url", "competitionList.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "修改失败!");
	    	e.printStackTrace();
	    }
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="detailCompetition.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView detailCourses(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("competition/detailCompetition");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		tempView.addObject("competition", newInfoService.getNewInfoById(id));
		return tempView;
	}
}
