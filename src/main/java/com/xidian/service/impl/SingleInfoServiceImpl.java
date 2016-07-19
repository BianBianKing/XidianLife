package com.xidian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.SingleInfoDao;
import com.xidian.forms.SingleInfo;
import com.xidian.service.api.SingleInfoService;

@Service("singleInfoServiceImpl")
@Transactional
public class SingleInfoServiceImpl implements SingleInfoService {

	@Resource(name="singleInfoDaoImpl")
	SingleInfoDao singleInfoDao;
	@Override
	public void addSingleInfo(SingleInfo singleInfo) {
		// TODO Auto-generated method stub
		singleInfoDao.addSingleInfo(singleInfo);
	}

	@Override
	public void deleteSingleInfo(SingleInfo singleInfo) {
		// TODO Auto-generated method stub
		singleInfoDao.deleteSingleInfo(singleInfo);
	}

	@Override
	public void updateSingleInfo(SingleInfo singleInfo) {
		// TODO Auto-generated method stub
		singleInfoDao.updateSingleInfo(singleInfo);
	}

	@Override
	public List<SingleInfo> getSingleInfo() {
		// TODO Auto-generated method stub
		return singleInfoDao.getSingleInfo();
	}

	@Override
	public SingleInfo getSingleInfoById(Integer uuid) {
		// TODO Auto-generated method stub
		return singleInfoDao.getSingleInfoById(uuid);
	}

}
