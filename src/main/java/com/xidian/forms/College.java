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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

@Entity
@Table(name="college")
public class College {
	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private String college_name;
	
	/*@OneToMany(targetEntity=SemestersCollege.class, cascade=ALL )
	@Fetch(JOIN)
	@JoinColumn(name="semester_id")
	public List<SemestersCollege> semesterCollegeList;*/
	
	@ManyToMany(mappedBy = "collegeList",targetEntity = Semester.class, fetch = FetchType.LAZY)  
	private List<Semester> semesterList;  
	
	@OneToMany(targetEntity=Classtable.class, cascade=ALL )
	@JoinColumn(name="college_id")
	public List<Classtable> classList;

	public College() {
		super();
	}

	public College(String college_name) {
		super();
		this.college_name = college_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}

	/*public List<SemestersCollege> getSemesterCollegeList() {
		return semesterCollegeList;
	}

	public void setSemesterCollegeList(List<SemestersCollege> semesterCollegeList) {
		this.semesterCollegeList = semesterCollegeList;
	}*/

	public List<Classtable> getClassList() {
		return classList;
	}

	public List<Semester> getSemesterList() {
		return semesterList;
	}

	public void setSemesterList(List<Semester> semesterList) {
		this.semesterList = semesterList;
	}

	public void setClassList(List<Classtable> classList) {
		this.classList = classList;
	}

	/*@Override
	public String toString() {
		return "College [id=" + id + ", college_name=" + college_name + ", semesterCollegeList=" + semesterCollegeList
				+ ", classList=" + classList + "]";
	}*/
	public boolean equals(College c){
		if(c.id == id){
			return true;
		}
		else
			return false;
	}

	
}
