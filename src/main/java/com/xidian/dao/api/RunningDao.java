package com.xidian.dao.api;

import com.xidian.forms.RunningTemp;

public interface RunningDao {
	public void addRunningTemp(RunningTemp runningTemp);
	public RunningTemp getRunningTempByUser(String username);
	public void updateRunningTempById(String content, Long id);
}
