package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.Courses;

public interface CoursesDao {
	public void addCourses(Courses courses);
	public void deleteCourses(Long id);
	public List<Courses> getCourses();
	public Courses getCoursesById(Long id);
	public void updateCourses(Long id, Courses courses);
}
