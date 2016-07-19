package com.xidian.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.SchoolBusDao;
import com.xidian.forms.SchoolBus;
import com.xidian.service.api.SchoolBusService;

@Service("schoolBusServiceImpl")
@Transactional
public class SchoolBusServiceImpl implements SchoolBusService{
	@Resource(name="schoolBusDaoImpl")
	SchoolBusDao schoolBusDao;

	@Override
	public void addSchoolBus(String title, String content) {
		SchoolBus tempBus = new SchoolBus();
		tempBus.setContent(content);
		tempBus.setTitle(title);
		tempBus.setDate(new Date());
		schoolBusDao.addSchoolBus(tempBus);
	}

	@Override
	public void deleteSchoolBus(Long id) {
		schoolBusDao.deleteSchoolBus(id);
	}

	@Override
	public List<SchoolBus> getSchoolBus() {
		return schoolBusDao.getSchoolBus();
	}

	@Override
	public SchoolBus getSchoolBusById(Long id) {
		return schoolBusDao.getSchoolBusById(id);
	}

	@Override
	public void updateSchoolBus(Long id, SchoolBus schoolBus) {
		schoolBusDao.updateSchoolBus(id, schoolBus);
	}

	@Override
	public SchoolBus getNowSchoolBus() {
		return schoolBusDao.getNowSchoolBus();
	}

	@Override
	public void setNowSchoolBus(Long id) {
		schoolBusDao.setNowSchoolBus(id);
	}
}
