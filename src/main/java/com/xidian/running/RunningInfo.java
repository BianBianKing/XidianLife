package com.xidian.running;

import java.io.Serializable;

public class RunningInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8152101454297275761L;
	//日期 	时段 	里程 	平均速度 	是否有效 	备注
      private String num;
	  private String date;
	  private String time;
	  private String distance;
	  private String speed;
	  private String ok;
	  private String info;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String isOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getOk() {
		return ok;
	}
	@Override
	public String toString() {
		return "[序号=" + num +",日期=" + date + ", 时段=" + time + ", 里程=" + distance + ", 平均速度=" + speed + ", 是否有效="
				+ ok + ", 备注=" + info + "]";
	}
	
	  
	  
}
