package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.SchoolBusDao;
import com.xidian.forms.SchoolBus;
import com.xidian.forms.User;

@Repository("schoolBusDaoImpl")
public class SchoolBusDaoImpl implements SchoolBusDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addSchoolBus(SchoolBus schoolBus) {
		Session session=sessionFactory.getCurrentSession();
		session.save(schoolBus);
	}

	@Override
	public void deleteSchoolBus(Long id) {
		Session session=sessionFactory.getCurrentSession();
		SchoolBus schoolBus = (SchoolBus)session.get(SchoolBus.class,id);
		session.delete(schoolBus);
	}

	@Override
	public List<SchoolBus> getSchoolBus() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from SchoolBus schoolBus order by schoolBus.date desc");
		List<SchoolBus> schoolBusList=query.list();
		return schoolBusList;
	}

	@Override
	public SchoolBus getSchoolBusById(Long id) {
		Session session=sessionFactory.getCurrentSession();
		SchoolBus schoolBus = (SchoolBus)session.get(SchoolBus.class,id);
		return schoolBus;
	}

	@Override
	public void updateSchoolBus(Long id, SchoolBus schoolBus) {
		Session session=sessionFactory.getCurrentSession();
		SchoolBus temp = (SchoolBus)session.get(SchoolBus.class,id);
		temp.setContent(schoolBus.getContent());
		temp.setDate(schoolBus.getDate());
		temp.setTitle(schoolBus.getTitle());
		temp.setNow(schoolBus.getNow());
		session.update(temp);
	}

	@Override
	public SchoolBus getNowSchoolBus() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from SchoolBus schoolBus where schoolBus.now = 1");
		List<SchoolBus> schoolBusList=query.list();
		if (schoolBusList.size() != 0) {
			return schoolBusList.get(0);
		} else {
			return null;
		}
		
	}

	@Override
	public void setNowSchoolBus(Long id) {
		Session session=sessionFactory.getCurrentSession();
		SchoolBus temp = (SchoolBus)session.get(SchoolBus.class,id);
		temp.setNow(1);
		session.update(temp);
	}

}
