package com.xidian.forms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="semesterweeknum")
public class SemesterWeekNum {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="weeknum")
	private Integer weeknum;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getWeeknum() {
		return weeknum;
	}

	public void setWeeknum(Integer weeknum) {
		this.weeknum = weeknum;
	}

	public SemesterWeekNum(Integer weeknum) {
		super();
		this.weeknum = weeknum;
	}

	public SemesterWeekNum() {
		super();
	}
	
	
}
