package com.xidian.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.VotesDao;
import com.xidian.forms.Votes;

@Repository("votesDaoImpl")
public class VotesDaoImpl implements VotesDao {
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public void addVote(Votes vote) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(vote);
	}

	@Override
	public void deleteVote(Votes vote) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.delete(vote);
	}

	@Override
	public void updateVote(Votes vote) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.update(vote);
	}

	@Override
	public List<Votes> getVote() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Votes votes order by votes.uuid desc");
		List<Votes> VL=query.list();
		return VL;
	}

	@Override
	public Votes getVoteByIdentity(String identity) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Votes votes where votes.identity="+identity);
		List<Votes> V=query.list();
		if (V.size() != 0) {
			return V.get(0);
		} else {
			return null;
		}
		
	}

}
