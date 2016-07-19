package com.xidian.timetable;

import java.io.Serializable;

import com.xidian.mastergrade.GradeInfo;

/**
 * Created by WJ on 2015/7/27.
 */
public class UserInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3808804984563235640L;
	private String userName;
    private String password;
    private String userDesc;

    private String jysxt_url;

    private String lt;
    private String execution;
    private String _eventId;
    private String rmShown;
    private GradeInfo gradeInfo;
    private int errorCode;
    
    private String[][] myCourse=new String[8][11]; 
    
    

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public GradeInfo getGradeInfo() {
        return gradeInfo;
    }

    public void setGradeInfo(GradeInfo gradeInfo) {
        this.gradeInfo = gradeInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
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


    public String getJysxt_url() {
        return jysxt_url;
    }

    public void setJysxt_url(String jysxt_url) {
        this.jysxt_url = jysxt_url;
    }

    public String[][] getMyCourse() {
		return myCourse;
	}

	public void setMyCourse(String[][] myCourse) {
		this.myCourse = myCourse;
	}

	@Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userDesc='" + userDesc + '\'' +
                ", jysxt_url='" + jysxt_url + '\'' +
                ", lt='" + lt + '\'' +
                ", execution='" + execution + '\'' +
                ", _eventId='" + _eventId + '\'' +
                ", rmShown='" + rmShown + '\'' +
                '}';
    }
}
