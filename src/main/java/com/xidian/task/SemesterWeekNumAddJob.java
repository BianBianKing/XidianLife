package com.xidian.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xidian.service.api.SemesterWeekNumService;

@Component
public class SemesterWeekNumAddJob {
	@Resource(name="semesterWeekNumServiceImpl")
	SemesterWeekNumService semesterWeekNumService;
	
	@Scheduled(cron = "0 0 0 ? * MON")  
	public void semesterWeekNumAddJob(){
		semesterWeekNumService.semesterWeekNumAdd();
		System.out.println("更新了教学周");
	}
	
	
}
