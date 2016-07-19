package com.xidian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xidian.dao.api.GradeStatisticsDao;
import com.xidian.forms.GradeStatistics;
import com.xidian.service.api.GradeStatisticsService;

@Service("gradeStatisticsServiceImpl")
@Transactional
public class GradeStatisticsServiceImpl implements GradeStatisticsService {
	@Resource(name="gradeStatisticsDaoImpl")
	GradeStatisticsDao gradeStatisticsDao;
	
	@Override
	public void addBacherlorNum() {
		gradeStatisticsDao.addBacherlorNum();
	}

	@Override
	public void addMasterNum() {
		gradeStatisticsDao.addMasterNum();
	}

	@Override
	public void addBacherlorOkNum() {
		gradeStatisticsDao.addBacherlorOkNum();
	}

	@Override
	public void addMasterOkNum() {
		gradeStatisticsDao.addMasterOkNum();
	}

	@Override
	public List<GradeStatistics> getGradeStatistics() {
		return gradeStatisticsDao.getGradeStatistics();
	}

}
