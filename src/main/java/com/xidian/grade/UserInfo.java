package com.xidian.grade;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WJ on 2015/7/25.
 */
public class UserInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8794181171491004871L;
	public String userName;
    public String password;

    private String lt;
    private String execution;
    private String _eventId;
    private String rmShown;
    private String cookie;
    private String jwxt_url;
    private List<SemesterInfo> semesterInfos;
    private List<SemesterGrade> semesterGrades;
    private FailGradeInfo failGradeInfo;
    private List<GradeInfo> currentGradeInfos;

    private String userDesc;


    public List<GradeInfo> getCurrentGradeInfos() {
        return currentGradeInfos;
    }

    public void setCurrentGradeInfos(List<GradeInfo> currentGradeInfos) {
        this.currentGradeInfos = currentGradeInfos;
    }

    public FailGradeInfo getFailGradeInfo() {
        return failGradeInfo;
    }

    public void setFailGradeInfo(FailGradeInfo failGradeInfo) {
        this.failGradeInfo = failGradeInfo;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String get_eventId() {
        return _eventId;
    }

    public void set_eventId(String _eventId) {
        this._eventId = _eventId;
    }

    public String getRmShown() {
        return rmShown;
    }

    public void setRmShown(String rmShown) {
        this.rmShown = rmShown;
    }


    public String getJwxt_url() {
        return jwxt_url;
    }

    public void setJwxt_url(String jwxt_url) {
        this.jwxt_url = jwxt_url;
    }

    public List<SemesterInfo> getSemesterInfos() {
        return semesterInfos;
    }

    public void setSemesterInfos(List<SemesterInfo> semesterInfos) {
        this.semesterInfos = semesterInfos;
    }

    public List<SemesterGrade> getSemesterGrades() {
        return semesterGrades;
    }

    public void setSemesterGrades(List<SemesterGrade> semesterGrades) {
        this.semesterGrades = semesterGrades;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }


}
