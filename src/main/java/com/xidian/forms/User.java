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
@Table(name="user")
public class User {
	@Id
	@GeneratedValue
	private long uid;
	
	@Column(name="email")
	private String username;
	
	@Column(name="pwd")
	private String password;
	
	@Column(name="name")
	private String name;
	
	@Column(name="register_date")
	@Temporal(TemporalType.DATE)
	private Date register_date;
	
	@Column(name="address")
	private String address;
	
	@Column(name="school")
	private String school;

	@Column(name="phone")
	private String phone;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="birthday")
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name="head_icon")
	private String head_icon;
	
	@Column(name="isTeacher")
	private boolean isTeacher;

	public boolean isTeacher() {
		return isTeacher;
	}

	public void setTeacher(boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Date getRegister_date() {
		return register_date;
	}
	
	public void setRegister_date(Date register_date) {
		this.register_date = register_date; 
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getHead_icon() {
		return head_icon;
	}
	
	public void setHead_icon(String head_icon) {
		this.head_icon = head_icon;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", register_date=" + register_date + ", address=" + address + ", school=" + school + ", phone="
				+ phone + ", sex=" + sex + ", birthday=" + birthday + ", head_icon=" + head_icon + ", isTeacher="
				+ isTeacher + "]";
	}

}
