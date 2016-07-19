package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.Feedback;

public interface FeedbackDao {
	public void addFeedback(Feedback feedback);
	public void deleteFeedbackById(Long id);
	public Feedback getFeedbackById(Long id);
	public List<Feedback> getFeedbacks();
}
