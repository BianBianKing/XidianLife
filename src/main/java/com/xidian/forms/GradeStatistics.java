package com.xidian.forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gradestatistics")
public class GradeStatistics {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="bachelornum")
	private int bachelorNum;
	
	@Column(name="masternum")
	private int masterNum;

	@Column(name="bacheloroknum")
	private int bachelorOkNum;
	
	@Column(name="masteroknum")
	private int masterOkNum;
	
	@Column(name="date")
	private Date date;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBachelorNum() {
		return bachelorNum;
	}

	public void setBachelorNum(int bachelorNum) {
		this.bachelorNum = bachelorNum;
	}

	public int getMasterNum() {
		return masterNum;
	}

	public void setMasterNum(int masterNum) {
		this.masterNum = masterNum;
	}

	public int getBachelorOkNum() {
		return bachelorOkNum;
	}

	public void setBachelorOkNum(int bachelorOkNum) {
		this.bachelorOkNum = bachelorOkNum;
	}

	public int getMasterOkNum() {
		return masterOkNum;
	}

	public void setMasterOkNum(int masterOkNum) {
		this.masterOkNum = masterOkNum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public GradeStatistics() {
		super();
	}

	public GradeStatistics(int bachelorNum, int masterNum, int bachelorOkNum, int masterOkNum, Date date) {
		super();
		this.bachelorNum = bachelorNum;
		this.masterNum = masterNum;
		this.bachelorOkNum = bachelorOkNum;
		this.masterOkNum = masterOkNum;
		this.date = date;
	}
	
}
