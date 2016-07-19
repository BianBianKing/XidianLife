package com.xidian.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.GradeTempDao;
import com.xidian.forms.GradeTemp;
import com.xidian.service.api.GradeTempService;

@Service("gradeTempServiceImpl")
@Transactional
public class GradeTempServiceImpl implements GradeTempService{
	@Resource(name="gradeTempDaoImpl")
	GradeTempDao gradeTempDao;

	@Override
	public void addGradeTemp(GradeTemp gradeTemp) {
		GradeTemp testExist = gradeTempDao.getGradeTempByUser(gradeTemp.getUsername());
		if(testExist == null) {
			gradeTempDao.addGradeTemp(gradeTemp);
		} else {
			gradeTempDao.updateGradeTempById(gradeTemp.getContent(), testExist.getId());
		}
	}

	@Override
	public GradeTemp getGradeTempByUser(String username) {
		return gradeTempDao.getGradeTempByUser(username);
	}

	@Override
	public void updateGradeTempById(String content, Long id) {
		gradeTempDao.updateGradeTempById(content, id);
	}
}
