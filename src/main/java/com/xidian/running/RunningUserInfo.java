package com.xidian.running;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WJ on 2015/7/25.
 */
public class RunningUserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -37158461415841042L;
	private String cookies;
    public String getCookies() {
		return cookies;
	}
	public void setCookies(String cookies) {
		this.cookies = cookies;
	}
	//学号
	private String userName;
	//密码
    private String password;
    //姓名
    private String name;
    //学号
    private String number;
    //性别
    private String sex;
    
    //
    private String distanceNum;
    private String speed;
    private String count;
    
    //
    private String group;
    private String minSpeed;
    private String maxSpeed;
    
    //
    private String addNum;
    private String decNum;
    private String finalNum;
    
    //单位、、没用。。。
    private String ms = " m/s";
    private String m = " m";
    
    //跑步记录
    private List<RunningInfo> runningInfos;
    
    public RunningUserInfo(){
    	this.userName="";
    	this.password="";
    }
    public RunningUserInfo(String name,String password){
    	this.userName=name;
    	this.password=password;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDistanceNum() {
		return distanceNum;
	}
	public void setDistanceNum(String distanceNum) {
		this.distanceNum = distanceNum;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getMinSpeed() {
		return minSpeed;
	}
	public void setMinSpeed(String minSpeed) {
		this.minSpeed = minSpeed;
	}
	public String getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(String maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public String getAddNum() {
		return addNum;
	}
	public void setAddNum(String addNum) {
		this.addNum = addNum;
	}
	public String getDecNum() {
		return decNum;
	}
	public void setDecNum(String decNum) {
		this.decNum = decNum;
	}
	public String getFinalNum() {
		return finalNum;
	}
	public void setFinalNum(String finalNum) {
		this.finalNum = finalNum;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	public List<RunningInfo> getRunningInfos() {
		return runningInfos;
	}
	public void setRunningInfos(List<RunningInfo> runningInfos) {
		this.runningInfos = runningInfos;
	}
	@Override
	public String toString() {
		return "学生信息：["  + "登录学号=" + userName + ", 登录密码=" + password + ", 姓名=" + name
				  + ", 性别=" + sex + "]"+ "\n长跑成绩汇总：[总里程=" + distanceNum + ", 平均速度=" + speed
				+ ", 有效次数=" + count + "]"+ "\n成绩认定标准:[所在分组=" + group + ", 最低速度=" + minSpeed + ", 最低里程=" + maxSpeed
				+ "]"+ "\n分数情况:[ 奖励加分=" + addNum + ", 惩罚扣分=" + decNum + ", 最终得分=" + finalNum + "]";
	}
    public String runningRecordsToString(){
    	String string=this.name+"的跑步记录:";
    	for(RunningInfo r:this.runningInfos){
    		string=string+"\n"+r.toString();
    	}
    	return string;
    }
    

    
    
    
}
