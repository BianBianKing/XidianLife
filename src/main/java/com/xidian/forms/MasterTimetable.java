package com.xidian.forms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mastertimetable")
public class MasterTimetable {
	@Id
	@GeneratedValue
	private long id;

	@Column(name="username")
	private String username;
	
	@Column(name="content")
	private String content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MasterTimetable(String username, String content) {
		super();
		this.username = username;
		this.content = content;
	}

	public MasterTimetable() {
		super();
	}

}
