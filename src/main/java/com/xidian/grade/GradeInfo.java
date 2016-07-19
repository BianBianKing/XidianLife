package com.xidian.grade;

import java.io.Serializable;

/**
 * Created by WJ on 2015/7/26.
 */
public class GradeInfo  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3398870444677941121L;
	private String courseCode;
    private String courseOrderCode;
    private String courseName;
    private String courseEnglistName;
    private String   credit;
    private String courseSort;
    private String courseScore;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseOrderCode() {
        return courseOrderCode;
    }

    public void setCourseOrderCode(String courseOrderCode) {
        this.courseOrderCode = courseOrderCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseEnglistName() {
        return courseEnglistName;
    }

    public void setCourseEnglistName(String courseEnglistName) {
        this.courseEnglistName = courseEnglistName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCourseSort() {
        return courseSort;
    }

    public void setCourseSort(String courseSort) {
        this.courseSort = courseSort;
    }

    public String getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(String courseScore) {
        this.courseScore = courseScore;
    }

    @Override
    public String toString() {
        return "GradeInfo{" +
                "courseCode='" + courseCode + '\'' +
                ", courseOrderCode='" + courseOrderCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseEnglistName='" + courseEnglistName + '\'' +
                ", credit=" + credit +
                ", courseSort='" + courseSort + '\'' +
                ", courseScore='" + courseScore + '\'' +
                '}';
    }
}
