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
@Table(name="votes")
public class Votes {
	@Id
	@GeneratedValue
	private long uuid;
	
	@Column(name="CREATEAT")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@Column(name="IDENTITY")
	private String identity;
	
	@Column(name="RANKERID")
	private Integer rankerId;

	public Votes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Votes(Date createAt, String identity, Integer rankerId) {
		super();
		this.createAt = createAt;
		this.identity = identity;
		this.rankerId = rankerId;
	}

	public long getUuId() {
		return uuid;
	}

	public void setUuId(long uuid) {
		this.uuid = uuid;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Integer getRankerId() {
		return rankerId;
	}

	public void setRankerId(Integer rankerId) {
		this.rankerId = rankerId;
	}

	@Override
	public String toString() {
		return "Votes [uuid=" + uuid + ", createAt=" + createAt + ", identity=" + identity + ", rankerId=" + rankerId + "]";
	}
	
}
