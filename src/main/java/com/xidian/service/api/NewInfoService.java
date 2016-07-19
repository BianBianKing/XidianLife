package com.xidian.service.api;

import java.util.List;

import com.xidian.forms.NewInfo;

public interface NewInfoService {
	public void addNewInfo(String title, String content, String type);
	public void deleteNewInfo(Long id);
	public List<NewInfo> getNewInfo();
	public NewInfo getNewInfoById(Long id);
	public void updateNewInfo(Long id, NewInfo newInfo);
	public List<NewInfo> getNewInfoByType(String type);
}
