package com.xidian.timetable;

import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Element;

import com.xidian.mastergrade.HttpClientManager;

public class Main {

	public static UserInfo test(String username, String password){
		System.out.println("用户名:"+username);
		System.out.println("密码"+password);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(username);
		userInfo.setPassword(password);
		HttpClientManager.init();
		if(NetUtility.getLoginWebCookie(userInfo)){
            if(NetUtility.doLoginAction(userInfo)){
            	if(NetUtility.getGradeWebCookie(userInfo))
            		if(NetUtility.getCourseInfo(userInfo))
            			return userInfo;
            }
        }
		return null;
	}
    public static void main(String[] args) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("1503121697");
        userInfo.setPassword("301414");

        // this function must be called before everyone account manual
        HttpClientManager.init();

//        if(NetUtility.getLoginWebCookie(userInfo)){
//            if(NetUtility.doLoginAction(userInfo)){
//                if(NetUtility.getGradeWebCookie(userInfo)){
//                    if(NetUtility.getGradeInfo(userInfo)){
//                        if(chechUserInfoValid(userInfo)){
//                            testOutPut(userInfo);
//                            return;
//                        }
//                    }
//                }
//            }
//        }
        if(NetUtility.getLoginWebCookie(userInfo)){
            if(NetUtility.doLoginAction(userInfo)){
            	if(NetUtility.getGradeWebCookie(userInfo))
            		if(NetUtility.getCourseInfo(userInfo))
            			printUserCourse(userInfo);
            }
            
                           
        }
       // System.out.println("Error code = " + userInfo.getErrorCode());




    }

    public static void printUserCourse(UserInfo user){
		for(int i=1;i<=5;i++){			
			System.out.println("星期"+Integer.toString(i));
			for(int j=1;j<=9;j++){
				
				System.out.println(Integer.toString(j)+":"+user.getMyCourse()[i][j]);		
			}
			
		}
    }
    
    
    /**
     *
     * This function only chech whether the result userInfo is valid
     * you also can check the userInfo.errocode to judge the error condition
     *
     * */
    private static boolean chechUserInfoValid(UserInfo userInfo){


        if(TextUtils.isEmpty(userInfo.getUserName()) || TextUtils.isEmpty(userInfo.getPassword()) ||
                userInfo.getGradeInfo() == null || userInfo.getGradeInfo().getCourseInfos() == null ||
                userInfo.getGradeInfo().getCourseInfos().size() == 0){
            return false;
        }else {
            return true;
        }
    }


    private static void testOutPut(UserInfo userInfo){
        System.out.println(userInfo.toString());
        System.out.println(userInfo.getGradeInfo().getLowestTotalCredit());
        System.out.println(userInfo.getGradeInfo().getLowestDegreeCredit());
        System.out.println(userInfo.getGradeInfo().getHadCredit());
        System.out.println(userInfo.getGradeInfo().getHadDegreeCredit());
        System.out.println(userInfo.getGradeInfo().getAverageScore());

        for(int i = 0; i < userInfo.getGradeInfo().getCourseInfos().size(); i++){
            System.out.println(userInfo.getGradeInfo().getCourseInfos().get(i).toString());
        }

    }

}
