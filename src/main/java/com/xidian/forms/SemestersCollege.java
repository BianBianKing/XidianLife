package com.xidian.forms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static javax.persistence.CascadeType.*;
import static org.hibernate.annotations.FetchMode.*;
@Entity
@Table(name="semesterscollege")
public class SemestersCollege {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(targetEntity = Semester.class)
	@JoinColumn(name = "semester_id", updatable = false)
	private Semester semester;
	
	@ManyToOne(targetEntity = College.class)
	@JoinColumn(name = "college_id", updatable = false)
	private College college;

	public SemestersCollege(Semester semester, College college) {
		super();
		this.semester = semester;
		this.college = college;
	}

	public SemestersCollege() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "SemestersCollege [id=" + id + ", semester=" + semester + ", college=" + college + "]";
	}
	
	
}
