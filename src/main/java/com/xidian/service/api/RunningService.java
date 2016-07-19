package com.xidian.service.api;

import com.xidian.forms.RunningTemp;

public interface RunningService {
	public void addRunningTemp(RunningTemp runningTemp);
	public RunningTemp getRunningTempByUser(String username);
	public void updateRunningTempById(String content, Long id);
}
