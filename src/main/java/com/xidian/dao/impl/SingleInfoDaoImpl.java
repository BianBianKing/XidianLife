package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.SingleInfoDao;
import com.xidian.forms.SingleInfo;

@Repository("singleInfoDaoImpl")
public class SingleInfoDaoImpl implements SingleInfoDao{

	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public void addSingleInfo(SingleInfo singleInfo) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(singleInfo);
	}

	@Override
	public void deleteSingleInfo(SingleInfo singleInfo) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.delete(singleInfo);
	}

	@Override
	public void updateSingleInfo(SingleInfo singleInfo) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.update(singleInfo);
	}

	@Override
	public List<SingleInfo> getSingleInfo() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from SingleInfo singleInfo order by singleInfo.uuid desc");
		List<SingleInfo> SIL=query.list();
		return SIL;
	}

	@Override
	public SingleInfo getSingleInfoById(Integer uuid) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		SingleInfo SI = (SingleInfo)session.get(SingleInfo.class, uuid);
		//Query query=session.createQuery("from SingleInfo singleInfo where singleInfo.uuid="+uuid);
		//SingleInfo SI=(SingleInfo) query.list().get(0);
		return SI;
	}
}
