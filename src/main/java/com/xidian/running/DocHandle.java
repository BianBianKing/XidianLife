package com.xidian.running;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DocHandle {
	
	//get user informations
	public static void getUserInfo(Document document,RunningUserInfo user){
		Elements labels  = document.select("label");
		Elements tables  = document.select("table.table-striped");
		
		user.setName(labels.get(1).text());
		user.setNumber(labels.get(2).text());
		user.setSex(labels.get(3).text());
		int i = 0;
		for(Element e:tables){
			i++;
			Elements tds=e.select("td");
			if(i==1){
				user.setDistanceNum(tds.get(1).text());
				user.setSpeed(tds.get(3).text());
				user.setCount(tds.get(5).text());
			}else if(i==2){
				user.setGroup(tds.get(1).text());
				user.setMinSpeed(tds.get(3).text());
				user.setMaxSpeed(tds.get(5).text());
			}else if(i==3){
				user.setAddNum(tds.get(1).text());
				user.setDecNum(tds.get(3).text());
				user.setFinalNum(tds.get(5).text());
			}

		}
//		System.out.println(labels.toString());
//		System.out.println(tables.toString());
		//System.out.println(user.toString());
	}
	
	
	//get running records 
	public static void getRunningList(Document document,RunningUserInfo user){
		Elements trs  = document.select("tr");
		List<RunningInfo> rInfo = new ArrayList<RunningInfo>();
		int i=0;
		for(Element e:trs){
			if(i==0){
				i++;
				continue;
			}
			Elements tds=e.select("td");
			RunningInfo r = new RunningInfo();
			for(int j=0;j<tds.size();j++){
				
				switch (j) {
					case 0:
						r.setNum(tds.get(j).text());
						//System.out.println(tds.get(j).text());
						break;
					case 1:
						r.setDate(tds.get(j).text());
						//System.out.println(tds.get(j).text());
						break;
					case 2:
						r.setTime(tds.get(j).text());
						break;
					case 3:
						r.setDistance(tds.get(j).text());
						break;
					case 4:
						r.setSpeed(tds.get(j).text());
						break;
					case 5:
						int xx = tds.get(j).select("span.glyphicon-ok").size();
						//System.out.println(tds.get(j).select("span.glyphicon-ok").toString());
						if(xx>0)
							r.setOk("ok");
						else
							r.setOk("false");
						break;
					case 6:
						r.setInfo(tds.get(j).text());
						break;
					default:
						break;
				}
			}
			rInfo.add(r);
		}
		user.setRunningInfos(rInfo);
		//System.out.println(user.getRunningInfos().toString());
	}
}
