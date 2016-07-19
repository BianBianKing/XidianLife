package com.xidian.forms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="semesterclass")
public class SemesterClass {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(targetEntity = Semester.class)
	@JoinColumn(name = "semester_id", updatable = false)
	private Semester semester;
	
	@ManyToOne(targetEntity = Classtable.class)
	@JoinColumn(name = "class_id", updatable = false)
	private Classtable classtable;

	public SemesterClass(Semester semester, Classtable classtable) {
		super();
		this.semester = semester;
		this.classtable = classtable;
	}

	public SemesterClass() {
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

	public Classtable getClasstable() {
		return classtable;
	}

	public void setClasstable(Classtable classtable) {
		this.classtable = classtable;
	}

	@Override
	public String toString() {
		return "SemesterClass [id=" + id + ", semester=" + semester + ", classtable=" + classtable + "]";
	}
	
	
}
