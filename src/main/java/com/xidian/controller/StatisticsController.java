package com.xidian.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xidian.forms.FlowStatistics;
import com.xidian.forms.GradeStatistics;
import com.xidian.service.api.FlowStatisticsService;
import com.xidian.service.api.GradeStatisticsService;

@Controller
@RequestMapping("/admin")
public class StatisticsController {
	@Resource(name="gradeStatisticsServiceImpl")
	GradeStatisticsService gradeStatisticsService;
	
	@Resource(name="flowStatisticsServiceImpl")
	FlowStatisticsService flowStatisticsService;
	
	@RequestMapping("showGradeStatistic")
	public String showGradeStatistic(Model model){
		List<GradeStatistics> gradeList = gradeStatisticsService.getGradeStatistics();
		model.addAttribute("gradeList", gradeList);
		return "statistics/showGradeStatistic";
	}
	
	@RequestMapping("showFlowStatistic")
	public String showFlowStatistic(Model model){
		List<FlowStatistics> flowList = flowStatisticsService.getFlowStatistics();
		model.addAttribute("flowList", flowList);
		return "statistics/showFlowStatistic";
	}
}
