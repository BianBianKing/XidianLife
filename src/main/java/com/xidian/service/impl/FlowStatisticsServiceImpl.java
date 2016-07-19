package com.xidian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.FlowStatisticsDao;
import com.xidian.forms.FlowStatistics;
import com.xidian.service.api.FlowStatisticsService;

@Service("flowStatisticsServiceImpl")
@Transactional
public class FlowStatisticsServiceImpl implements FlowStatisticsService{
	@Resource(name="flowStatisticsDaoImpl")
	FlowStatisticsDao flowStatisticsDao;
	
	@Override
	public void addNum() {
		flowStatisticsDao.addNum();
	}

	@Override
	public void addOkNum() {
		flowStatisticsDao.addOkNum();
	}

	@Override
	public List<FlowStatistics> getFlowStatistics() {
		return flowStatisticsDao.getFlowStatistics();
	}

}
