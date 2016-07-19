package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.GradeStatistics;

public interface GradeStatisticsDao {
	void addBacherlorNum();
	void addMasterNum();
	void addBacherlorOkNum();
	void addMasterOkNum();
	List<GradeStatistics> getGradeStatistics();
}
