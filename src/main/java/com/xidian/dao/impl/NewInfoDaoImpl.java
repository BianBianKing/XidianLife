package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.NewInfoDao;
import com.xidian.forms.NewInfo;

@Repository("newInfoDaoImpl")
public class NewInfoDaoImpl implements NewInfoDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addNewInfo(NewInfo newInfo) {
		Session session=sessionFactory.getCurrentSession();
		session.save(newInfo);
	}

	@Override
	public void deleteNewInfo(Long id) {
		Session session=sessionFactory.getCurrentSession();
		NewInfo newInfo = (NewInfo)session.get(NewInfo.class,id);
		session.delete(newInfo);
	}

	@Override
	public List<NewInfo> getNewInfo() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from NewInfo newinfo order by newinfo.date desc");
		List<NewInfo> newInfoList=query.list();
		return newInfoList;
	}

	@Override
	public NewInfo getNewInfoById(Long id) {
		Session session=sessionFactory.getCurrentSession();
		NewInfo newInfo = (NewInfo)session.get(NewInfo.class,id);
		return newInfo;
	}

	@Override
	public void updateNewInfo(Long id, NewInfo newInfo) {
		Session session=sessionFactory.getCurrentSession();
		NewInfo temp = (NewInfo)session.get(NewInfo.class,id);
		temp.setContent(newInfo.getContent());
		temp.setDate(newInfo.getDate());
		temp.setTitle(newInfo.getTitle());
		temp.setType(newInfo.getType());
		session.update(temp);
	}

	@Override
	public List<NewInfo> getNewInfoByType(String type) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from NewInfo newinfo where newinfo.type=:type order by newinfo.date desc").setParameter("type", type);
		List<NewInfo> newInfoList=query.list();
		return newInfoList;
	}
}
