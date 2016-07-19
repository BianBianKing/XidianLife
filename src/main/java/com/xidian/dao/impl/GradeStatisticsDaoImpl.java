package com.xidian.dao.impl;


import java.sql.Date;
import java.text.SimpleDateFormat;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.GradeStatisticsDao;
import com.xidian.forms.GradeStatistics;

@Repository("gradeStatisticsDaoImpl")
public class GradeStatisticsDaoImpl implements GradeStatisticsDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addBacherlorNum() {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date(System.currentTimeMillis());
		Query query = session.createQuery("from GradeStatistics gradestatistics where date = :date").setDate("date", date);
		List<GradeStatistics> list = query.list();
		if(list.isEmpty()){
			GradeStatistics gs = new GradeStatistics(1,0,0,0,date);
			session.save(gs);
		}
		else{
			GradeStatistics gs = list.get(0);
			gs.setBachelorNum(gs.getBachelorNum() + 1);
			session.update(gs);
		}
	}

	@Override
	public void addMasterNum() {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date(System.currentTimeMillis());
		Query query = session.createQuery("from GradeStatistics gradestatistics where date = :date").setDate("date", date);
		List<GradeStatistics> list = query.list();
		if(list.isEmpty()){
			GradeStatistics gs = new GradeStatistics(0,1,0,0,date);
			session.save(gs);
		}
		else{
			GradeStatistics gs = list.get(0);
			gs.setMasterNum(gs.getMasterNum() + 1);
			session.update(gs);
		}
	}

	@Override
	public void addBacherlorOkNum() {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date(System.currentTimeMillis());
		Query query = session.createQuery("from GradeStatistics gradestatistics where date = :date").setDate("date", date);
		List<GradeStatistics> list = query.list();
		if(list.isEmpty()){
			GradeStatistics gs = new GradeStatistics(1,0,1,0,date);
			session.save(gs);
		}
		else{
			GradeStatistics gs = list.get(0);
			gs.setBachelorNum(gs.getBachelorNum() + 1);
			gs.setBachelorOkNum(gs.getBachelorOkNum() + 1);
			session.update(gs);
		}
	}

	@Override
	public void addMasterOkNum() {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date(System.currentTimeMillis());
		Query query = session.createQuery("from GradeStatistics gradestatistics where date = :date").setDate("date", date);
		List<GradeStatistics> list = query.list();
		if(list.isEmpty()){
			GradeStatistics gs = new GradeStatistics(0,1,0,1,date);
			session.save(gs);
		}
		else{
			GradeStatistics gs = list.get(0);
			gs.setMasterNum(gs.getMasterNum() + 1);
			gs.setMasterOkNum(gs.getMasterOkNum() + 1);
			session.update(gs);
		}
	}

	@Override
	public List<GradeStatistics> getGradeStatistics() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from GradeStatistics gradestatistics");
		List<GradeStatistics> list = query.list();
		return list;
	}
	
}
