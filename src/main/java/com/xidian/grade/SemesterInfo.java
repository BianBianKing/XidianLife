package com.xidian.grade;

import java.io.Serializable;

/**
 * 学期条目信息
 * Created by WJ on 2015/7/26.
 */
public class SemesterInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3130172635842207295L;
	private String semesterName;
    private String url;

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SemesterInfo{" +
                "semesterName='" + semesterName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
