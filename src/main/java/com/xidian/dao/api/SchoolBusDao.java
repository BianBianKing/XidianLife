package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.SchoolBus;

public interface SchoolBusDao {
	public void addSchoolBus(SchoolBus schoolBus);
	public void deleteSchoolBus(Long id);
	public List<SchoolBus> getSchoolBus();
	public SchoolBus getSchoolBusById(Long id);
	public void updateSchoolBus(Long id, SchoolBus schoolBus);
	public SchoolBus getNowSchoolBus();
	public void setNowSchoolBus(Long id);
}
