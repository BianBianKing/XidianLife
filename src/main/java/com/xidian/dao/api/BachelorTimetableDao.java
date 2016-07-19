package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.Classtable;
import com.xidian.forms.College;
import com.xidian.forms.Semester;
import com.xidian.forms.Timetable;

public interface BachelorTimetableDao {
	List<Semester> getSemesterList();
	List<College> getCollegeList();
	List<Classtable> getClasstableList();
	List<Timetable> getTimetableBySemesterId(int semester_id);
	List<College> getCollegeListBySemesterId(int semester_id);
	List<Classtable> getClasstableBySemesterIdAndCollegeId(int semester_id, int college_id);
	Timetable getTimetableBySemesterIdAndClasstableId(int semester_id, int class_id);
	List<Timetable> getTimetableBySemesterIdAndCollegeId(int semester_id, int college_id);
	List<Classtable> getClasstableByCollegeId(int college_id);
	List<Classtable> getClasstableBySemesterId(int semester_id);
	College getCollgeById(int college_id);
	List<Timetable> getAllTimetable();
	Semester getSemesterById(int semester_id);
	Classtable getClasstableById(int class_id);
	Timetable getTimetableById(int timetable_id);
	
	boolean addSemester(Semester semester);
	boolean addCollege(College college);
	boolean addClasstable(Classtable classtable);
	boolean addTimetable(Timetable timetable);
	
	boolean delSemester(int semester_id);
	boolean delCollege(int college_id);
	boolean delClasstable(int class_id);
	boolean delTimetable(int timetable_id);
	
	void delTimetable(Timetable timetable);
	
	void updateSemester(Semester semester);
	void updateCollege(College college);
	void updateClasstable(Classtable classtable);
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
