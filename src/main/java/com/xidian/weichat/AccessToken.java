package com.xidian.weichat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 * 微信通用接口凭证
 * 
 * @author liufeng
 * @date 2013-08-08
 */

public class AccessToken {

	// 获取到的凭证

	private String token;

	// 凭证有效时间，单位：秒

	private int expiresIn;

	public String getToken() {

		return token;

	}

	public void setToken(String token) {

		this.token = token;

	}

	public int getExpiresIn() {

		return expiresIn;

	}

	public void setExpiresIn(int expiresIn) {

		this.expiresIn = expiresIn;

	}
	
	private String APP_ID;
	private String APPSECRET;
	
	public String getAccess_token() {

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="

				+ APP_ID + "&secret=" + APPSECRET;

		String accessToken = null;

		try {

			URL urlGet = new URL(url);

			HttpURLConnection http = (HttpURLConnection) urlGet

			.openConnection();

			http.setRequestMethod("GET"); // 必须是get方式请求

			http.setRequestProperty("Content-Type",

			"application/x-www-form-urlencoded");

			http.setDoOutput(true);

			http.setDoInput(true);

			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒

			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();

			InputStream is = http.getInputStream();

			int size = is.available();

			byte[] jsonBytes = new byte[size];

			is.read(jsonBytes);

			String message = new String(jsonBytes, "UTF-8");

			JSONObject demoJson = new JSONObject(message);

			accessToken = demoJson.getString("access_token");

			System.out.println(accessToken);

			is.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return accessToken;

	}

}