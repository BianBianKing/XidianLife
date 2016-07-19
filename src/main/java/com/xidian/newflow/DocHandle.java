package com.xidian.newflow;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.cookie.IgnoreSpec;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 * Created by yyt on 2015/7/27.
 */

public class DocHandle {
	public static void getErrorInfo(Document document,UserInfo user){
		Elements elements=document.getElementsByClass("error-summary");
		Elements es=elements.get(0).select("li");
		//StringBuilder sb=new StringBuilder();
		int i=0;
		for (Element element : es) {
			i++;
			if(i==1)
				user.userError=element.text();
			if(i==2)
				user.codeError=element.text();
			
		}
		
	}
	public static void getFlowInfo(Document document,UserInfo user){
		//user.setfInfo(new UserInfo.FlowInfo());
		Elements es=document.select("tr");
		int i =0;
		//int cTr=0;
		int ccc=0;
		int gogogo=0;
		String [] temp=new String [6];
		for (Element element : es) {
			int bb=0;
			//cTr++;
			ccc++;
			if(ccc>=15)break;
			i=0;
			Elements tr=element.select("td");
			if(tr.size()==0){
				//cTr--;
				continue;
				}
			for (Element td : tr) {
				temp[i]=td.text();
				i++;		
			}
			
			
			if((gogogo!=1)&&((temp[0].indexOf("G")>-1))){
				gogogo=1;
				user.fInfo.groupName=temp[0];
				user.fInfo.usedFlow=temp[1];
				user.fInfo.lastCountTime=temp[2];
				user.fInfo.extraFlow=temp[3];
				continue;
			}

			if(gogogo==1&&(temp[0].indexOf("G")>-1)){
				UserInfo.FlowInfo.Flow2 ff=user.fInfo.new Flow2();
				ff.name=temp[0];
				ff.allFlow=temp[1];
				ff.left=temp[2];
				ff.used=temp[3];
				ff.deadline=temp[4];
				user.fInfo.flow2.add(ff);
			}
		}
	}
	public static void getCsrfToken(Document document,UserInfo user){
		  Elements es=document.select("meta");
          for (Element element : es) {
			if(element.attr("name").equals("csrf-token"))
			{
				user.setCsrf_token(element.attr("content"));
				return;
			}
		  }
          System.out.print("token error");
	}
	
	public static void getVerifyCode(Document document,UserInfo user){
		Element eee  = document.getElementById(NetConstans.VERIFYCODEID);
		String url = NetConstans.LOGIN_URL+eee.attr("src");
		user.imagePath=eee.attr("src").split("=")[1];
		user.imagePath=user.imagePath;
		//System.out.println(url);
		ImageOP.downloadImageByURL(url,user);
		
		int[][] data=PictureOperate.readPic2IntArray(user.imagePath+".png");
		File f1 = new File(user.imagePath+".png");
		if(f1.exists())
			f1.deleteOnExit();
		String newPath2=user.imagePath+"-";
		PictureOperate.cutPicture(data,newPath2);
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<4;i++){
			String newPath3=user.imagePath+"-"
					+Integer.toString(i+1)+".png";
			data = PictureOperate.readPic2IntArray(newPath3);
			float[] f=PictureOperate.changeDataToInt9(data);
			double res;
			double minRes=9999;
			int val=-1;
			//0..9 = 10numbers
			for(int j =0;j<10;j++){
				res=0;
				//9 blocks
				for(int k =0;k<9;k++){
					res=res+Math.abs(f[k]-PictureOperate.training[j][k]);
				}
				if(res<minRes){
					minRes=res;
					val=j;
				}
				
			}
			sb.append(Integer.toString(val));
		}
		
		//String s=HttpOperate.recognizeCodeByORCKingWebsite(url);
		user.setCode(sb.toString());
		System.out.println(sb.toString());

		for(int i=1;i<=4;i++){
			File ff=new File(user.imagePath+"-"+Integer.toString(i)+".png");
			ff.delete();
		}
		File fx = new File(user.imagePath+".png");
		if(fx.exists())
		{  
			if(!fx.delete())
			{

			    System.gc();

			    fx.delete();

			}
		}
		
	}
	
	
	
	//get user informations
	public static void getUserInfo(Document document,UserInfo user){
		Elements labels  = document.select("label");
		Elements tables  = document.select("table.table-striped");
		
//		user.setName(labels.get(1).text());
//		user.setNumber(labels.get(2).text());
//		user.setSex(labels.get(3).text());
//		int i = 0;
//		for(Element e:tables){
//			i++;
//			Elements tds=e.select("td");
//			if(i==1){
//				user.setDistanceNum(tds.get(1).text());
//				user.setSpeed(tds.get(3).text());
//				user.setCount(tds.get(5).text());
//			}else if(i==2){
//				user.setGroup(tds.get(1).text());
//				user.setMinSpeed(tds.get(3).text());
//				user.setMaxSpeed(tds.get(5).text());
//			}else if(i==3){
//				user.setAddNum(tds.get(1).text());
//				user.setDecNum(tds.get(3).text());
//				user.setFinalNum(tds.get(5).text());
//			}
//
//		}
//		System.out.println(labels.toString());
//		System.out.println(tables.toString());
		//System.out.println(user.toString());
	}
	
	
	//get running records 
	public static void getRunningList(Document document,UserInfo user){
		Elements trs  = document.select("tr");
//		List<RunningInfo> rInfo = new ArrayList<RunningInfo>();
//		int i=0;
//		for(Element e:trs){
//			if(i==0){
//				i++;
//				continue;
//			}
//			Elements tds=e.select("td");
//			RunningInfo r = new RunningInfo();
//			for(int j=0;j<tds.size();j++){
//				
//				switch (j) {
//					case 0:
//						r.setNum(tds.get(j).text());
//						//System.out.println(tds.get(j).text());
//						break;
//					case 1:
//						r.setDate(tds.get(j).text());
//						//System.out.println(tds.get(j).text());
//						break;
//					case 2:
//						r.setTime(tds.get(j).text());
//						break;
//					case 3:
//						r.setDistance(tds.get(j).text());
//						break;
//					case 4:
//						r.setSpeed(tds.get(j).text());
//						break;
//					case 5:
//						int xx = tds.get(j).select("span.glyphicon-ok").size();
//						//System.out.println(tds.get(j).select("span.glyphicon-ok").toString());
//						if(xx>0)
//							r.setOk("ok");
//						else
//							r.setOk("false");
//						break;
//					case 6:
//						r.setInfo(tds.get(j).text());
//						break;
//					default:
//						break;
//				}
//			}
//			rInfo.add(r);
//		}
//		user.setRunningInfos(rInfo);
//		//System.out.println(user.getRunningInfos().toString());
	}
}
