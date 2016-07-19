package com.xidian.grade;
import java.io.Serializable;
import java.util.List;

/**
 * Created by WJ on 2015/7/26.
 */
public class SemesterGrade  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6046669474315849401L;
	private String semesterName;
    private List<GradeInfo> gradeInfos;
    private int lowestCredit;
    private int hadCredit;
    private int hadCourseNum;
    private int successCourseNum;

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public List<GradeInfo> getGradeInfos() {
        return gradeInfos;
    }

    public void setGradeInfos(List<GradeInfo> gradeInfos) {
        this.gradeInfos = gradeInfos;
    }

    public int getLowestCredit() {
        return lowestCredit;
    }

    public void setLowestCredit(int lowestCredit) {
        this.lowestCredit = lowestCredit;
    }

    public int getHadCredit() {
        return hadCredit;
    }

    public void setHadCredit(int hadCredit) {
        this.hadCredit = hadCredit;
    }

    public int getHadCourseNum() {
        return hadCourseNum;
    }

    public void setHadCourseNum(int hadCourseNum) {
        this.hadCourseNum = hadCourseNum;
    }

    public int getSuccessCourseNum() {
        return successCourseNum;
    }

    public void setSuccessCourseNum(int successCourseNum) {
        this.successCourseNum = successCourseNum;
    }
}
