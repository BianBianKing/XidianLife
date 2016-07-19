package com.xidian.newflow;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyt on 2015/7/27.
 */
public class UserInfo {

	
	private String cookiesString;
	private UserCookies userCookies;
	//ѧ��
	private String userName;
	//����
    private String password;
    //����
    private String name;
    private String code;
    private String csrf_token;
    public FlowInfo fInfo;
    public String userError;
    public String codeError="";
    
    public String imagePath;
    
    public FlowInfo getfInfo() {
		return fInfo;
	}


	public void setfInfo(FlowInfo fInfo) {
		this.fInfo = fInfo;
	}

    
    public String getCsrf_token() {
		return csrf_token;
	}


	public void setCsrf_token(String csrf_token) {
		this.csrf_token = csrf_token;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public UserInfo(String a,String b){
    	userName=a;
    	password=b;
    	this.fInfo=new FlowInfo();
    }
    
    
	public String getCookiesString() {
		return cookiesString;
	}
	public void setCookiesString(String cookiesString) {
		this.cookiesString = cookiesString;
		try {
			String[] cc=cookiesString.split(";");
		String s1=(cc[0].split("="))[1];
		String s2=(cc[2].split("="))[1];
		String s3=(cc[4].split("="))[1];
		userCookies=new UserCookies(s1,s2,s3);
		}catch(ArrayIndexOutOfBoundsException e){
			//e.printStackTrace();
		}
		
		
	}
	public UserCookies getUserCookies() {
		return userCookies;
	}
	public void setUserCookies(UserCookies userCookies) {
		this.userCookies = userCookies;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    public void printFlowInfo(){
    	System.out.println("�˺ţ�"+this.userName+"\n"+
    			"�Ʒ�����:"+this.fInfo.groupName+"\n"+
    			"��������:"+this.fInfo.usedFlow+"\n"+
    			"��������:"+this.fInfo.lastCountTime+"\n"+
    			"�ײ�������:"+this.fInfo.extraFlow+"\n");
    	for(int i=0;i<fInfo.flow2.size();i++){
    		FlowInfo.Flow2 ff=fInfo.flow2.get(i);
    		System.out.print("--------------------"+"\n"+
    			"�ײ�����:"+ff.name+"\n"+
    			"�ײ�������:"+ff.allFlow+"\n"+
    			"�ײ�ʣ������:"+ff.left+"\n"+
    			"�ײ���������:"+ff.used+"\n"+
    			"��ֹ����:"+ff.deadline+"\n");
    	}
    	
    }
    
 
    
	public class FlowInfo{
		public class Flow2{
			public String name="3G����ײ�";
			public String allFlow;
			public String left;
			public String used;
			public String deadline;
		}
		public String userId;
    	public String ipAddr;
    	public String webLoginTime;
    	
    	public String groupName="��У��ѧ������3G";
    	public String usedFlow;
    	public String lastCountTime;
    	public String extraFlow;
    	
    	public ArrayList<Flow2> flow2=new ArrayList<Flow2>();
    	 
    }
    
	class UserCookies{
		private String PHPSESSID;
		private String csrf;
		private String BIGipServerzyzfw;
		public UserCookies(){
			PHPSESSID=null;
			csrf=null;
			BIGipServerzyzfw=null;
		}
		public UserCookies(String phpid,String csrf1,String big){
			PHPSESSID=phpid;
			csrf=csrf1;
			BIGipServerzyzfw=big;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "PHPSESSID="+PHPSESSID+";"+"_csrf="+csrf+";"+"BIGipServerzyzfw.xidian.edu.cn="+BIGipServerzyzfw;
			
		}
	}
}
