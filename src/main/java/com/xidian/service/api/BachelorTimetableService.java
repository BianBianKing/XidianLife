package com.xidian.service.api;

import java.util.List;

import com.xidian.forms.Classtable;
import com.xidian.forms.College;
import com.xidian.forms.Semester;
import com.xidian.forms.Timetable;

public interface BachelorTimetableService {
	public List<Semester> getSemesterList();
	public List<College> getCollegeList();
	public List<Classtable> getClasstableList();
	public List<Timetable> getTimetableBySemesterId(int semester_id);
	public List<College> getCollegeListBySemesterId(int semester_id);
	public List<Classtable> getClasstableBySemesterIdAndCollegeId(int semester_id, int college_id);
	public Timetable getTimetableBySemesterIdAndClasstableId(int semester_id, int class_id);
	List<Classtable> getClasstableByCollegeId(int college_id);
	College getCollgeById(int college_id);
	List<Timetable> getTimetableBySemesterIdAndCollegeId(int semester_id, int college_id);
	List<Timetable> getAllTimetable();
	Semester getSemesterById(int semester_id);
	Classtable getClasstableById(int class_id);
	List<Classtable> getClasstableBySemesterId(int semester_id);
	Timetable getTimetableById(int timetable_id);
	
	boolean addSemester(String semester_name);
	boolean addCollege(String college_name);
	boolean addClasstable(int college_id, String class_name);
	boolean addTimetable(Timetable timetable);
	
	boolean delSemester(int semester_id);
	boolean delCollege(int college_id);
	boolean delClasstable(int class_id);
	boolean delTimetable(int timetable_id);
	
	void delTimetable(Timetable timetable);
	
	boolean updateSemester(int semester_id, String semester_name);
	boolean updateCollege(int college_id, String college_name);
	boolean updateClasstable(int class_id, String class_name);
	boolean updateTimetable(int timetable_id, String photo, String pdf);
	
	void updateClasstable(Classtable classtable);
	void updateSemester(Semester semester);
	void updateTimetable(Timetable timetable);
	
	void timetableUpdateCollege(Timetable timetable);
	void timetableUpdateClasstable(Timetable timetable);
	
	long getAllTimetableCount();
	long getTimetableCountBySemesterId(int semester_id);
	long getTimetableCountBySemesterIdAndCollegeId(int semester_id,int college_id);
	
	List<Timetable> getTimetableForPage(int page, int pageSize);
	List<Timetable> getTimetableBySemesterIdForPage(int semester_id, int page, int pageSize);
	List<Timetable> getTimetableBySemesterIdAndCollegeIdForPage(int semester_id, int college_id,int page, int pageSize);
}
