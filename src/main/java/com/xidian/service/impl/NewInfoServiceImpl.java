package com.xidian.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.NewInfoDao;
import com.xidian.forms.NewInfo;
import com.xidian.service.api.NewInfoService;

@Service("newInfoServiceImpl")
@Transactional
public class NewInfoServiceImpl implements NewInfoService{
	@Resource(name="newInfoDaoImpl")
	NewInfoDao newInfoDao;

	@Override
	public void addNewInfo(String title, String content, String type) {
		NewInfo tempNewInfo = new NewInfo();
		tempNewInfo.setContent(content);
		tempNewInfo.setTitle(title);
		tempNewInfo.setDate(new Date());
		tempNewInfo.setType(type);
		newInfoDao.addNewInfo(tempNewInfo);
	}

	@Override
	public void deleteNewInfo(Long id) {
		newInfoDao.deleteNewInfo(id);
	}

	@Override
	public List<NewInfo> getNewInfo() {
		return newInfoDao.getNewInfo();
	}

	@Override
	public NewInfo getNewInfoById(Long id) {
		return newInfoDao.getNewInfoById(id);
	}

	@Override
	public void updateNewInfo(Long id, NewInfo newInfo) {
		newInfoDao.updateNewInfo(id, newInfo);
	}

	@Override
	public List<NewInfo> getNewInfoByType(String type) {
		return newInfoDao.getNewInfoByType(type);
	}
	
}
