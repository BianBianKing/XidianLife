package com.xidian.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.CoursesDao;
import com.xidian.forms.Courses;
import com.xidian.service.api.CoursesService;

@Service("coursesServiceImpl")
@Transactional
public class CoursesServiceImpl implements CoursesService{
	@Resource(name="coursesDaoImpl")
	CoursesDao coursesDao;
	
	@Override
	public void addCourses(String title, String content) {
		Courses tempCourses = new Courses();
		tempCourses.setContent(content);
		tempCourses.setTitle(title);
		tempCourses.setDate(new Date());
		coursesDao.addCourses(tempCourses);
	}

	@Override
	public void deleteCourses(Long id) {
		coursesDao.deleteCourses(id);
	}

	@Override
	public List<Courses> getCourses() {
		return coursesDao.getCourses();
	}

	@Override
	public Courses getCoursesById(Long id) {
		return coursesDao.getCoursesById(id);
	}

	@Override
	public void updateCourses(Long id, Courses courses) {
		coursesDao.updateCourses(id, courses);
	}

}
