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
import com.xidian.forms.NewInfo;
import com.xidian.service.api.NewInfoService;

@Controller
@RequestMapping("/admin")
public class CoursesController {
	@Resource(name="newInfoServiceImpl")
	NewInfoService newInfoService;
	
	@RequestMapping(value="addCourses.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView testReturnUser(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("courses/addCourses");
	}
	
	@RequestMapping(value="addCourses.action",method={RequestMethod.POST,RequestMethod.GET})
	public void addCoursesForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String content = request.getParameter("content");
	    JSONObject contentJsonObject = new JSONObject(content);
	    JSONObject ret=new JSONObject();
	    try {
	    	newInfoService.addNewInfo(contentJsonObject.getString("title"), contentJsonObject.getString("content"), "Courses");
	    	ret.put("status", "success");
	    	ret.put("info", "添加选课通知成功！");
	    	ret.put("url", "coursesList.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "添加选课通知失败!");
	    	e.printStackTrace();
	    }
	    System.out.println(ret.toString());
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="coursesList.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView coursesList(HttpServletRequest request,HttpServletResponse response) {
		List<NewInfo> tempList = newInfoService.getNewInfoByType("Courses");
		ModelAndView tempView = new ModelAndView("courses/coursesList");
		tempView.addObject("coursesList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="deleteCourse.action",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView deleteCourse(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("courses/coursesList");
	    
	    try {
	    	newInfoService.deleteNewInfo(id);
	    	tempView.addObject("status", "success");
	    	tempView.addObject("info", "删除成功！");
	    } catch (Exception e) {
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "删除失败！");
	    	e.printStackTrace();
	    }
	    
	    List<NewInfo> tempList = newInfoService.getNewInfoByType("Courses");
	    tempView.addObject("coursesList", tempList);
	    return tempView;
	}
	
	
	@RequestMapping(value="modifCourse.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView modifCourse(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("courses/modifCourse");
	    
	    try {
	    	NewInfo tempCourse = newInfoService.getNewInfoById(id);
	    	tempView.addObject("title", tempCourse.getTitle());
	    	tempView.addObject("content", tempCourse.getContent());
	    	System.out.println(tempCourse.getContent());
	    	tempView.addObject("id", id);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return tempView;
	}
	@RequestMapping(value="modifCourses.action",method={RequestMethod.POST,RequestMethod.GET})
	public void modifCourseToForm(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    String content = request.getParameter("content");
	    JSONObject contentJsonObject = new JSONObject(content);
	    NewInfo tempCourses = new NewInfo();
	    tempCourses.setContent(contentJsonObject.getString("content"));
	    tempCourses.setTitle(contentJsonObject.getString("title"));
	    tempCourses.setDate(new Date());
	    tempCourses.setType("Courses");
	    JSONObject ret=new JSONObject();
	    try {
	    	newInfoService.updateNewInfo(id, tempCourses);
	    	ret.put("status", "success");
	    	ret.put("info", "修改成功！");
	    	ret.put("url", "coursesList.htm");
	    } catch (Exception e) {
	    	ret.put("status", "fail");
	    	ret.put("info", "修改失败!");
	    	e.printStackTrace();
	    }
	    response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="detailCourses.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView detailCourses(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView tempView =  new ModelAndView("courses/detailCourses");
		String temp_id = request.getParameter("id");
		Long id = Long.parseLong(temp_id);
		tempView.addObject("courses", newInfoService.getNewInfoById(id));
		return tempView;
	}
}
