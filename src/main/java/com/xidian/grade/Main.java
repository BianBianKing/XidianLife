package com.xidian.grade;


import org.apache.http.NoHttpResponseException;
import org.apache.http.util.TextUtils;

public class Main {

	public static UserInfo test(String username, String password) throws NoHttpResponseException {
	    System.out.println("Hello World");
        UserInfo userInfo = new UserInfo();
        userInfo.userName = username;
        userInfo.password = password;
        try {
        	// 0 before query everyone student's score u must use the init function manual
            HttpClientManager.init();

            // 1 step one to init httpclient get cookie
            boolean isGetCookieok = NetUtilies.getLoginWebCookie(userInfo);
            if (isGetCookieok){
                if(NetUtilies.doLoginAction(userInfo)){
                    if(NetUtilies.getGradeCookie(userInfo)){
                        // pase User Info
                        if(NetUtilies.getUserInfo(userInfo)){
                            if(NetUtilies.getSemesterInfo(userInfo)){
                                if(NetUtilies.getGradeList(userInfo)){
                                    if(NetUtilies.getFailGradeInfo(userInfo)){
                                        if(NetUtilies.getCurrentGradeInfo(userInfo)){
                                            // when come here should check whether userinfo is valid
                                            if(checkUserInfoValid(userInfo)){
                                                testOutPut(userInfo);
                                                return userInfo;
                                            }else {
                                                System.out.println("Get User Info Failed");
                                                return null;
                                            }
                                        }
                                    }

                                }

                            }
                        }

                    }
                }
            }
        }catch(NoHttpResponseException nhe){
        	throw nhe;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
        
		return null;
    }


    private static boolean checkUserInfoValid(UserInfo userInfo){

        if(TextUtils.isEmpty(userInfo.userName) || TextUtils.isEmpty(userInfo.password) ||
                TextUtils.isEmpty(userInfo.getUserDesc()) ||
                userInfo.getSemesterInfos() == null || userInfo.getSemesterInfos().size() <=0 ||
                userInfo.getSemesterGrades() == null || userInfo.getSemesterGrades().size() <= 0 ||
                userInfo.getCurrentGradeInfos() == null || userInfo.getCurrentGradeInfos().size() <= 0){
            return false;
        }

        return true;
    }


    private static void testOutPut(UserInfo userInfo){
        System.out.println("userAccount = " + userInfo.userName);
        System.out.println("userDesc = " + userInfo.getUserDesc());
        for(int i = 0; i < userInfo.getSemesterGrades().size(); i++){
            System.out.println(userInfo.getSemesterGrades().get(i).getSemesterName());
            for(int j = 0; j < userInfo.getSemesterGrades().get(i).getGradeInfos().size(); j++){
                System.out.println(userInfo.getSemesterGrades().get(i).getGradeInfos().get(j).toString());
            }
        }

        System.out.println("尚不及格科目：");

        for(int i = 0; i < userInfo.getFailGradeInfo().getFailGradeInfos().size(); i++){
            System.out.println(userInfo.getFailGradeInfo().getFailGradeInfos().get(i).toString());
        }

        System.out.println("本学期成绩列表：");
        for(int i = 0; i < userInfo.getCurrentGradeInfos().size(); i++){
            System.out.println(userInfo.getCurrentGradeInfos().get(i).toString());
        }

    }


}
