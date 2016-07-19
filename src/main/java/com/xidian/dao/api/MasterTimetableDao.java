package com.xidian.dao.api;

import com.xidian.forms.MasterTimetable;

public interface MasterTimetableDao {
	public void addMasterTimetable(MasterTimetable masterTimetable);
	public MasterTimetable getMasterTimetableByUser(String username);
	public void updateMasterTimetableById(String content, Long id);
}
