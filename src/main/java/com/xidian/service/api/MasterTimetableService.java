package com.xidian.service.api;

import com.xidian.forms.MasterTimetable;

public interface MasterTimetableService {
	public void addMasterTimetable(MasterTimetable masterTimetable);
	public MasterTimetable getMasterTimetableByUser(String username);
	public void updateMasterTimetableById(String content, Long id);
}
