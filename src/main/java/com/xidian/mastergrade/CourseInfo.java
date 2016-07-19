package com.xidian.mastergrade;

import java.io.Serializable;

/**
 * Created by WJ on 2015/7/27.
 */
public class CourseInfo implements Serializable{

    /**<br/>
	 * @Fields serialVersionUID
	 * @auther wy
	 */
	private static final long serialVersionUID = -4995801169636722488L;
	private String courseTime;
    private String courseCode;
    private String courseName;
    private String courseCredit;
    private String courseSort;
    private String courseScore;
    private String courseGotCredit;
    private String ps;

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
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

    public String getCourseGotCredit() {
        return courseGotCredit;
    }

    public void setCourseGotCredit(String courseGotCredit) {
        this.courseGotCredit = courseGotCredit;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "courseTime='" + courseTime + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseCredit='" + courseCredit + '\'' +
                ", courseSort='" + courseSort + '\'' +
                ", courseScore='" + courseScore + '\'' +
                ", courseGotCredit='" + courseGotCredit + '\'' +
                ", ps='" + ps + '\'' +
                '}';
    }
}
