package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.CompetitionDao;
import com.xidian.forms.Competition;

@Repository("competitionDaoImpl")
public class CompetitionDaoImpl implements CompetitionDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addCompetition(Competition competition) {
		Session session=sessionFactory.getCurrentSession();
		session.save(competition);
	}

	@Override
	public void deleteCompetition(Long id) {
		Session session=sessionFactory.getCurrentSession();
		Competition competition = (Competition)session.get(Competition.class,id);
		session.delete(competition);
	}

	@Override
	public List<Competition> getCompetition() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Competition competition  order by competition.date desc");
		List<Competition> competitionList=query.list();
		return competitionList;
	}

	@Override
	public Competition getCompetitionById(Long id) {
		Session session=sessionFactory.getCurrentSession();
		Competition competition = (Competition)session.get(Competition.class,id);
		return competition;
	}

	@Override
	public void updateCompetition(Long id, Competition competition) {
		Session session=sessionFactory.getCurrentSession();
		Competition temp = (Competition)session.get(Competition.class,id);
		temp.setContent(competition.getContent());
		temp.setDate(competition.getDate());
		temp.setTitle(competition.getTitle());
		session.update(temp);
	}
}
