package com.xidian.timetable;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class DocOperate {

	public static void getStuCourse(Document doc,UserInfo user){
		String s="";
		for(int i=1;i<=5;i++){
			for(int j=1;j<=9;j++){
				s=Integer.toString(j)+","+Integer.toString(i);
				Element e = doc.getElementById(s);

				if(e!=null){
					//System.out.println(e.toString());
					user.getMyCourse()[i][j]=e.text();
				}
				else {
					user.getMyCourse()[i][j]="";
				}
			
				
			}
		}
	}
	
	
}
