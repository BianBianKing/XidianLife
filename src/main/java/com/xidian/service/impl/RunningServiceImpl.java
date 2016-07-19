package com.xidian.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.RunningDao;
import com.xidian.forms.RunningTemp;
import com.xidian.service.api.RunningService;

@Service("runningServiceImpl")
@Transactional
public class RunningServiceImpl implements RunningService{
	@Resource(name="runningDaoImpl")
	private RunningDao runningDao;
	
	@Override
	public void addRunningTemp(RunningTemp runningTemp) {
		RunningTemp testExist = runningDao.getRunningTempByUser(runningTemp.getUsername());
		if(testExist == null) {
			runningDao.addRunningTemp(runningTemp);
		} else {
			runningDao.updateRunningTempById(runningTemp.getContent(), testExist.getId());
		}
	}

	@Override
	public RunningTemp getRunningTempByUser(String username) {
		return runningDao.getRunningTempByUser(username);
	}

	@Override
	public void updateRunningTempById(String content, Long id) {
		runningDao.updateRunningTempById(content, id);
	}

}
