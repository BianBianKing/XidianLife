package com.xidian.service.api;

import com.xidian.forms.GradeTemp;

public interface GradeTempService {
	public void addGradeTemp(GradeTemp gradeTemp);
	public GradeTemp getGradeTempByUser(String username);
	public void updateGradeTempById(String content, Long id);
}
