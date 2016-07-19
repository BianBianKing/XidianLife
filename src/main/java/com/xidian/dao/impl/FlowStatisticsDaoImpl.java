package com.xidian.dao.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.FlowStatisticsDao;
import com.xidian.forms.FlowStatistics;

@Repository("flowStatisticsDaoImpl")
public class FlowStatisticsDaoImpl implements FlowStatisticsDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addNum() {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date(System.currentTimeMillis());
		Query query = session.createQuery("from FlowStatistics flowstatistics where date = :date").setDate("date", date);
		List<FlowStatistics> list = query.list();
		if(list.isEmpty()){
			FlowStatistics gs = new FlowStatistics(1,0,date);
			session.save(gs);
		}
		else{
			FlowStatistics gs = list.get(0);
			gs.setNum(gs.getNum() + 1);
			session.update(gs);
		}
	}

	@Override
	public void addOkNum() {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date(System.currentTimeMillis());
		Query query = session.createQuery("from FlowStatistics flowstatistics where date = :date").setDate("date", date);
		List<FlowStatistics> list = query.list();
		if(list.isEmpty()){
			FlowStatistics gs = new FlowStatistics(1,1,date);
			session.save(gs);
		}
		else{
			FlowStatistics gs = list.get(0);
			gs.setNum(gs.getNum() + 1);
			gs.setOkNum(gs.getOkNum() + 1);
			session.update(gs);
		}
	}

	@Override
	public List<FlowStatistics> getFlowStatistics() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from FlowStatistics flowstatistics");
		return query.list();
	}

}
