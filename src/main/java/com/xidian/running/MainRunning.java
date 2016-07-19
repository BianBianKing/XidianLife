package com.xidian.running;



public class MainRunning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpClientManager.init();
		System.out.println("start");
		
		
		RunningUserInfo user = new RunningUserInfo("14150110002","14150110002");
		//login
		if(HttpOperate.doLoginAction(user)){
			//get user informations
			if(HttpOperate.getLoginInfo(user))
				System.out.println(user.toString());
			else
				System.out.println("can't get user's informations!");
			// get user running records
			if(HttpOperate.getRunningList(user))
				System.out.println(user.runningRecordsToString());
			else
				System.out.println("can't get user's running records!");
		}
		else{
			System.out.println("can't connect successfully!");
		}
		
		
		
		System.out.println("end");
	}
	
	public static RunningUserInfo test(String username, String password){
		HttpClientManager.init();
		System.out.println("start");
		
		RunningUserInfo user = new RunningUserInfo(username, password);
		//login
		if(HttpOperate.doLoginAction(user)){
			//get user informations
			if(!HttpOperate.getLoginInfo(user))
				return null;
			// get user running records
			HttpOperate.getRunningList(user);
			//System.out.println(user.runningRecordsToString());
		}
		else
			return null;
		return user;
	}
}
