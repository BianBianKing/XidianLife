package com.xidian.newflow;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Created by yyt on 2015/7/27.
 */
public class PictureOperate {
	public static double[][] training={{0.08109636,0.11882422,0.097718984,0.16471681,0.012037847,0.15010731,0.12571308,0.12345239,0.12633303},
			{0.094021074,0.1523235,0.10720465,0.025480336,0.15859744,0.12668568,0.034008253,0.15977877,0.14190029},
			{0.119355924,0.1325529,0.105123974,0.06283798,0.105190694,0.11346,0.14045481,0.11455601,0.1064677},
			{0.098846205,0.118350856,0.1072374,0.068111144,0.060573746,0.1398199,0.14643817,0.11368952,0.14693305},
			{0.067009665,0.14885916,0.13173823,0.12899652,0.12399581,0.1535224,0.011137693,0.08719594,0.14754453},
			{0.12064973,0.1061435,0.07945029,0.09429037,0.111821115,0.12468118,0.12422808,0.11509752,0.123638205},
			{0.07331369,0.10415137,0.09903542,0.150196,0.10524961,0.12104425,0.10826316,0.10418492,0.13456158},
			{0.13854086,0.15597662,0.15599127,0.0023965032,0.14475943,0.10486859,0.070073225,0.17323248,0.054161012},
			{0.090744406,0.11515948,0.092614226,0.10933248,0.106482685,0.11344299,0.1309479,0.10308235,0.13819347},
			{0.10449091,0.111691475,0.11890649,0.11189275,0.100265004,0.15143946,0.097423755,0.090955116,0.112935044}};
	private static int current=0;
	public static int[][] readPic2IntArray(String path){
		int[][] data;
		try {
			File file =new File(path);
			if(!file.exists())
				return null;
			BufferedImage bufferedImage = ImageIO.read(file);
			int h = bufferedImage.getHeight();
			int w = bufferedImage.getWidth();
			data=new int[h][w];
			int kind = bufferedImage.getType();
		    for (int x = 0; x < w; x++) {
				 for (int y = 0; y < h; y++) {
					   int argb = bufferedImage.getRGB(x, y);
					   int r = (argb >> 16) & 0xFF;
					   int g = (argb >> 8) & 0xFF;
					   int b = (argb >> 0) & 0xFF;
					   int grayPixel = (int) ((b * 29 + g * 150 + r * 77 + 128) >> 8);                
					   if(grayPixel>10)
						   grayPixel=255;
					    data[y][x] = grayPixel;
					   
					 //data[x][y]=(byte)bufferedImage.getData().getDataElements(x, y,BufferedImage.TYPE_BYTE_GRAY);
				 }
			    
			}

		    file.deleteOnExit();
			return data;  
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int cutPicture(int[][] data,String pathString){
		
		int[][] theData;int[][] theData1;int[][] theData2;
		int finished=1;
		//String pathString="C:\\training\\2-";
		String path;
		int beginW=0;
		while(finished<=4){
			theData=cutPicture(data,beginW);
			int wNew=theData[0].length;
			beginW=current+1;
			int hNew=theData.length;
			int cutX=wNew;
			int averageW=0;
			if(wNew>70){
				averageW=theData[0].length/4;
				cutX = cutPicture3(theData,averageW);			
			}else if(wNew>50){
				averageW=theData[0].length/3;
				cutX = cutPicture3(theData,averageW);	
			}else if(wNew>30){
				averageW=theData[0].length/2;
				cutX = cutPicture3(theData,averageW);		
			}
			beginW=beginW-(wNew-cutX);
			theData1=new int[hNew][cutX];	
			for(int x=0;x<cutX;x++){
				for(int y=0;y<hNew;y++){
					theData1[y][x]=theData[y][x];
				}
			}
			path=pathString+Integer.toString(finished++)+".png";
			writePNG(theData1,path);
			
		}
		
		
		
		return 4;
		
	}
	public static int cutPicture3(int[][] data,int w2){
		int w3=w2-5;
		int minBlack=9999;
		int cutW=-1;
		int sum=0;
		for(int i=w3;i<=w3+10;i++){
			sum=0;
			for(int j=0;j<data.length;j++){
				if(data[j][i]==0&&data[j][i+1]==0){
					sum++;
				}
			}
			if(sum<minBlack){
				minBlack=sum;
				cutW=i;
			}
		}
		if(minBlack>15)
			cutW=w2;
		return cutW+1;

	}
	public static int cutPicture2(int[][] data){
		//int[][] theData;
		int w=data[0].length;
		int w2=w/2;
		int w3=w2-5;
		int minBlack=9999;
		int cutW=-1;
		int sum=0;
		for(int i=w3;i<=w3+10;i++){
			sum=0;
			for(int j=0;j<data.length;j++){
				if(data[j][i]==0&&data[j][i+1]==0){
					sum++;
				}
			}
			if(sum<minBlack){
				minBlack=sum;
				cutW=i;
			}
		}
		if(minBlack>15)
			cutW=w2;
		return cutW+1;
	}
	public static int[][] cutPicture(int[][] data,int st){
		
		int[][] newData;
		int w=data[0].length;
		int h=data.length;
		int wST=-1;int wED=-1;
		int hST=-1;int hED=-1;
		int flag=0;int flag2=0;
		for(int i=st;i<w;i++){
			flag=0;flag2=0;
			for(int j=0;j<h;j++)
			{
				if(wST==-1&&data[j][i]!=255){
					wST=i;
					flag=100;
					break;
				}
				if(data[j][i]!=255)
					flag=1;

			}
			if(wST!=-1&&flag==0){
				wED=i-1;
				current=wED;
				break;
			}
				
		}
		//newData=new int[wED-wST+1][h];
		for(int j=0;j<h;j++){
			flag=0;
			for(int i=wST;i<=wED;i++)
			{
				if(hST==-1&&data[j][i]!=255){
					hST=j;
					flag=1;
					break;
				}
				if(data[j][i]!=255)
					flag=1;
			}
			if(hST!=-1&&flag==0){
				hED=j-1;
				break;
			}
				
		}
		newData = new int[hED-hST+1][wED-wST+1];
		for(int i=wST;i<=wED;i++){
			for(int j=hST;j<=hED;j++){
				newData[j-hST][i-wST]=data[j][i];
			}
		}
		
		return newData;
		
	}
	public static void writePNG(int theData[][],String path){
		BufferedImage grayBufferedImage = new BufferedImage(theData[0].length, theData.length, BufferedImage.TYPE_3BYTE_BGR);
		for(int x=0;x<theData[0].length;x++){
			for(int y=0;y<theData.length;y++)
			{
				if(theData[y][x]==255)
					grayBufferedImage.setRGB(x, y,Color.WHITE.getRGB());
				else 
					grayBufferedImage.setRGB(x, y,Color.BLACK.getRGB());
			}
		}
		try {
			ImageIO.write(grayBufferedImage, "png", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static float[] changeDataToInt9(int[][] data){
		int[] res=new int[9];
		float[] res2=new float[9];
		int all=0;
		int w = data[0].length;
		int h =data.length;
		int w2=w/3;
		int h2=h/3;
		int[] dxst={0,1,2,0,1,2,0,1,2};
		int[] dyst={0,0,0,1,1,1,2,2,2};
		int[] dxed={1,2,3,1,2,3,1,2,3};
		int[] dyed={1,1,1,2,2,2,3,3,3};
		int k=0;
		int stw;int sth;int edw;int edh;
		while(k<9){
			res[k]=0;
			stw=w2*dxst[k];
			sth=h2*dyst[k];
			edw=w2*dxed[k];
			edh=h2*dyed[k];
			for(int i = stw;i<edw;i++){
				for(int j = sth;j<edh;j++){
					if(data[j][i]==0)
						res[k]=res[k]+1;
				}
			}
			all=all+res[k];

			k++;
		}
		for(int i=0;i<9;i++)
			res2[i]=(float)1.0*res[i]/all;
		
		return res2;
		
		
	}
	public static float changeDataToWB(int[][] data){
		int w = data[0].length;
		int h =data.length;
		int black=0;
		int allB=0;
		for(int i = 0;i<w;i++){
			for(int j = 0;j<h;j++){
				allB++;
				if(data[j][i]==0)
					black=black+1;
			}
		}
		float x=(float)1.0*black/allB;
		return x;
	}

	
	public static double compare2Float(float[] f,double[] dd){
		double res=0;
		for(int i=0;i<9;i++){
			res=res+Math.abs(f[i]-dd[i]);
		}
		return res;
	}
	
}
