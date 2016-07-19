package com.xidian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.CoursesDao;
import com.xidian.dao.api.FeedbackDao;
import com.xidian.forms.Feedback;
import com.xidian.service.api.FeedbackService;
@Service("feedbackServiceImpl")
@Transactional
public class FeedbackServiceImpl implements FeedbackService{
	@Resource(name="feedbackDaoImpl")
	FeedbackDao feedbackDao;
	@Override
	public void addFeedback(Feedback feedback) {
		feedbackDao.addFeedback(feedback);
	}

	@Override
	public void deleteFeedbackById(Long id) {
		feedbackDao.deleteFeedbackById(id);	
	}

	@Override
	public Feedback getFeedbackById(Long id) {
		return getFeedbackById(id);
	}

	@Override
	public List<Feedback> getFeedbacks() {
		return feedbackDao.getFeedbacks();
	}

}
