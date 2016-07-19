package com.xidian.dao.api;

import com.xidian.forms.GradeTemp;

public interface GradeTempDao {
	public void addGradeTemp(GradeTemp gradeTemp);
	public GradeTemp getGradeTempByUser(String username);
	public void updateGradeTempById(String content, Long id);
}
