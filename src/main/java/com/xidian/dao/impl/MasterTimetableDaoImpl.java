package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.MasterTimetableDao;
import com.xidian.forms.MasterTimetable;

@Repository("masterTimetableDaoImpl")
public class MasterTimetableDaoImpl implements MasterTimetableDao{
	@Autowired
	SessionFactory sessionFactory;
	@Override
	public void addMasterTimetable(MasterTimetable masterTimetable) {
		Session session=sessionFactory.getCurrentSession();
		session.save(masterTimetable);
	}

	@Override
	public MasterTimetable getMasterTimetableByUser(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from MasterTimetable mastertimetable where mastertimetable.username=:username").setParameter("username", username);
		List<MasterTimetable> masterTimetable = query.list();
		if(masterTimetable.isEmpty()) {
			return null;
		} else {
			return masterTimetable.get(0); 
		}
	}

	@Override
	public void updateMasterTimetableById(String content, Long id) {
		Session session=sessionFactory.getCurrentSession();
		MasterTimetable temp = (MasterTimetable)session.get(MasterTimetable.class,id);
		temp.setContent(content);
		session.update(temp);
	}

}
