package com.xidian.forms;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

import static javax.persistence.CascadeType.*;
import static org.hibernate.annotations.FetchMode.*;

@Entity
@Table(name="semester")
public class Semester {
	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private String semester_name;
	
	/*@OneToMany(targetEntity=SemestersCollege.class, cascade=ALL )
	@Fetch(JOIN)
	@JoinColumn(name="semester_id")
	public List<SemestersCollege> semesterCollegeList;*/
	
	@ManyToMany(targetEntity = College.class, fetch = FetchType.LAZY)  
	@JoinTable(name = "semesterscollege", joinColumns = @JoinColumn(name = "semester_id"), inverseJoinColumns = @JoinColumn(name = "college_id"))  
	private List<College> collegeList;  
	
	/*@OneToMany(targetEntity=SemesterClass.class, cascade=ALL )
	@Fetch(JOIN)
	@JoinColumn(name="semester_id")
	public List<SemesterClass> semesterClassList;*/
	
	@ManyToMany(targetEntity = Classtable.class, fetch = FetchType.LAZY)  
	@JoinTable(name = "semesterclass", joinColumns = @JoinColumn(name = "semester_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))  
	private List<Classtable> classList; 
	
	@OneToMany(targetEntity=Timetable.class, cascade=ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="semester_id")
	public List<Timetable> timetableList;

	public Semester(String semester_name) {
		super();
		this.semester_name = semester_name;
	}

	public Semester() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSemester_name() {
		return semester_name;
	}

	public void setSemester_name(String semester_name) {
		this.semester_name = semester_name;
	}

	/*public List<SemestersCollege> getSemesterCollegeList() {
		return semesterCollegeList;
	}

	public void setSemesterCollegeList(List<SemestersCollege> semesterCollegeList) {
		this.semesterCollegeList = semesterCollegeList;
	}*/

	/*public List<SemestersCollege> getSemesterClassList() {
		return semesterClassList;
	}*/

	public List<College> getCollegeList() {
		return collegeList;
	}

	public void setCollegeList(List<College> collegeList) {
		this.collegeList = collegeList;
	}

	/*public void setSemesterClassList(List<SemestersCollege> semesterClassList) {
		this.semesterClassList = semesterClassList;
	}*/

	

	public List<Classtable> getClassList() {
		return classList;
	}

	public void setClassList(List<Classtable> classList) {
		this.classList = classList;
	}

	public List<Timetable> getTimetableList() {
		return timetableList;
	}

	public void setTimetableList(List<Timetable> timetableList) {
		this.timetableList = timetableList;
	}

	

	/*@Override
	public String toString() {
		return "Semester [id=" + id + ", semester_name=" + semester_name + ", semesterCollegeList="
				+ semesterCollegeList + ", semesterClassList=" + semesterClassList + ", timetableList=" + timetableList
				+ "]";
	}*/
	public boolean equals(Semester s){
		if(s.getSemester_name() == this.semester_name)
			return true;
		else
			return false;
	}
}
