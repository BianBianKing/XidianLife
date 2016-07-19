package com.xidian.service.api;

import java.util.List;

import com.xidian.forms.Courses;

public interface CoursesService {
	public void addCourses(String title, String content);
	public void deleteCourses(Long id);
	public List<Courses> getCourses();
	public Courses getCoursesById(Long id);
	public void updateCourses(Long id, Courses courses);
}
