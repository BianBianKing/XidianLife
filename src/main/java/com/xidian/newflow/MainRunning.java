package com.xidian.newflow;
import java.io.File;

import org.apache.http.impl.cookie.IgnoreSpec;




/**
 * Created by yyt on 2015/7/27.
 */
public class MainRunning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpClientManager.init();
		System.out.println("start");
		UserInfo user = new UserInfo("1503121697","301414");
		//UserInfo user = new UserInfo("13121375","lisai123");
		//UserInfo user = new UserInfo("1405122314","201724");
		if(user.getUserName().equals(""))
		{
			System.out.println("输入用户名");
		}
		//login
		boolean loginOk=false;
		int cc=0;
		do{
			cc++;
			if(cc>5)
			{
				System.out.println("login error!tyr late.");
				break;
			}
			if(HttpOperate.getLoginInfo(user)){
				loginOk=HttpOperate.loginFlowQuery(user);
				if(!loginOk&&!user.codeError.equals("")){
					System.out.println(user.userError);
					break;
				}
			}
			else{
				System.out.println("getLoginInfo error!!");
				break;
			}
			
		}while(!loginOk);
		if(loginOk&&HttpOperate.getFlowInfo(user)){
			user.printFlowInfo();
		}
		else{
			System.out.println("get FlowInfo error!");
		}
		
	

		
		System.out.println("end");
	}

}
