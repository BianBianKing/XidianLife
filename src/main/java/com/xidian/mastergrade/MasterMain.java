package com.xidian.mastergrade;

import org.apache.http.NoHttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.TextUtils;

public class MasterMain {


    	public static MasterUserInfo test(String username, String password) {
            MasterUserInfo userInfo = new MasterUserInfo();
//            userInfo.setUserName("1405122314");
//            userInfo.setPassword("112030");
            userInfo.setUserName(username);
            userInfo.setPassword(password);

        // this function must be called before everyone account manual
        HttpClientManager.init();

        if(NetUtility.getLoginWebCookie(userInfo)){
            if(NetUtility.doLoginAction(userInfo)){
                if(NetUtility.getGradeWebCookie(userInfo)){
                    if(NetUtility.getGradeInfo(userInfo)){
                        if(chechUserInfoValid(userInfo)){
                            testOutPut(userInfo);
                            return userInfo;
                        }
                    }
                }
            }
        }

        System.out.println("Error code = " + userInfo.getErrorCode());
		return userInfo;




    }


    /**
     *
     * This function only chech whether the result userInfo is valid
     * you also can check the userInfo.errocode to judge the error condition
     *
     * */
    private static boolean chechUserInfoValid(MasterUserInfo userInfo){


        if(TextUtils.isEmpty(userInfo.getUserName()) || TextUtils.isEmpty(userInfo.getPassword()) ||
                userInfo.getGradeInfo() == null || userInfo.getGradeInfo().getCourseInfos() == null ||
                userInfo.getGradeInfo().getCourseInfos().size() == 0){
            return false;
        }else {
            return true;
        }
    }


    private static void testOutPut(MasterUserInfo userInfo){
        System.out.println(userInfo.toString());
        System.out.println("棰濆畾鎬诲鍒� : " + userInfo.getGradeInfo().getLowestTotalCredit());
        System.out.println("棰濆畾瀛︿綅鍒� : " + userInfo.getGradeInfo().getLowestDegreeCredit());
        System.out.println("宸插畬鎴愬鍒� : " + userInfo.getGradeInfo().getHadCredit());
        System.out.println("宸插畬鎴愬浣嶈瀛﹀垎 : " + userInfo.getGradeInfo().getHadDegreeCredit());
        System.out.println("瀛︿綅璇惧姞鏉冨钩鍧囧垎 : " + userInfo.getGradeInfo().getAverageScore());

        for(int i = 0; i < userInfo.getGradeInfo().getCourseInfos().size(); i++){
            System.out.println(userInfo.getGradeInfo().getCourseInfos().get(i).toString());
        }

    }

}
