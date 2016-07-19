package com.xidian.newflow;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.methods.HttpUriRequest;

import com.sun.jna.platform.win32.Netapi32Util.User;
import com.sun.jna.platform.win32.WinDef.UCHAR;
/**
 * Created by yyt on 2015/7/27.
 */
public class ImageOP {
	public static void downloadImageByURL(String s,UserInfo user){
		URL url;
		try {
			url = new URL(s);
			//HttpURLConnection uc= (HttpURLConnection)url.openConnection(); 
			URLConnection   uc   =   url.openConnection(); 
			uc.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			uc.setRequestProperty("accept", "*/*");  
            
			uc.setRequestProperty("connection", "Keep-Alive");  
  
			uc.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
			
			uc.setRequestProperty("Cookie",user.getUserCookies().toString());
			uc.connect();
			File   file   =   new   File(user.imagePath+".png"); 
			FileOutputStream   out   =   new   FileOutputStream(file); 
			int   i=0; 
			InputStream   is   =   uc.getInputStream(); 
			while   ((i=is.read())!=-1)   { 
				out.write(i); 
			} 
			is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
