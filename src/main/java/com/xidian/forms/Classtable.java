package com.xidian.forms;

import static javax.persistence.CascadeType.ALL;
import static org.hibernate.annotations.FetchMode.JOIN;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

@Entity
@Table(name="classtable")
public class Classtable {
	@Id
	@GeneratedValue
	private int id;
	
	/*@OneToMany(targetEntity=SemesterClass.class, cascade=ALL )
	@Fetch(JOIN)
	@JoinColumn(name="class_id")
	public List<SemesterClass> semesterClassList;*/
	
	@ManyToMany(mappedBy = "classList",targetEntity = Semester.class, fetch = FetchType.LAZY)  
	private List<Semester> semesterList; 
	
	@ManyToOne(targetEntity = College.class)
	@JoinColumn(name = "college_id", updatable = false)
	private College college;
	
	@OneToMany(targetEntity=Timetable.class, cascade=ALL )
	@JoinColumn(name="class_id")
	public List<Timetable> timetableList;
	
	@Column
	private String class_name;

	public Classtable(College college, String class_name) {
		super();
		this.college = college;
		this.class_name = class_name;
	}

	public Classtable() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public List<SemesterClass> getSemesterClassList() {
		return semesterClassList;
	}

	public void setSemesterClassList(List<SemesterClass> semesterClassList) {
		this.semesterClassList = semesterClassList;
	}*/

	public College getCollege() {
		return college;
	}

	public List<Semester> getSemesterList() {
		return semesterList;
	}

	public void setSemesterList(List<Semester> semesterList) {
		this.semesterList = semesterList;
	}

	public List<Timetable> getTimetableList() {
		return timetableList;
	}

	public void setTimetableList(List<Timetable> timetableList) {
		this.timetableList = timetableList;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	/*@Override
	public String toString() {
		return "Classtable [id=" + id + ", semesterClassList=" + semesterClassList + ", college=" + college
				+ ", class_name=" + class_name + "]";
	}
	*/
	
}
