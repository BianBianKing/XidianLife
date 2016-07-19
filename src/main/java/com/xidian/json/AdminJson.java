package com.xidian.json;

public class AdminJson {
	private String status;
	private String info;
	private String url;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public AdminJson(String status, String info, String url) {
		super();
		this.status = status;
		this.info = info;
		this.url = url;
	}
	public AdminJson(String status){
		this.status = status;
	}
}
