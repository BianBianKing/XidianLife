package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.CoursesDao;
import com.xidian.forms.Courses;

@Repository("coursesDaoImpl")
public class CoursesDaoImpl implements CoursesDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addCourses(Courses courses) {
		Session session=sessionFactory.getCurrentSession();
		session.save(courses);
	}

	@Override
	public void deleteCourses(Long id) {
		Session session=sessionFactory.getCurrentSession();
		Courses courses = (Courses)session.get(Courses.class,id);
		session.delete(courses);
	}

	@Override
	public List<Courses> getCourses() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Courses courses  order by courses.date desc");
		List<Courses> coursesList=query.list();
		return coursesList;
	}

	@Override
	public Courses getCoursesById(Long id) {
		Session session=sessionFactory.getCurrentSession();
		Courses courses = (Courses)session.get(Courses.class,id);
		return courses;
	}

	@Override
	public void updateCourses(Long id, Courses courses) {
		Session session=sessionFactory.getCurrentSession();
		Courses temp = (Courses)session.get(Courses.class,id);
		temp.setContent(courses.getContent());
		temp.setDate(courses.getDate());
		temp.setTitle(courses.getTitle());
		session.update(temp);
	}

}
