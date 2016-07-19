package com.xidian.forms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="timetable")
public class Timetable {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne(targetEntity = Classtable.class)
	@JoinColumn(name = "class_id", updatable = false)
	private Classtable classtable;
	
	@ManyToOne(targetEntity = Semester.class)
	@JoinColumn(name = "semester_id", updatable = false)
	private Semester semester;
	
	@Column
	private String photo;
	
	@Column
	private String pdf;

	public Timetable(Classtable classtable, Semester semester, String photo, String pdf) {
		super();
		this.classtable = classtable;
		this.semester = semester;
		this.photo = photo;
		this.pdf = pdf;
	}

	public Timetable() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Classtable getClasstable() {
		return classtable;
	}

	public void setClasstable(Classtable classtable) {
		this.classtable = classtable;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	@Override
	public String toString() {
		return "Timetable [id=" + id + ", classtable=" + classtable + ", semester=" + semester + ", photo=" + photo
				+ ", pdf=" + pdf + "]";
	}
	
}
