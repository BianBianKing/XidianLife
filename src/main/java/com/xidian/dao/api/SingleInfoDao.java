package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.SingleInfo;

public interface SingleInfoDao {

	void addSingleInfo(SingleInfo singleInfo);
	
	void deleteSingleInfo(SingleInfo singleInfo);
	
	void updateSingleInfo(SingleInfo singleInfo);
	
	List<SingleInfo> getSingleInfo();
	
	SingleInfo getSingleInfoById(Integer uuid);
	
}
