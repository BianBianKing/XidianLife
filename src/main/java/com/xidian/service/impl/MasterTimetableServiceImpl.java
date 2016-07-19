package com.xidian.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.MasterTimetableDao;
import com.xidian.forms.MasterTimetable;
import com.xidian.forms.RunningTemp;
import com.xidian.service.api.MasterTimetableService;

@Service("masterTimetableServiceImpl")
@Transactional
public class MasterTimetableServiceImpl implements MasterTimetableService{
	@Resource(name="masterTimetableDaoImpl")
	private MasterTimetableDao masterTimetableDao;
	
	@Override
	public void addMasterTimetable(MasterTimetable masterTimetable) {
		MasterTimetable testExist = masterTimetableDao.getMasterTimetableByUser(masterTimetable.getUsername());
		if(testExist == null) {
			masterTimetableDao.addMasterTimetable(masterTimetable);
		} else {
			masterTimetableDao.updateMasterTimetableById(masterTimetable.getContent(), testExist.getId());
		}
	}

	@Override
	public MasterTimetable getMasterTimetableByUser(String username) {
		return masterTimetableDao.getMasterTimetableByUser(username);
	}

	@Override
	public void updateMasterTimetableById(String content, Long id) {
		masterTimetableDao.updateMasterTimetableById(content, id);
	}

}
