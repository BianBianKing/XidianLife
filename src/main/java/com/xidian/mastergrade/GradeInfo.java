package com.xidian.mastergrade;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WJ on 2015/7/27.
 */
public class GradeInfo implements Serializable{

    /**<br/>
	 * @Fields serialVersionUID
	 * @auther wy
	 */
	private static final long serialVersionUID = -5246566038363909501L;
	private String lowestTotalCredit;
    private String lowestDegreeCredit;
    private String hadCredit;
    private String hadDegreeCredit;
    private String averageScore;

    List<CourseInfo> courseInfos;

    public String getLowestTotalCredit() {
        return lowestTotalCredit;
    }

    public void setLowestTotalCredit(String lowestTotalCredit) {
        this.lowestTotalCredit = lowestTotalCredit;
    }

    public String getLowestDegreeCredit() {
        return lowestDegreeCredit;
    }

    public void setLowestDegreeCredit(String lowestDegreeCredit) {
        this.lowestDegreeCredit = lowestDegreeCredit;
    }

    public String getHadCredit() {
        return hadCredit;
    }

    public void setHadCredit(String hadCredit) {
        this.hadCredit = hadCredit;
    }

    public String getHadDegreeCredit() {
        return hadDegreeCredit;
    }

    public void setHadDegreeCredit(String hadDegreeCredit) {
        this.hadDegreeCredit = hadDegreeCredit;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public List<CourseInfo> getCourseInfos() {
        return courseInfos;
    }

    public void setCourseInfos(List<CourseInfo> courseInfos) {
        this.courseInfos = courseInfos;
    }

    @Override
    public String toString() {
        return "GradeInfo{" +
                "lowestTotalCredit='" + lowestTotalCredit + '\'' +
                ", lowestDegreeCredit='" + lowestDegreeCredit + '\'' +
                ", hadCredit='" + hadCredit + '\'' +
                ", hadDegreeCredit='" + hadDegreeCredit + '\'' +
                ", averageScore='" + averageScore + '\'' +
                '}';
    }
}
