package com.xidian.forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="cards")
public class Cards {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name="lost_name")
	private String lostName;
	
	@Column(name="lost_num")
	private String lostNum;

	@Column(name="keeper_name")
	private String keeperName;
	
	@Column(name="keeper_phone")
	private String keeperPhone;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLostName() {
		return lostName;
	}

	public void setLostName(String lostName) {
		this.lostName = lostName;
	}

	public String getLostNum() {
		return lostNum;
	}

	public void setLostNum(String lostNum) {
		this.lostNum = lostNum;
	}

	public String getKeeperName() {
		return keeperName;
	}

	public void setKeeperName(String keeperName) {
		this.keeperName = keeperName;
	}

	public String getKeeperPhone() {
		return keeperPhone;
	}

	public void setKeeperPhone(String keeperPhone) {
		this.keeperPhone = keeperPhone;
	}
}
