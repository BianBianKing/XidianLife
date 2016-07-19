package com.xidian.dao.api;

import java.util.List;
import com.xidian.forms.NewInfo;

public interface NewInfoDao {
	public void addNewInfo(NewInfo newInfo);
	public void deleteNewInfo(Long id);
	public List<NewInfo> getNewInfo();
	public NewInfo getNewInfoById(Long id);
	public void updateNewInfo(Long id, NewInfo newInfo);
	public List<NewInfo> getNewInfoByType(String type);
}
