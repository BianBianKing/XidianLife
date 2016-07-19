package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.GradeTempDao;
import com.xidian.forms.Courses;
import com.xidian.forms.GradeTemp;
import com.xidian.forms.User;
@Repository("gradeTempDaoImpl")
public class GradeTempDaoImpl implements GradeTempDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addGradeTemp(GradeTemp gradeTemp) {
		Session session=sessionFactory.getCurrentSession();
		session.save(gradeTemp);
	}

	@Override
	public GradeTemp getGradeTempByUser(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from GradeTemp gradeTemp where gradeTemp.username=:username").setParameter("username", username);
		List<User> gradeTemp=query.list();
		if(gradeTemp.isEmpty()) {
			return null;
		} else {
			return (GradeTemp) query.list().get(0); 
		}
	}

	@Override
	public void updateGradeTempById(String content, Long id) {
		Session session=sessionFactory.getCurrentSession();
		GradeTemp temp = (GradeTemp)session.get(GradeTemp.class,id);
		temp.setContent(content);
		session.update(temp);
	}

	

}
