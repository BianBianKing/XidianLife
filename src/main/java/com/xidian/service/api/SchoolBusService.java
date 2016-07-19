package com.xidian.service.api;

import java.util.List;

import com.xidian.forms.SchoolBus;

public interface SchoolBusService {
	public void addSchoolBus(String title, String content);
	public void deleteSchoolBus(Long id);
	public List<SchoolBus> getSchoolBus();
	public SchoolBus getSchoolBusById(Long id);
	public void updateSchoolBus(Long id, SchoolBus schoolBus);
	public SchoolBus getNowSchoolBus();
	public void setNowSchoolBus(Long id);
}
