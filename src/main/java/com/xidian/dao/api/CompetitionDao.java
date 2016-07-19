package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.Competition;

public interface CompetitionDao {
	public void addCompetition(Competition competition);
	public void deleteCompetition(Long id);
	public List<Competition> getCompetition();
	public Competition getCompetitionById(Long id);
	public void updateCompetition(Long id, Competition competition);
}
