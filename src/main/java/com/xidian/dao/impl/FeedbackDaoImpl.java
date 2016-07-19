package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.FeedbackDao;
import com.xidian.forms.Competition;
import com.xidian.forms.Feedback;
@Repository("feedbackDaoImpl")
public class FeedbackDaoImpl implements FeedbackDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addFeedback(Feedback feedback) {
		Session session=sessionFactory.getCurrentSession();
		session.save(feedback);
	}

	@Override
	public void deleteFeedbackById(Long id) {
		Session session=sessionFactory.getCurrentSession();
		Feedback feedback = (Feedback)session.get(Feedback.class,id);
		session.delete(feedback);		
	}

	@Override
	public Feedback getFeedbackById(Long id) {
		Session session=sessionFactory.getCurrentSession();
		Feedback feedback = (Feedback)session.get(Feedback.class,id);
		return feedback;
	}

	@Override
	public List<Feedback> getFeedbacks() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Feedback feedback  order by feedback.date desc");
		List<Feedback> feedbackList=query.list();
		return feedbackList;
	}
}
