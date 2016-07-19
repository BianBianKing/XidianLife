package com.xidian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.BachelorTimetableDao;
import com.xidian.forms.Classtable;
import com.xidian.forms.College;
import com.xidian.forms.Semester;
import com.xidian.forms.Timetable;
import com.xidian.service.api.BachelorTimetableService;

@Service("bachelorTimetableServiceImpl")
@Transactional
public class BachelorTimetableServiceImpl implements BachelorTimetableService {
	@Resource(name="bachelorTimetableDaoImpl")
	private BachelorTimetableDao bachelorTimetableDao;
	
	@Override
	public List<Semester> getSemesterList() {
		return bachelorTimetableDao.getSemesterList();
	}

	@Override
	public List<College> getCollegeList() {
		return bachelorTimetableDao.getCollegeList();
	}

	@Override
	public List<Classtable> getClasstableList() {
		return bachelorTimetableDao.getClasstableList();
	}

	@Override
	public List<Timetable> getTimetableBySemesterId(int semester_id) {
		return bachelorTimetableDao.getTimetableBySemesterId(semester_id);
	}

	@Override
	public List<College> getCollegeListBySemesterId(int semester_id) {
		return bachelorTimetableDao.getCollegeListBySemesterId(semester_id);
	}

	@Override
	public List<Classtable> getClasstableBySemesterIdAndCollegeId(int semester_id, int college_id) {
		return bachelorTimetableDao.getClasstableBySemesterIdAndCollegeId(semester_id, college_id);
	}

	@Override
	public Timetable getTimetableBySemesterIdAndClasstableId(int semester_id, int class_id) {
		return bachelorTimetableDao.getTimetableBySemesterIdAndClasstableId(semester_id, class_id);
	}

	@Override
	public boolean addSemester(String semester_name) {
		Semester s = new Semester(semester_name);
		return bachelorTimetableDao.addSemester(s);
	}

	@Override
	public boolean addCollege(String college_name) {
		College c = new College(college_name);
		return bachelorTimetableDao.addCollege(c);
	}

	@Override
	public boolean addClasstable(int college_id, String class_name) {
		College c = bachelorTimetableDao.getCollgeById(college_id);
		Classtable ct = new Classtable(c, class_name);
		return bachelorTimetableDao.addClasstable(ct);
	}

	@Override
	public boolean addTimetable(Timetable timetable) {
		return bachelorTimetableDao.addTimetable(timetable);
	}

	@Override
	public List<Classtable> getClasstableByCollegeId(int college_id) {
		return bachelorTimetableDao.getClasstableByCollegeId(college_id);
	}

	@Override
	public College getCollgeById(int college_id) {
		return bachelorTimetableDao.getCollgeById(college_id);
	}

	@Override
	public List<Timetable> getTimetableBySemesterIdAndCollegeId(int semester_id, int college_id) {
		return bachelorTimetableDao.getTimetableBySemesterIdAndCollegeId(semester_id, college_id);
	}

	@Override
	public List<Timetable> getAllTimetable() {
		return bachelorTimetableDao.getAllTimetable();
	}

	@Override
	public Semester getSemesterById(int semester_id) {
		return bachelorTimetableDao.getSemesterById(semester_id);
	}

	@Override
	public Classtable getClasstableById(int class_id) {
		return bachelorTimetableDao.getClasstableById(class_id);
	}

	@Override
	public boolean delSemester(int semester_id) {
		return bachelorTimetableDao.delSemester(semester_id);
	}

	@Override
	public boolean delCollege(int college_id) {
		return bachelorTimetableDao.delCollege(college_id);
	}

	@Override
	public boolean delClasstable(int class_id) {
		return bachelorTimetableDao.delClasstable(class_id);
	}

	@Override
	public boolean delTimetable(int timetable_id) {
		return bachelorTimetableDao.delTimetable(timetable_id);
	}

	@Override
	public boolean updateSemester(int semester_id, String semester_name) {
		Semester semester = bachelorTimetableDao.getSemesterById(semester_id);
		if(semester != null){
			semester.setSemester_name(semester_name);
			bachelorTimetableDao.updateSemester(semester);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean updateCollege(int college_id, String college_name) {
		College college = bachelorTimetableDao.getCollgeById(college_id);
		if(college != null){
			college.setCollege_name(college_name);
			bachelorTimetableDao.updateCollege(college);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean updateClasstable(int class_id, String class_name) {
		Classtable classtable = bachelorTimetableDao.getClasstableById(class_id);
		if(classtable != null){
			classtable.setClass_name(class_name);
			bachelorTimetableDao.updateClasstable(classtable);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean updateTimetable(int timetable_id, String photo, String pdf) {
		Timetable timetable = bachelorTimetableDao.getTimetableById(timetable_id);
		if(timetable != null){
			timetable.setPdf(pdf);
			timetable.setPhoto(photo);
			bachelorTimetableDao.updateTimetable(timetable);
			return true;
		}
		else
			return false;
	}

	@Override
	public void timetableUpdateCollege(Timetable timetable) {
		bachelorTimetableDao.timetableUpdateCollege(timetable);
	}

	@Override
	public void timetableUpdateClasstable(Timetable timetable) {
		bachelorTimetableDao.timetableUpdateClasstable(timetable);
	}

	@Override
	public void updateClasstable(Classtable classtable) {
		bachelorTimetableDao.updateClasstable(classtable);
	}

	@Override
	public void updateSemester(Semester semester) {
		bachelorTimetableDao.updateSemester(semester);
	}

	@Override
	public List<Classtable> getClasstableBySemesterId(int semester_id) {
		return bachelorTimetableDao.getClasstableBySemesterId(semester_id);
	}

	@Override
	public Timetable getTimetableById(int timetable_id) {
		return bachelorTimetableDao.getTimetableById(timetable_id);
	}

	@Override
	public void updateTimetable(Timetable timetable) {
		bachelorTimetableDao.updateTimetable(timetable);
	}

	@Override
	public void delTimetable(Timetable timetable) {
		bachelorTimetableDao.delTimetable(timetable);
	}

	@Override
	public long getAllTimetableCount() {
		return bachelorTimetableDao.getAllTimetableCount();
	}

	@Override
	public long getTimetableCountBySemesterId(int semester_id) {
		return bachelorTimetableDao.getTimetableCountBySemesterId(semester_id);
	}

	@Override
	public long getTimetableCountBySemesterIdAndCollegeId(int semester_id, int college_id) {
		return bachelorTimetableDao.getTimetableCountBySemesterIdAndCollegeId(semester_id, college_id);
	}

	@Override
	public List<Timetable> getTimetableForPage(int page, int pageSize) {
		return bachelorTimetableDao.getTimetableForPage(page, pageSize);
	}

	@Override
	public List<Timetable> getTimetableBySemesterIdForPage(int semester_id, int page, int pageSize) {
		return bachelorTimetableDao.getTimetableBySemesterIdForPage(semester_id, page, pageSize);
	}

	@Override
	public List<Timetable> getTimetableBySemesterIdAndCollegeIdForPage(int semester_id, int college_id, int page,
			int pageSize) {
		return bachelorTimetableDao.getTimetableBySemesterIdAndCollegeIdForPage(semester_id, college_id, page, pageSize);
	}

	

}
