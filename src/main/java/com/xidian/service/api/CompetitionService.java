package com.xidian.service.api;

import java.util.List;

import com.xidian.forms.Competition;

public interface CompetitionService {
	public void addCompetition(String title, String content);
	public void deleteCompetition(Long id);
	public List<Competition> getCompetition();
	public Competition getCompetitionById(Long id);
	public void updateCompetition(Long id, Competition competition);
}
