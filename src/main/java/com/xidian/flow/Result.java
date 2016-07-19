package com.xidian.flow;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("result")
public class Result {
	private String usedString;
	private String remainderString;
	private String userinfo;
	
	public String getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}
	public String getUsedString() {
		return usedString;
	}
	public double getUsedDouble() {
		char[] temp = usedString.toCharArray();
		int i = 0;
		for(int k = 0; k < temp.length; ++k) {
			if (Character.isDigit(temp[k]) || temp[k] == '.' || temp[k] == 'M' || temp[k] == 'G' || temp[k] == 'K' || temp[k] == 'B') {
				temp[i++] = temp[k];
			}
		}
		String tempString;
		if (temp[i-2] == 'K') {
			tempString = new String(temp, 1, i-3);
		} else {
			tempString = new String(temp, 1, i-2);
		}
		double tempUserd = Double.parseDouble(tempString);
		if (temp[i-1] == 'G') {
			tempUserd *= 1024;
		} else if (temp[i-2] == 'K'){
			tempUserd /= 1024;
		}
		return tempUserd;
	}
	public void setUsedString(String usedString) {
		this.usedString = usedString;
	}
	public String getRemainderString() {
		return remainderString;
	}
	public double getRemainderDouble() {
		char[] temp = remainderString.toCharArray();
		int i = 0;
		double tempRemain;
		for(int k = 0; k < temp.length; ++k) {
			if (Character.isDigit(temp[k]) || temp[k] == '.' || temp[k] == 'M' || temp[k] == 'G') {
				temp[i++] = temp[k];
			}
			if(temp[k] == '-') {
				return 0;
			}
		}
		String tempString = new String(temp, 0, i-1);
		tempRemain = Double.parseDouble(tempString);
		if (temp[i-1] == 'G') {
			tempRemain *= 1024;
		}
		return tempRemain;
	}
	public void setRemainderString(String remainderString) {
		this.remainderString = remainderString;
	}
}
