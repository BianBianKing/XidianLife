package com.xidian.flow;

import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;

@Service("main")
public class Main {

   // private static UserManager userManager;
   // private static boolean isOk = false;

    public static Result test(String username, String password) {
	// write your code here
        //System.out.println("Hello world");
//        try {
//            LocalOcr.trainData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    	boolean isOk = false;
        System.out.println(username);
        UserManager userManager = new UserManager();
        userManager.setUsername(username);
        userManager.setPassword(password);
        int count = 0;
        try {
        	while(!isOk){
                userManager.setBIGipServerzyzfw(null);
                userManager.setPHPSESSID(null);
                if(TextUtils.isEmpty(userManager.getBIGipServerzyzfw()) || TextUtils.isEmpty(userManager.getPHPSESSID())){
                    userManager.updateCookie();
                }
                System.out.println("has save cookie");
                //System.out.println("woshiyigeren");
                //if(userManager.parseCheckCode()){
                    System.out.println("has check code");
                    if(userManager.doLogin()){
                        System.out.println("Login ok");
                    	
                        Result result = userManager.parseFlowInfo();
                        isOk = true;
                        return result;
                    }
                    return null;
                //}
//                count++;
//                if(count > 3) {
//                	return null;
//                }
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                    
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
        
        return null;
    }
}
