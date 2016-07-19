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

import com.xidian.forms.Courses;
import com.xidian.forms.SchoolBus;
import com.xidian.service.api.SchoolBusService;

@Controller
@RequestMapping("/admin")
public class SchoolBusController {
	@Resource(name="schoolBusServiceImpl")
	SchoolBusService schoolBusService;
	
	@RequestMapping(value="addBus.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView testReturnUser(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("bus/addBus");
	}
	
	@RequestMapping(value="addBus.action",method={RequestMethod.POST,RequestMethod.GET})
	public void addCoursesForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String content = request.getParameter("content");
	    JSONObject contentJsonObject = new JSONObject(content);
	    JSONObject ret=new JSONObject();
	    try {
	    	schoolBusService.addSchoolBus(contentJsonObject.getString("title"), contentJsonObject.getString("content"));
	    	ret.put("status", "success");
	    	ret.put("info", "添加校车时间成功！");
	    	ret.put("url", "busList.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "添加校车时间失败!");
	    	e.printStackTrace();
	    }
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="busList.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView busList(HttpServletRequest request,HttpServletResponse response) {
		List<SchoolBus> tempList = schoolBusService.getSchoolBus();
		ModelAndView tempView = new ModelAndView("bus/busList");
		tempView.addObject("busList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="deleteBus.action",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView deleteBus(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("bus/busList");
	    
	    try {
	    	schoolBusService.deleteSchoolBus(id);
	    	tempView.addObject("status", "success");
	    	tempView.addObject("info", "删除成功！");
	    } catch (Exception e) {
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "删除失败！");
	    	e.printStackTrace();
	    }
	    
	    List<SchoolBus> tempList = schoolBusService.getSchoolBus();
	    tempView.addObject("busList", tempList);
	    return tempView;
	}
	
	
	@RequestMapping(value="modifBus.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView modifBus(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("bus/modifBus");
	    
	    try {
	    	SchoolBus tempBus = schoolBusService.getSchoolBusById(id);
	    	tempView.addObject("title", tempBus.getTitle());
	    	tempView.addObject("content", tempBus.getContent());
	    	tempView.addObject("id", id);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return tempView;
	}
	@RequestMapping(value="modifBus.action",method={RequestMethod.POST,RequestMethod.GET})
	public void modifCourseToForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    String content = request.getParameter("content");
	    JSONObject contentJsonObject = new JSONObject(content);
	    SchoolBus tempBus = new SchoolBus();
	    tempBus.setContent(contentJsonObject.getString("content"));
	    tempBus.setTitle(contentJsonObject.getString("title"));
	    tempBus.setDate(new Date());
	    JSONObject ret=new JSONObject();
	    try {
	    	schoolBusService.updateSchoolBus(id, tempBus);
	    	ret.put("status", "success");
	    	ret.put("info", "修改成功！");
	    	ret.put("url", "busList.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "修改失败!");
	    	e.printStackTrace();
	    }
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="detailBus.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView detailCourses(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("bus/detailBus");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		tempView.addObject("bus", schoolBusService.getSchoolBusById(id));
		return tempView;
	}
	@RequestMapping(value="setBus.action",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView setBus(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("bus/detailBus");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		try {
			SchoolBus nowBus = schoolBusService.getNowSchoolBus();
			if (nowBus != null) {
				nowBus.setNow(0);
				schoolBusService.updateSchoolBus(nowBus.getId(), nowBus);
			}
			schoolBusService.setNowSchoolBus(id);
			
			tempView.addObject("status", "success");
			tempView.addObject("info", "设置成功！");
		} catch (Exception e) {
			e.printStackTrace();
			tempView.addObject("status", "success");
			tempView.addObject("info", "设置失败！");
		}
		tempView.addObject("bus", schoolBusService.getSchoolBusById(id));
		return tempView;
	}
}
