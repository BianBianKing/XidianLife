package com.xidian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.RankerDao;
import com.xidian.forms.Ranker;
import com.xidian.service.api.RankerService;

@Service("rankerServiceImpl")
@Transactional
public class RankerServiceImpl implements RankerService {

	@Resource(name="rankerDaoImpl")
	RankerDao rankerDao;
	@Override
	public void addRanker(Ranker ranker) {
		// TODO Auto-generated method stub
		rankerDao.addRanker(ranker);
	}

	@Override
	public void deleteRanker(Ranker ranker) {
		// TODO Auto-generated method stub
		rankerDao.deleteRanker(ranker);
	}

	@Override
	public void updateRanker(Ranker ranker) {
		// TODO Auto-generated method stub
		rankerDao.updateRanker(ranker);
	}

	@Override
	public List<Ranker> getRanker() {
		// TODO Auto-generated method stub
		return rankerDao.getRanker();
	}

	@Override
	public Ranker getRankerById(Integer uuid) {
		// TODO Auto-generated method stub
		return rankerDao.getRankerById(uuid);
	}

	@Override
	public List<Ranker> getRankerDiy(Integer limit) {
		// TODO Auto-generated method stub
		return rankerDao.getRankerDiy(limit);
	}

	@Override
	public List<Ranker> getRankerList(Integer matchs) {
		// TODO Auto-generated method stub
		return rankerDao.getRankerList(matchs);
	}

}
