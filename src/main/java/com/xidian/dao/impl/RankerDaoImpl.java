package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.RankerDao;
import com.xidian.forms.Ranker;

@Repository("rankerDaoImpl")
public class RankerDaoImpl implements RankerDao {

	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	@Override
	public void addRanker(Ranker ranker) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(ranker);
	}

	@Override
	public void deleteRanker(Ranker ranker) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(ranker);
	}

	@Override
	public void updateRanker(Ranker ranker) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(ranker);
	}

	@Override
	public List<Ranker> getRanker() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Ranker ranker order by ranker.uuid desc");
		List<Ranker> RL=query.list();
		return RL;
	}

	@Override
	public Ranker getRankerById(Integer uuid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Ranker ranker where ranker.uuid="+uuid);
		Ranker ranker=(Ranker) query.list().get(0);
		return ranker;
	}

	@Override
	public List<Ranker> getRankerDiy(Integer limit) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Ranker ranker order by ranker.matchs desc limit "+ limit);
		List<Ranker> RL=query.list();
		return RL;
	}

	@Override
	public List<Ranker> getRankerList(Integer matchs) {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Ranker ranker where ranker.matchs >"+ matchs +" order by ranker.countFollowers desc, ranker.matchs desc");
		List<Ranker> RL=query.list();
		return RL;
	}

}
