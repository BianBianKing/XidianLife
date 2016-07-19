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
public class OthersController {
	@Resource(name="newInfoServiceImpl")
	NewInfoService newInfoService;
	
	@RequestMapping(value="addOthers.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView addOthers(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("others/addOthers");
	}
	
	@RequestMapping(value="addOthers.action",method={RequestMethod.POST,RequestMethod.GET})
	public void addOthersForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String content = request.getParameter("content");
	    JSONObject contentJsonObject = new JSONObject(content);
	    JSONObject ret=new JSONObject();
	    try {
	    	newInfoService.addNewInfo(contentJsonObject.getString("title"), contentJsonObject.getString("content"), "Others");
	    	ret.put("status", "success");
	    	ret.put("info", "添加其他公告成功！");
	    	ret.put("url", "othersList.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "添加竞赛公告失败!");
	    	e.printStackTrace();
	    }
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="othersList.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView othersList(HttpServletRequest request,HttpServletResponse response) {
		List<NewInfo> tempList = newInfoService.getNewInfoByType("Others");
		ModelAndView tempView = new ModelAndView("others/othersList");
		tempView.addObject("othersList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="deleteOthers.action",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView deleteOthers(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("others/othersList");
	    
	    try {
	    	newInfoService.deleteNewInfo(id);
	    	tempView.addObject("status", "success");
	    	tempView.addObject("info", "删除成功！");
	    } catch (Exception e) {
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "删除失败！");
	    	e.printStackTrace();
	    }
	    
	    List<NewInfo> tempList = newInfoService.getNewInfoByType("Others");
	    tempView.addObject("othersList", tempList);
	    return tempView;
	}
	
	
	@RequestMapping(value="modifOthers.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView modifOthers(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("others/modifOthers");
	    
	    try {
	    	NewInfo tempOthers = newInfoService.getNewInfoById(id);
	    	tempView.addObject("title", tempOthers.getTitle());
	    	tempView.addObject("content", tempOthers.getContent());
	    	tempView.addObject("id", id);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return tempView;
	}
	@RequestMapping(value="modifOthers.action",method={RequestMethod.POST,RequestMethod.GET})
	public void modifOthersToForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    String content = request.getParameter("content");
	    JSONObject contentJsonObject = new JSONObject(content);
	    NewInfo tempOthers = new NewInfo();
	    tempOthers.setContent(contentJsonObject.getString("content"));
	    tempOthers.setTitle(contentJsonObject.getString("title"));
	    tempOthers.setDate(new Date());
	    tempOthers.setType("Others");
	    JSONObject ret=new JSONObject();
	    try {
	    	newInfoService.updateNewInfo(id, tempOthers);
	    	ret.put("status", "success");
	    	ret.put("info", "修改成功！");
	    	ret.put("url", "othersList.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "修改失败!");
	    	e.printStackTrace();
	    }
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="detailOthers.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView detailOthers(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("others/detailOthers");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		tempView.addObject("others", newInfoService.getNewInfoById(id));
		return tempView;
	}
}
