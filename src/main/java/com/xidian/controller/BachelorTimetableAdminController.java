package com.xidian.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xidian.forms.Classtable;
import com.xidian.forms.College;
import com.xidian.forms.Semester;
import com.xidian.forms.SemesterWeekNum;
import com.xidian.forms.Timetable;
import com.xidian.json.AdminJson;
import com.xidian.service.api.BachelorTimetableService;
import com.xidian.service.api.SemesterWeekNumService;

@Controller
@RequestMapping("/admin")
public class BachelorTimetableAdminController {
	@Resource(name="bachelorTimetableServiceImpl")
	BachelorTimetableService bachelorTimetableService;
	@Resource(name="semesterWeekNumServiceImpl")
	SemesterWeekNumService semesterWeekNumService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("semesterList.htm")
	public String semesterList(Model model){
		List<Semester> semesterList = bachelorTimetableService.getSemesterList();
		model.addAttribute(semesterList);
		return "bachelortimetableAdmin/semesterList";
	}
	@RequestMapping("collegeList.htm")
	public String collegeList(Model model){
		List<College> collegeList = bachelorTimetableService.getCollegeList();
		model.addAttribute(collegeList);
		return "bachelortimetableAdmin/collegeList";
	}
	@RequestMapping("addSemester.htm")
	public String addSemester(){
		return "bachelortimetableAdmin/addSemester";
	}
	@RequestMapping("editSemester.htm")
	public String editSemester(int semester_id, Model model){
		Semester semester = bachelorTimetableService.getSemesterById(semester_id);
		model.addAttribute("semester_id", semester_id);
		model.addAttribute("semester_name", semester.getSemester_name());
		return "bachelortimetableAdmin/editSemester";
	}
	@RequestMapping("addCollege.htm")
	public String addCollege(){
		return "bachelortimetableAdmin/addCollege";
	}
	@RequestMapping("editCollege.htm")
	public String editCollege(int college_id, Model model){
		College college = bachelorTimetableService.getCollgeById(college_id);
		model.addAttribute("college_id", college_id);
		model.addAttribute("college_name", college.getCollege_name());
		return "bachelortimetableAdmin/editCollege";
	}
	@RequestMapping("addSemester.action")
	@ResponseBody
	public AdminJson addSemesterAction(String content) throws JSONException{
	    JSONObject contentJsonObject = new JSONObject(content);
	    AdminJson rj;
	    try {
	    	if(bachelorTimetableService.addSemester(contentJsonObject.getString("semester_name")))
	    		rj = new AdminJson("success","添加学期成功","semesterList.htm");
	    	else
	    		rj = new AdminJson("fail","学期名已经存在","semesterList.htm");
	    } catch (Exception e) {
	    	rj = new AdminJson("fail","添加学期异常","semesterList.htm");
	    	e.printStackTrace();
	    }
	    return rj;
	}
	@RequestMapping("editSemester.action")
	@ResponseBody
	public AdminJson editSemesterAction(int semester_id, String content) throws JSONException{
	    JSONObject contentJsonObject = new JSONObject(content);
	    AdminJson rj;
	    try {
	    	if(bachelorTimetableService.updateSemester(semester_id, contentJsonObject.getString("semester_name")))
	    		rj = new AdminJson("success","修改学期成功","semesterList.htm");
	    	else
	    		rj = new AdminJson("fail","学期id不存在","semesterList.htm");
	    } catch (Exception e) {
	    	rj = new AdminJson("fail","修改学期异常","semesterList.htm");
	    	e.printStackTrace();
	    }
	    return rj;
	}
	@RequestMapping("addCollege.action")
	@ResponseBody
	public AdminJson addCollegeAction(String content) throws JSONException{
	    JSONObject contentJsonObject = new JSONObject(content);
	    AdminJson rj;
	    try {
	    	if(bachelorTimetableService.addCollege(contentJsonObject.getString("college_name")))
	    		rj = new AdminJson("success","添加学院成功","collegeList.htm");
	    	else
	    		rj = new AdminJson("fail","学院名已经存在","collegeList.htm");
	    } catch (Exception e) {
	    	rj = new AdminJson("fail","添加学院异常","collegeList.htm");
	    	e.printStackTrace();
	    }
	    return rj;
	}
	@RequestMapping("editCollege.action")
	@ResponseBody
	public AdminJson editCollegeAction(int college_id, String content) throws JSONException{
	    JSONObject contentJsonObject = new JSONObject(content);
	    AdminJson rj;
	    try {
	    	if(bachelorTimetableService.updateCollege(college_id, contentJsonObject.getString("college_name")))
	    		rj = new AdminJson("success","修改学院成功","collegeList.htm");
	    	else
	    		rj = new AdminJson("fail","学院id不存在","collegeList.htm");
	    } catch (Exception e) {
	    	rj = new AdminJson("fail","修改学院异常","collegeList.htm");
	    	e.printStackTrace();
	    }
	    return rj;
	}
	@RequestMapping("classtableList.htm")
	public String classtableList(Model model, int college_id){
		College c = bachelorTimetableService.getCollgeById(college_id);
		List<Classtable> classtableList = bachelorTimetableService.getClasstableByCollegeId(college_id);
		model.addAttribute(classtableList);
		model.addAttribute("college_name", c.getCollege_name());
		model.addAttribute("college_id",college_id);
		return "bachelortimetableAdmin/classtableList";
	}
	@RequestMapping("addClasstable.htm")
	public String addClasstable(Integer college_id, Model model){
		College college = bachelorTimetableService.getCollgeById(college_id);
		model.addAttribute("college",college);
		//model.addAttribute("college_id", college_id);
		return "bachelortimetableAdmin/addClasstable";
	}
	@RequestMapping("editClasstable.htm")
	public String editClasstable(int class_id, Model model){
		List<College> collegeList = bachelorTimetableService.getCollegeList();
		model.addAttribute(collegeList);
		Classtable classtable = bachelorTimetableService.getClasstableById(class_id);
		model.addAttribute("class_id", class_id);
		model.addAttribute("class_name", classtable.getClass_name());
		model.addAttribute("college_id", classtable.getCollege().getId());
		return "bachelortimetableAdmin/editClasstable";
	}
	@RequestMapping("editClasstable.action")
	public AdminJson editClasstableAction(int class_id, String content) throws JSONException{
		JSONObject contentJsonObject = new JSONObject(content);
		AdminJson rj;
		try {
			int college_id = bachelorTimetableService.getClasstableById(class_id).getCollege().getId();
			if(bachelorTimetableService.updateClasstable(class_id,  contentJsonObject.getString("class_name")))
				rj = new AdminJson("success", "修改班级成功", "classtableList.htm?college_id" + college_id);
			else
				rj = new AdminJson("fail", "班级id不存在", "classtableList.htm?college_id" + college_id);
		} catch (Exception e) {
			rj = new AdminJson("fail", "修改班级异常", "collegeList.htm");
			e.printStackTrace();
		}
		return rj;
	}
	@RequestMapping("addClasstable.action")
	public AdminJson addClasstableAction(String content) throws JSONException{
		JSONObject contentJsonObject = new JSONObject(content);
		AdminJson rj;
		try {
			int college_id = Integer.parseInt(contentJsonObject.getString("college_id"));
			if(bachelorTimetableService.addClasstable(college_id, contentJsonObject.getString("class_name")))
				rj = new AdminJson("success", "添加班级成功", "classtableList.htm?college_id" + college_id);
			else
				rj = new AdminJson("fail", "改班级已经存在", "classtableList.htm?college_id" + college_id);
		} catch (Exception e) {
			rj = new AdminJson("fail", "添加班级异常", "collegeList.htm");
			e.printStackTrace();
		}
		return rj;
	}
	@RequestMapping("timetableList.htm")
	public String timetableList(@RequestParam(required=false)Integer semester_id, @RequestParam(required=false)Integer college_id, @RequestParam(required=false)Integer class_id, @RequestParam(required=false) Integer page, Model model){
		//System.out.println(semester_id+" "+college_id);
		List<Timetable> timetableList = null;
		int level = 0;
		long total = 0;
		int pageSize = 15;
		if(page == null)
			page = 1;
		if(semester_id != null){
			if(college_id != null){
				if(class_id != null){
					level = 3;
					timetableList = new ArrayList<Timetable>();
					timetableList.add(bachelorTimetableService.getTimetableBySemesterIdAndClasstableId(semester_id, class_id));
					model.addAttribute("class_id", class_id);
					model.addAttribute("college_id", college_id);
					model.addAttribute("semester_id", semester_id);
				}
				else{
					level = 2;
					total = bachelorTimetableService.getTimetableCountBySemesterIdAndCollegeId(semester_id, college_id);
					timetableList = bachelorTimetableService.getTimetableBySemesterIdAndCollegeIdForPage(semester_id, college_id, page, pageSize);
					List<Classtable> classtableList = bachelorTimetableService.getClasstableBySemesterIdAndCollegeId(semester_id, college_id);
					model.addAttribute(classtableList);
					//model.addAttribute("class_id", class_id);
					model.addAttribute("college_id", college_id);
					model.addAttribute("semester_id", semester_id);
				}
			}
			else{
				level = 1;
				total = bachelorTimetableService.getTimetableCountBySemesterId(semester_id);
				timetableList = bachelorTimetableService.getTimetableBySemesterIdForPage(semester_id, page, pageSize);
				List<College> collegeList = bachelorTimetableService.getCollegeListBySemesterId(semester_id);
				model.addAttribute(collegeList);
				//model.addAttribute("college_id", college_id);
				model.addAttribute("semester_id", semester_id);
			}
			
		}
		else{
			total = bachelorTimetableService.getAllTimetableCount();
			List<Semester> semesterList = bachelorTimetableService.getSemesterList();
			timetableList = bachelorTimetableService.getTimetableForPage(page, pageSize);
			model.addAttribute(semesterList);
			//model.addAttribute("semester_id", semester_id);
		}
		model.addAttribute(timetableList);
		model.addAttribute("level", level);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("total", total);
		System.out.println("total:"+total+",pageCount"+total/pageSize + 1);
		model.addAttribute("pageCount", total/pageSize + 1);
		return "bachelortimetableAdmin/timetableList";
	}
	@RequestMapping("addTimetable.htm")
	public String addTimetable(Model model){
		List<Semester> semesterList = bachelorTimetableService.getSemesterList();
		List<College> collegeList = bachelorTimetableService.getCollegeList();
		if(!collegeList.isEmpty()){
			List<Classtable> classtableList = collegeList.get(0).getClassList();
			model.addAttribute(classtableList);
			Timetable t = bachelorTimetableService.getTimetableBySemesterIdAndClasstableId(semesterList.get(0).getId(), classtableList.get(0).getId());
			if(t != null){
				model.addAttribute("timetableExistence", 1);
			}
		}
		model.addAttribute(semesterList);
		model.addAttribute(collegeList);
		return "bachelortimetableAdmin/addTimetable";
	}
	@RequestMapping("editTimetable.htm")
	public String editTimetable(int timetable_id, Model model){
		Timetable timetable = bachelorTimetableService.getTimetableById(timetable_id);
		model.addAttribute(timetable);
		return "bachelortimetableAdmin/editTimetable";
	}
	@RequestMapping("addTimetable.action")
	public String addTimetableAction(Model model, int semester_id, int college_id, int class_id, @RequestParam(value="photo") MultipartFile photo, @RequestParam(value="pdf") MultipartFile pdf){
		String info = "添加成功";
		Timetable tempTimetable =  bachelorTimetableService.getTimetableBySemesterIdAndClasstableId(semester_id, class_id);
		if(tempTimetable != null){
			List<Semester> semesterList = bachelorTimetableService.getSemesterList();
			List<Timetable> timetableList = bachelorTimetableService.getAllTimetable();
			int level = 0;
			model.addAttribute(semesterList);
			model.addAttribute(timetableList);
			model.addAttribute("level", level);
			model.addAttribute("status", "success");
			model.addAttribute("info", "由于课表已经存在，添加失败");
			/*model.addAttribute("status", "fail");
    		model.addAttribute("info", "课表已经存在");*/
    		return "bachelortimetableAdmin/timetableList";
		}
		Timetable timetable = new Timetable();
		String xiangduiDir = "/resources/timetable/";
		String curRealPath = request.getSession().getServletContext().getRealPath("/");
		if(photo.getSize() > 0){
			if(!validateImage(photo)){
				info += "<br/>图片格式不正确，未成功上传";
			}
			else{
				String imageXiangduiFileName = semester_id+"_"+college_id+"_"+class_id+".jpg";
				String imageXiangdui = xiangduiDir + imageXiangduiFileName;
				String imagePath = curRealPath+imageXiangdui;
				saveFile(imagePath, photo);
				timetable.setPhoto(imageXiangdui);
			}
		}
		if(pdf.getSize() > 0){
			if(!validatePDF(pdf)){
				info += "<br/>pdf格式不正确，未成功上传";
			}
			else{
				String pdfXiangduiFileName = semester_id+"_"+college_id+"_"+class_id+".pdf";
				String pdfXiangdui = xiangduiDir + pdfXiangduiFileName;
				String pdfPath = curRealPath + pdfXiangdui;
				saveFile(pdfPath, pdf);
				timetable.setPdf(pdfXiangdui);
			}
		}

		Semester semester = bachelorTimetableService.getSemesterById(semester_id);
		Classtable classtable = bachelorTimetableService.getClasstableById(class_id);
		College college = bachelorTimetableService.getCollgeById(college_id);
		timetable.setSemester(semester);
		timetable.setClasstable(classtable);
		bachelorTimetableService.addTimetable(timetable);
		
		List<College> collegeList = bachelorTimetableService.getCollegeListBySemesterId(semester_id);
		boolean collegeStatus = false;
		for(College c: collegeList){
			if(c.getId() == college.getId()){
				collegeStatus = true;
				break;
			}
		}
		if(!collegeStatus){
			collegeList.add(college);
			semester.setCollegeList(collegeList);
		}
		
		List<Classtable> classtableList = bachelorTimetableService.getClasstableBySemesterId(semester_id);
		boolean classtableStatus = false;
		for(Classtable ct : classtableList){
			if(ct.getId() == classtable.getId()){
				classtableStatus = true;
				break;
			}
		}
		if(!classtableStatus){
			classtableList.add(classtable);
			semester.setClassList(classtableList);
		}
		bachelorTimetableService.updateSemester(semester);
		
		/*classtable.getSemesterList().add(semester);
		bachelorTimetableService.updateClasstable(classtable);*/
		
		List<Semester> semesterList = bachelorTimetableService.getSemesterList();
		List<Timetable> timetableList = bachelorTimetableService.getAllTimetable();
		int level = 0;
		model.addAttribute(semesterList);
		model.addAttribute(timetableList);
		model.addAttribute("level", level);
		model.addAttribute("status", "success");
		model.addAttribute("info", info);
		return "bachelortimetableAdmin/timetableList";
	}
	@RequestMapping("editTimetable.action")
	public String editTimetableAction(Model model,int timetable_id, @RequestParam(value="photo") MultipartFile photo, @RequestParam(value="pdf") MultipartFile pdf){
		String info = "修改成功";
		Timetable timetable =  bachelorTimetableService.getTimetableById(timetable_id);
		String xiangduiDir = "/resources/timetable/";
		String curRealPath = request.getSession().getServletContext().getRealPath("/");
		Semester semester = timetable.getSemester();
		Classtable classtable = timetable.getClasstable();
		College college = classtable.getCollege();
		if(photo.getSize() > 0){
			if(!validateImage(photo)){
				//model.addAttribute("status", "fail");
	    		//model.addAttribute("info", "图片格式不正确");
				info += "<br/>图片格式不正确，未成功上传";
			}
			else{
				String imageXiangduiFileName = semester.getId()+"_"+college.getId()+"_"+classtable.getId()+".jpg";
				String imageXiangdui = xiangduiDir + imageXiangduiFileName;
				String imagePath = curRealPath+imageXiangdui;
				saveFile(imagePath, photo);
				timetable.setPhoto(imageXiangdui);
			}
		}
		else{
			timetable.setPdf("");
		}
		System.out.println(pdf.getSize());
		if(pdf.getSize() > 0){
			if(!validatePDF(pdf)){
				//model.addAttribute("status", "fail");
	    		//model.addAttribute("info", "pdf格式不正确");
				info += "<br/>pdf格式不正确，未成功上传";
			}
			else{
				String pdfXiangduiFileName =semester.getId()+"_"+college.getId()+"_"+classtable.getId()+".pdf";
				String pdfXiangdui = xiangduiDir + pdfXiangduiFileName;
				String pdfPath = curRealPath + pdfXiangdui;
				saveFile(pdfPath, pdf);
				timetable.setPdf(pdfXiangdui);
			}
		}
		else{
			timetable.setPdf("");
		}
		bachelorTimetableService.updateTimetable(timetable);
		
		List<Semester> semesterList = bachelorTimetableService.getSemesterList();
		List<Timetable> timetableList = bachelorTimetableService.getAllTimetable();
		int level = 0;
		model.addAttribute(semesterList);
		model.addAttribute(timetableList);
		model.addAttribute("level", level);
		model.addAttribute("status", "success");
		model.addAttribute("info", info);
		return "bachelortimetableAdmin/timetableList";
	}
	public void test(){
		Semester semester = bachelorTimetableService.getSemesterById(6);
		List<College> collegeList = bachelorTimetableService.getCollegeListBySemesterId(6);
		College college = bachelorTimetableService.getCollgeById(6);
		collegeList.add(college);
		semester.setCollegeList(collegeList);
		bachelorTimetableService.updateSemester(semester);
		
		//Classtable classtable = bachelorTimetableService.getClasstableById(19);
		//classtable.getSemesterList().add(semester);
		/*List<Classtable> classtableList = semester.getClassList();
		if(!classtableList.isEmpty())
			System.out.println(classtableList.get(0).getClass_name());*/
		//.add(classtable);
	}
	
	
	@RequestMapping("delSemester.action")
	public String delSemester(Model model, int semester_id) {
		
	    try {
	    	if(bachelorTimetableService.delSemester(semester_id)){
	    		model.addAttribute("status", "success");
	    		model.addAttribute("info", "删除成功");
	    	}
	    	else{
	    		model.addAttribute("status", "fail");
	    		model.addAttribute("info", "删除id错误");
	    	}
	    		
	    } catch (Exception e) {
	    	model.addAttribute("status", "fail");
    		model.addAttribute("info", "删除异常");
	    	e.printStackTrace();
	    }
	    List<Semester> semesterList = bachelorTimetableService.getSemesterList();
		model.addAttribute(semesterList);
	    return "bachelortimetableAdmin/semesterList";
	}
	@RequestMapping("delCollege.action")
	public String delCollege(Model model, int college_id) {
		
	    try {
	    	if(bachelorTimetableService.delCollege(college_id)){
	    		model.addAttribute("status", "success");
	    		model.addAttribute("info", "删除成功");
	    	}
	    	else{
	    		model.addAttribute("status", "fail");
	    		model.addAttribute("info", "删除id错误");
	    	}
	    		
	    } catch (Exception e) {
	    	model.addAttribute("status", "fail");
    		model.addAttribute("info", "删除异常");
	    	e.printStackTrace();
	    }
	    List<College> collegeList = bachelorTimetableService.getCollegeList();
		model.addAttribute(collegeList);
	    return "bachelortimetableAdmin/collegeList";
	}
	@RequestMapping("delClasstable.action")
	public String delClasstable(Model model, int class_id) {
		int college_id = bachelorTimetableService.getClasstableById(class_id).getCollege().getId();
	    try {
	    	if(bachelorTimetableService.delClasstable(class_id)){
	    		model.addAttribute("status", "success");
	    		model.addAttribute("info", "删除成功");
	    	}
	    	else{
	    		model.addAttribute("status", "fail");
	    		model.addAttribute("info", "删除id错误");
	    	}
	    		
	    } catch (Exception e) {
	    	model.addAttribute("status", "fail");
    		model.addAttribute("info", "删除异常");
	    	e.printStackTrace();
	    }
	    College college = bachelorTimetableService.getCollgeById(college_id);
		List<Classtable> classtableList = college.getClassList();
		model.addAttribute(classtableList);
		model.addAttribute("college_name", college.getCollege_name());
		model.addAttribute("college_id",college.getId());
		return "bachelortimetableAdmin/classtableList";
	}
	@RequestMapping("delTimetable.action")
	public String delTimetable(int timetable_id, @RequestParam(required=false)Integer semester_id, @RequestParam(required=false)Integer college_id, @RequestParam(required=false)Integer class_id,Model model){
		try {
			Timetable timetable = bachelorTimetableService.getTimetableById(timetable_id);
			if(timetable != null){
				//删除课表时，解除学年与班级之间的联系
				Semester semester = timetable.getSemester();
				Classtable classtable = timetable.getClasstable();
				List<Classtable> semesterClasstableList = bachelorTimetableService.getClasstableBySemesterId(semester.getId());
				for(Classtable ct : semesterClasstableList){
					if(ct.getId() == classtable.getId()){
						semesterClasstableList.remove(ct);
						break;
					}
				}
				semester.setClassList(semesterClasstableList);
				bachelorTimetableService.updateSemester(semester);
				//删除课表
				bachelorTimetableService.delTimetable(timetable);
				model.addAttribute("status", "success");
	    		model.addAttribute("info", "删除成功");
			}
	    	else{
	    		model.addAttribute("status", "fail");
	    		model.addAttribute("info", "删除id错误");
	    	}
	    		
	    } catch (Exception e) {
	    	model.addAttribute("status", "fail");
    		model.addAttribute("info", "删除异常");
	    	e.printStackTrace();
	    }
		
		List<Timetable> timetableList = null;
		int level = 0;
		if(semester_id != null){
			if(college_id != null){
				if(class_id != null){
					level = 3;
					timetableList = new ArrayList<Timetable>();
					timetableList.add(bachelorTimetableService.getTimetableBySemesterIdAndClasstableId(semester_id, class_id));
					model.addAttribute("class_id", class_id);
					model.addAttribute("college_id", college_id);
					model.addAttribute("semester_id", semester_id);
				}
				else{
					level = 2;
					timetableList = bachelorTimetableService.getTimetableBySemesterIdAndCollegeId(semester_id, college_id);
					List<Classtable> classtableList = bachelorTimetableService.getClasstableBySemesterIdAndCollegeId(semester_id, college_id);
					model.addAttribute(classtableList);
					model.addAttribute("college_id", college_id);
					model.addAttribute("semester_id", semester_id);
				}
			}
			else{
				level = 1;
				timetableList = bachelorTimetableService.getTimetableBySemesterId(semester_id);
				List<College> collegeList = bachelorTimetableService.getCollegeListBySemesterId(semester_id);
				model.addAttribute(collegeList);
				model.addAttribute("semester_id", semester_id);
			}
			
		}
		else{
			List<Semester> semesterList = bachelorTimetableService.getSemesterList();
			timetableList = bachelorTimetableService.getAllTimetable();
			model.addAttribute(semesterList);
		}
		model.addAttribute(timetableList);
		model.addAttribute("level", level);
		return "bachelortimetableAdmin/timetableList";
	}
	@RequestMapping("checkTimetableExistence.action")
	@ResponseBody
	public AdminJson checkTimetableExistence(int semester_id, int classtable_id){
		Timetable timetable = bachelorTimetableService.getTimetableBySemesterIdAndClasstableId(semester_id, classtable_id);
		if(timetable != null){
			AdminJson aj = new AdminJson("1001");
			return aj;
		}
		else{
			AdminJson aj = new AdminJson("1000");
			return aj;
		}
	}
	@RequestMapping("setSemesterWeekNum.htm")
	public String setSemesterWeekNum(Model model){
		SemesterWeekNum semesterWeekNum = semesterWeekNumService.getSemesterWeekNum();
		if(semesterWeekNum == null){
			model.addAttribute("semesterWeekNum", "当前未设置");
		}
		else
			model.addAttribute("semesterWeekNum", semesterWeekNum.getWeeknum());
		return "bachelortimetableAdmin/setSemesterWeekNum";
	}
	@RequestMapping("setSemesterWeekNum.action")
	@ResponseBody
	public AdminJson setSemesterWeekNumAction(String content){
		JSONObject contentJsonObject;
		try {
			contentJsonObject = new JSONObject(content);
			int num = contentJsonObject.getInt("semesterWeekNum");
			SemesterWeekNum semesterWeekNum = new SemesterWeekNum(num);
			semesterWeekNumService.updateSemesterWeekNum(semesterWeekNum);
		} catch (JSONException e) {
			e.printStackTrace();
			return new AdminJson("fail","设置失败：请输入数字","setSemesterWeekNum.htm");
		}
		
		return new AdminJson("success","设置教学周成功","setSemesterWeekNum.htm");
	}
	
	
	private boolean validateImage(MultipartFile image){
		//System.out.println(image.getContentType());
		if(!image.getContentType().equals("image/jpeg") && !image.getContentType().equals("image/png") && !image.getContentType().equals("image/gif") && !image.getContentType().equals("image/bmp")){//问题
			return false;
		}
		return true;
	}
	private boolean validatePDF(MultipartFile pdf){
		System.out.println(pdf.getContentType());
		if(!pdf.getContentType().equals("application/pdf") ){
			return false;
		}
		return true;
	}
	private boolean saveFile(String filename, MultipartFile image){
		try{
			File file = new File(filename);
			FileUtils.writeByteArrayToFile(file, image.getBytes());
		}
		catch(IOException e){
			return false;
		}
		return true;
	}
	
}
