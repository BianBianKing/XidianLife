package com.xidian.forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="ranker")
public class Ranker {

	@Id
	@GeneratedValue
	private Integer uuid;
	
	@Column(name="AUSERID")
	private Integer AuserId;
	
	@Column(name="BUSERID")
	private Integer BuserId;
	
	@Column(name="MATCHS")
	private Integer matchs;
	
	@Column(name="COUNTFLLOWERS")
	private Integer countFollowers;
	
	@Column(name="CREATEAT")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@Column(name="UPDATEAT")
	@Temporal(TemporalType.DATE)
	private Date updateAt;
	@Transient
	private String aPhoto;
	@Transient
	private String aName;
	@Transient
	private String bPhoto;
	@Transient
	private String bName;

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public Integer getAuserId() {
		return AuserId;
	}

	public void setAuserId(Integer auserId) {
		AuserId = auserId;
	}

	public Integer getBuserId() {
		return BuserId;
	}

	public void setBuserId(Integer buserId) {
		BuserId = buserId;
	}

	public Integer getMatchs() {
		return matchs;
	}

	public void setMatchs(Integer matchs) {
		this.matchs = matchs;
	}

	public Integer getCountFollowers() {
		return countFollowers;
	}

	public void setCountFollowers(Integer countFollowers) {
		this.countFollowers = countFollowers;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
	public String getaPhoto() {
		return aPhoto;
	}

	public void setaPhoto(String aPhoto) {
		this.aPhoto = aPhoto;
	}

	public String getbPhoto() {
		return bPhoto;
	}

	public void setbPhoto(String bPhoto) {
		this.bPhoto = bPhoto;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	@Override
	public String toString() {
		return "Ranker [uuid=" + uuid + ", AuserId=" + AuserId + ", BuserId=" + BuserId + ", matchs=" + matchs
				+ ", countFollowers=" + countFollowers + ", createAt=" + createAt + ", updateAt=" + updateAt
				+ ", aPhoto=" + aPhoto + ", bPhoto=" + bPhoto + "]";
	}

}
