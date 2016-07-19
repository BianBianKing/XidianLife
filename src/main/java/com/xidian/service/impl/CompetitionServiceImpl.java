package com.xidian.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.CompetitionDao;
import com.xidian.forms.Competition;
import com.xidian.service.api.CompetitionService;

@Service("competitionServiceImpl")
@Transactional
public class CompetitionServiceImpl implements CompetitionService{
	@Resource(name="competitionDaoImpl")
	CompetitionDao competitionDao;

	@Override
	public void addCompetition(String title, String content) {
		Competition tempCompetition = new Competition();
		tempCompetition.setContent(content);
		tempCompetition.setTitle(title);
		tempCompetition.setDate(new Date());
		competitionDao.addCompetition(tempCompetition);
	}

	@Override
	public void deleteCompetition(Long id) {
		competitionDao.deleteCompetition(id);
	}

	@Override
	public List<Competition> getCompetition() {
		return competitionDao.getCompetition();
	}

	@Override
	public Competition getCompetitionById(Long id) {
		return competitionDao.getCompetitionById(id);
	}

	@Override
	public void updateCompetition(Long id, Competition competition) {
		competitionDao.updateCompetition(id, competition);
	}
	
}
