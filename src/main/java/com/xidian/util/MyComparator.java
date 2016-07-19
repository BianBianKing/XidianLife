package com.xidian.util;

import java.util.Comparator;

import com.xidian.forms.Courses;

public class MyComparator implements Comparator<Object>{

/**
* o1比o2大，返回-1；o1比o2小，返回1。
*/
	public int compare(Object o1, Object o2) {
		Courses tempCourses = (Courses)o1;
		Courses tempCourses2 = (Courses)o2;
		if (tempCourses.getDate().before(tempCourses2.getDate())){
		return 1;
		}
		if (tempCourses2.getDate().before(tempCourses.getDate())){
		return -1;
		}
		return 0;
	}
}
