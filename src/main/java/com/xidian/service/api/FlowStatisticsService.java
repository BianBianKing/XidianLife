package com.xidian.service.api;

import java.util.List;

import com.xidian.forms.FlowStatistics;

public interface FlowStatisticsService {
	void addNum();
	void addOkNum();
	List<FlowStatistics> getFlowStatistics();
}
