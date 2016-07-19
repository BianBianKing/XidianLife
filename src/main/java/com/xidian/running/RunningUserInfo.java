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
	//ѧ��
	private String userName;
	//����
    private String password;
    //����
    private String name;
    //ѧ��
    private String number;
    //�Ա�
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
    
    //��λ����û�á�����
    private String ms = " m/s";
    private String m = " m";
    
    //�ܲ���¼
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
		return "ѧ����Ϣ��["  + "��¼ѧ��=" + userName + ", ��¼����=" + password + ", ����=" + name
				  + ", �Ա�=" + sex + "]"+ "\n���ܳɼ����ܣ�[�����=" + distanceNum + ", ƽ���ٶ�=" + speed
				+ ", ��Ч����=" + count + "]"+ "\n�ɼ��϶���׼:[���ڷ���=" + group + ", ����ٶ�=" + minSpeed + ", ������=" + maxSpeed
				+ "]"+ "\n�������:[ �����ӷ�=" + addNum + ", �ͷ��۷�=" + decNum + ", ���յ÷�=" + finalNum + "]";
	}
    public String runningRecordsToString(){
    	String string=this.name+"���ܲ���¼:";
    	for(RunningInfo r:this.runningInfos){
    		string=string+"\n"+r.toString();
    	}
    	return string;
    }
    

    
    
    
}
