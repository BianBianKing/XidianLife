package com.xidian.grade;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WJ on 2015/7/28.
 */
public class FailGradeInfo  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1568757621440068108L;
	private String failCoursCredit;
    private String failCoursNum;
    private List<GradeInfo> failGradeInfos;

    public String getFailCoursNum() {
        return failCoursNum;
    }

    public void setFailCoursNum(String failCoursNum) {
        this.failCoursNum = failCoursNum;
    }

    public String getFailCoursCredit() {
        return failCoursCredit;
    }

    public void setFailCoursCredit(String failCoursCredit) {
        this.failCoursCredit = failCoursCredit;
    }

    public List<GradeInfo> getFailGradeInfos() {
        return failGradeInfos;
    }

    public void setFailGradeInfos(List<GradeInfo> failGradeInfos) {
        this.failGradeInfos = failGradeInfos;
    }
}
