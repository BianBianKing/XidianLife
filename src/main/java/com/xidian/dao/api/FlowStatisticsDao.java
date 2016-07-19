package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.FlowStatistics;


public interface FlowStatisticsDao {
	void addNum();
	void addOkNum();
	List<FlowStatistics> getFlowStatistics();
}
