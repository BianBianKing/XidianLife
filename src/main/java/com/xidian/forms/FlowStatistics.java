package com.xidian.forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="flowstatistics")
public class FlowStatistics {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="num")
	private int num;
	
	@Column(name="oknum")
	private int okNum;
	
	@Column(name="date")
	private Date date;

	public FlowStatistics() {
		super();
	}

	public FlowStatistics(int num, int okNum, Date date) {
		super();
		this.num = num;
		this.okNum = okNum;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getOkNum() {
		return okNum;
	}

	public void setOkNum(int okNum) {
		this.okNum = okNum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
