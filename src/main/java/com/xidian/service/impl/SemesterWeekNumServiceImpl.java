package com.xidian.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.SemesterWeekNumDao;
import com.xidian.forms.SemesterWeekNum;
import com.xidian.service.api.SemesterWeekNumService;

@Service("semesterWeekNumServiceImpl")
@Transactional
public class SemesterWeekNumServiceImpl implements SemesterWeekNumService{
	@Resource(name="semesterWeekNumDaoImpl")
	SemesterWeekNumDao semesterWeekNumDao;
	
	@Override
	public void updateSemesterWeekNum(SemesterWeekNum semesterWeekNum) {
		semesterWeekNumDao.updateSemesterWeekNum(semesterWeekNum);
	}

	@Override
	public SemesterWeekNum getSemesterWeekNum() {
		return semesterWeekNumDao.getSemesterWeekNum();
	}

	@Override
	public void semesterWeekNumAdd() {
		SemesterWeekNum semesterWeekNum = getSemesterWeekNum();
		if(semesterWeekNum != null){
			semesterWeekNum.setWeeknum(semesterWeekNum.getWeeknum() + 1);
			updateSemesterWeekNum(semesterWeekNum);
		}
	}

}
