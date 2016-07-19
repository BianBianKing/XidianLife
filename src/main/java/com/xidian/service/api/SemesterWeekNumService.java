package com.xidian.service.api;

import com.xidian.forms.SemesterWeekNum;

public interface SemesterWeekNumService {
	void updateSemesterWeekNum(SemesterWeekNum semesterWeekNum);
	SemesterWeekNum getSemesterWeekNum();
	void semesterWeekNumAdd();
}
