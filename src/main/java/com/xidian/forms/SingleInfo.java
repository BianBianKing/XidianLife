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
@Table(name="singleInfo")
public class SingleInfo {

	
	@Id
	@GeneratedValue
	private Integer uuid;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="SCHOOL")
	private String school;
	
	@Column(name="COLLEGE")
	private String college;
	
	@Column(name="GRADE")
	private String grade;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="QQ")
	private String qq;
	
	@Column(name="HOBBY")
	private String hobby;
	
	@Column(name="TARGET")
	private Integer target;
	
	@Column(name="PHOTOURL")
	private String photourl;
	
	@Column(name="CREATEAT")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@Column(name="UPDATEAT")
	@Temporal(TemporalType.DATE)
	private Date updateAt;
	
	@Column(name="TRAIN")
	private Integer train;
	
	@Column(name="PHOTOCOMPRESS")
	private String photocompress;
	
	public Integer getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
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

	public Integer getTrain() {
		return train;
	}

	public void setTrain(Integer train) {
		this.train = train;
	}

	public String getPhotocompress() {
		return photocompress;
	}

	public void setPhotocompress(String photocompress) {
		this.photocompress = photocompress;
	}

	@Override
	public String toString() {
		return "SingleInfo [uuid=" + uuid + ", name=" + name + ", school=" + school + ", college=" + college
				+ ", grade=" + grade + ", gender=" + gender + ", qq=" + qq + ", hobby=" + hobby + ", target=" + target
				+ ", photourl=" + photourl + ", createAt=" + createAt + ", updateAt=" + updateAt + ", train=" + train
				+ ", photocompress=" + photocompress + "]";
	}


	

	
	
}
