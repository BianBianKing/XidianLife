package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.RunningDao;
import com.xidian.forms.RunningTemp;
import com.xidian.forms.User;

@Repository("runningDaoImpl")
public class RunningDaoImpl implements RunningDao{
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addRunningTemp(RunningTemp runningTemp) {
		Session session=sessionFactory.getCurrentSession();
		session.save(runningTemp);
	}

	@Override
	public RunningTemp getRunningTempByUser(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from RunningTemp runningtemp where runningtemp.username=:username").setParameter("username", username);
		List<RunningTemp> runningTemp = query.list();
		if(runningTemp.isEmpty()) {
			return null;
		} else {
			return runningTemp.get(0); 
		}
	}

	@Override
	public void updateRunningTempById(String content, Long id) {
		Session session=sessionFactory.getCurrentSession();
		RunningTemp temp = (RunningTemp)session.get(RunningTemp.class,id);
		temp.setContent(content);
		session.update(temp);
	}

}
