package com.xidian.service.api;

import java.util.List;

import com.xidian.forms.Ranker;

public interface RankerService {
	void addRanker(Ranker ranker);
	
	void deleteRanker(Ranker ranker);
	
	void updateRanker(Ranker ranker);
	
	List<Ranker> getRanker();
	
	List<Ranker> getRankerDiy(Integer limit);
	
	List<Ranker> getRankerList(Integer matchs);
	
	Ranker getRankerById(Integer uuid);
}
