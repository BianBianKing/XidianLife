package com.xidian.util;

public class QuerySecret {
	public static String addSecret(String before) {
		char[] temp = before.toCharArray();
		for(int i = 0; i < temp.length; ++i) {
			temp[i] += 41;
		}
		for(int i = 1; i < temp.length; i+=2) {
			char a = temp[i];
			temp[i] = temp[i-1];
			temp[i-1] = a;
		}
		
		return new String(temp);
	}
	public static String deleteSecret(String after) {
		char[] temp = after.toCharArray();
		for(int i = 0; i < temp.length; ++i) {
			temp[i] -= 41;
		}
		for(int i = 1; i < temp.length; i+=2) {
			char a = temp[i];
			temp[i] = temp[i-1];
			temp[i-1] = a;
		}
		return new String(temp);
	}
	
//	public static void main(String[] args) {
//		System.out.println(QuerySecret.addSecret("1403121624"));
//		System.out.println(QuerySecret.deleteSecret(QuerySecret.addSecret("1403121624")));
//	}
}
