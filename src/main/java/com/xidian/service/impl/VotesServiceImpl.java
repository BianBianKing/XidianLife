package com.xidian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.VotesDao;
import com.xidian.forms.Votes;
import com.xidian.service.api.VotesService;

@Service("votesServiceImpl")
@Transactional
public class VotesServiceImpl implements VotesService {
	@Resource(name="votesDaoImpl")
	VotesDao votesDao;

	@Override
	public void addVote(Votes vote) {
		// TODO Auto-generated method stub
		votesDao.addVote(vote);
	}

	@Override
	public void deleteVote(Votes vote) {
		// TODO Auto-generated method stub
		votesDao.deleteVote(vote);
	}

	@Override
	public void updateVote(Votes vote) {
		// TODO Auto-generated method stub
		votesDao.updateVote(vote);
	}

	@Override
	public List<Votes> getVote() {
		// TODO Auto-generated method stub
		return votesDao.getVote();
	}

	@Override
	public Votes getVoteByIdentity(String identity) {
		// TODO Auto-generated method stub
		return votesDao.getVoteByIdentity(identity);
	}

}
