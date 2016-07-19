package com.xidian.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xidian.forms.Classtable;
import com.xidian.forms.College;
import com.xidian.forms.Semester;
import com.xidian.forms.Timetable;
import com.xidian.json.Json;
import com.xidian.service.api.BachelorTimetableService;
import com.xidian.util.QuerySecret;

@Controller
public class BachelorTimetableController {
	@Resource(name="bachelorTimetableServiceImpl")
	BachelorTimetableService bachelorTimetableService;
	
	@Autowired
	HttpServletRequest request;
	/**
	 * 获得所有的学年信息
	 * @return
	 */
	
	@RequestMapping("testA")
	@ResponseBody
	public Json testA(){
		return new Json("测试乱码");
	}
	
	
	@RequestMapping("getAllSemester")
	@ResponseBody
	public Json getAllSemester(){
		//StringBuilder result = new StringBuilder("");
		List<String> result = new LinkedList<String>();
		List<Semester> list = bachelorTimetableService.getSemesterList();
		for(Semester s: list){
			System.out.println(s.getSemester_name());
			result.add(s.getSemester_name());
			//result.append(s.getSemester_name());
			/*for(College c : s.getCollegeList()){
				System.out.println(c.getCollege_name());
				result += c.getCollege_name()+",";
			}*/
			//result += ".";
		}
		Json json = new Json("1000");
		json.setResult(result);
		//sj.setSemesterList(list);
		return json;
	}
	
	/**
	 * 根据学期获得学院列表
	 * @param semester_id
	 * @return
	 */
	@RequestMapping("getCollegeListBySemesterId")
	@ResponseBody
	public Json getCollegeListBySemesterId(int semester_id){
		List<String> result = new LinkedList<String>();
		List<String> ids = new LinkedList<String>();
		for(College c : bachelorTimetableService.getCollegeListBySemesterId(semester_id)){
			result.add(c.getCollege_name());
			ids.add(Integer.toString(c.getId()));
		}
			//System.out.println(c.getCollege_name());
		Json json = new Json("1000");
		json.setResult(result);
		json.setIds(ids);
		return json;
	}
	
	/**
	 * 根据学期和学院获得所有的班级
	 * @param semester_id
	 * @param college_id
	 * @return
	 */
	@RequestMapping("getClasstableBySemesterIdAndCollegeId")
	@ResponseBody
	public Json getClasstableBySemesterIdAndCollegeId(int semester_id, int college_id){
		List<String> result = new LinkedList<String>();
		List<String> ids = new LinkedList<String>();
		for(Classtable c : bachelorTimetableService.getClasstableBySemesterIdAndCollegeId(semester_id, college_id)){
			//System.out.println(c.getClass_name());
			result.add(c.getClass_name());
			ids.add(Integer.toString(c.getId()));
		}
		Json json = new Json("1000");
		json.setResult(result);
		json.setIds(ids);
		return json;
	}
	/**
	 * 根据学院获得所有的班级
	 * @param semester_id
	 * @param college_id
	 * @return
	 */
	@RequestMapping("getClasstableByCollegeId")
	@ResponseBody
	public Json getClasstableByCollegeId(int college_id){
		List<String> result = new LinkedList<String>();
		List<String> ids = new LinkedList<String>();
		for(Classtable c : bachelorTimetableService.getClasstableByCollegeId(college_id)){
			//System.out.println(c.getClass_name());
			result.add(c.getClass_name());
			ids.add(Integer.toString(c.getId()));
		}
		Json json = new Json("1000");
		json.setResult(result);
		json.setIds(ids);
		return json;
	}
	@RequestMapping("queryBachelorTimetable.htm")
	public String queryBachelorTimetable(Model model){
		Cookie[] cookies = request.getCookies();
		Map<String, String> timetableInfo = new HashMap<String, String>(){
			{
				put("semester", null);
				put("class", null);
			}
		};
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if(timetableInfo.containsKey(cookie.getName()))
					timetableInfo.put(cookie.getName(), cookie.getValue());
					
			}
		}
		System.out.println(timetableInfo);
		String semester_id = timetableInfo.get("semester");
		String class_id = timetableInfo.get("class");
		
		if(semester_id !=null && class_id != null){
			Timetable timetable = bachelorTimetableService.getTimetableBySemesterIdAndClasstableId(Integer.parseInt(semester_id), Integer.parseInt(class_id));
			if(timetable == null){
				model.addAttribute("status", "fail");
				model.addAttribute("info", "查询的课表不存在");
				return "bachelortimetable/check";
			}
			else{
				String semester_name = timetable.getSemester().getSemester_name();
				String college_name = timetable.getClasstable().getCollege().getCollege_name();
				String class_name = timetable.getClasstable().getClass_name();
				model.addAttribute("semester_name",semester_name);
				model.addAttribute("college_name",college_name);
				model.addAttribute("class_name",class_name);
				model.addAttribute("photo", timetable.getPhoto());
				System.out.println(timetable.getPhoto());
				return "bachelortimetable/result";
			}
		}
		else{
			List<Semester> semesterlist = bachelorTimetableService.getSemesterList();
			model.addAttribute("semesterList", semesterlist);
			if(!semesterlist.isEmpty()){
				List<College> collegeList = bachelorTimetableService.getCollegeListBySemesterId(semesterlist.get(0).getId());
				model.addAttribute(collegeList);
				if(!collegeList.isEmpty()){
					List<Classtable> classtableList = collegeList.get(0).getClassList();
					model.addAttribute(classtableList);
				}
			}
			return "bachelortimetable/check";
		}
	}
	@RequestMapping("bachelorTimetableResult")
	public String bachelorTimetableResult(Model model, int semester_id, int class_id,HttpServletResponse response){
		Timetable timetable = bachelorTimetableService.getTimetableBySemesterIdAndClasstableId(semester_id, class_id);
		if(timetable == null) {
			model.addAttribute("status", "fail");
			model.addAttribute("info", "查询的课表不存在");
			return "bachelortimetable/check";
		}
		else{
			String semester_name = timetable.getSemester().getSemester_name();
			String college_name = timetable.getClasstable().getCollege().getCollege_name();
			String class_name = timetable.getClasstable().getClass_name();
			model.addAttribute("semester_name",semester_name);
			model.addAttribute("college_name",college_name);
			model.addAttribute("class_name",class_name);
			Cookie cookiesm = new Cookie("semester",String.valueOf(semester_id));
			cookiesm.setMaxAge(Integer.MAX_VALUE);
			Cookie cookiecl = new Cookie("class", String.valueOf(class_id));
			cookiecl.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookiesm);
			response.addCookie(cookiecl);
			model.addAttribute("photo", timetable.getPhoto());
			System.out.println(timetable.getPhoto());
			return "bachelortimetable/result";
		}
	}
	
	@RequestMapping("queryBachelorTimetableOut")
	public String queryBachelorTimetableOut(HttpServletResponse response) {
		Cookie cookie = new Cookie("semester", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("class", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		return "redirect:queryBachelorTimetable.htm";
	}
	
	
	/**
	 * 
	 * @param semester_id
	 * @param class_id
	 * @return
	 */
	@RequestMapping("getTimetableBySemesterIdAndClasstableId")
	@ResponseBody
	public String getTimetableBySemesterIdAndClasstableId(int semester_id, int class_id){
		Timetable t = bachelorTimetableService.getTimetableBySemesterIdAndClasstableId(semester_id, class_id);
		System.out.println(t.getId());
		return "";
	}
	
	
	@RequestMapping("getTimetableBySemesterId")
	@ResponseBody
	public String getTimetableBySemesterId(){
		List<String> result = new LinkedList<String>();
		for(Timetable t : bachelorTimetableService.getTimetableBySemesterId(1))
			System.out.println(t.getClasstable().getClass_name()+" "+t.getId());
		return "";
	}
}
