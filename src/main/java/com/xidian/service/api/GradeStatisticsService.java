package com.xidian.service.api;

import java.util.List;

import com.xidian.forms.GradeStatistics;

public interface GradeStatisticsService {
	void addBacherlorNum();
	void addMasterNum();
	void addBacherlorOkNum();
	void addMasterOkNum();
	List<GradeStatistics> getGradeStatistics();
}
