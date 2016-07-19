package com.xidian.util;

import java.text.DecimalFormat;

import com.xidian.exception.FlowParseException;

public class FlowUtil {
	private static final String[] unit = {"G","M","K","B"};
	public static double getGbView(String str){
		if(str.endsWith("G")){
			String number = str.replace("G", "");
			return Double.parseDouble(number);
		}
		else if(str.endsWith("M")){
			String number = str.replace("M", "");
			return Double.parseDouble(number)/1024;
		}
		else if(str.endsWith("K")){
			String number = str.replace("K", "");
			return Double.parseDouble(number)/1024/1024;
		}
		else if(str.endsWith("byte")){
			String number = str.replace("byte", "");
			return Double.parseDouble(number)/1024/1024/1024;
		}
		else{
			throw new RuntimeException(new FlowParseException());
		}
	}
	public static String getGoodViewByGb(double flow){
		DecimalFormat df = new DecimalFormat(".00");
		for(int i = 0; i < unit.length; i++){
			if(flow > 1){
				return df.format(flow)+unit[i];
			}
			else{
				flow = flow*1024;
			}
		}
		return null;
	}
	public static void main(String[] args){
		System.out.println(getGbView("2.3G"));
		System.out.println(getGbView("2.3K"));
		System.out.println(getGbView("2.3M"));
		System.out.println(getGbView("2.3byte"));
		
		System.out.println(getGoodViewByGb(getGbView("2.3G")));
		System.out.println(getGoodViewByGb(getGbView("2.3M")));
		System.out.println(getGoodViewByGb(getGbView("2.3K")));
		System.out.println(getGoodViewByGb(getGbView("2.3byte")));
	}
}
