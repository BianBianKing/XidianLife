package com.xidian.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.SemesterWeekNumDao;
import com.xidian.forms.SemesterWeekNum;
@Repository("semesterWeekNumDaoImpl")
public class SemesterWeekNumDaoImpl implements SemesterWeekNumDao{
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void updateSemesterWeekNum(SemesterWeekNum semesterWeekNum) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from SemesterWeekNum swn");
		SemesterWeekNum old = (SemesterWeekNum)query.uniqueResult();
		if(old != null){
			old.setWeeknum(semesterWeekNum.getWeeknum());
			session.update(old);
		}
		else
			session.save(semesterWeekNum);
	}

	@Override
	public SemesterWeekNum getSemesterWeekNum() {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from SemesterWeekNum swn");
		return (SemesterWeekNum)query.uniqueResult();
	}

}
