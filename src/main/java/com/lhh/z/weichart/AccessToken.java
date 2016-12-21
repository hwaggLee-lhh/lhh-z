package com.lhh.z.weichart;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.lhh.connection.url.LhhUtilsHttp;

public class AccessToken {
	private static AccessToken instance;
	public synchronized static AccessToken getInstance() {
		if(instance==null) instance = new AccessToken();
		return instance;
	}
	private String url = null;
	private String accessToken = null;
	private long expiresTime = 0l;
	private AccessToken() {
		StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/cgi-bin/token");
		sb.append("?grant_type=client_credential");
		sb.append("&appid=");
		sb.append(WeiChartConstants.APP_ID);
		sb.append("&secret=");
		sb.append(WeiChartConstants.APP_SECRET);
		url = sb.toString();
	}
	public String getToken(){
		if(expiresTime>System.currentTimeMillis() && StringUtils.isNotBlank(accessToken)) {
			return accessToken;
		}
		String cacheToken = getCacheToken();
		if(StringUtils.isNotBlank(cacheToken)) {
			return cacheToken;
		}
		String res = LhhUtilsHttp.execute(url);
		JSONObject resObj = JSONObject.fromObject(res);
		String tokenNew = resObj.getString("access_token");
		accessToken = tokenNew;
		//超时时间为２小时，即7200秒,7000秒小于２小时
		expiresTime = System.currentTimeMillis()+7000*1000;
		writeCacheToken();
		return tokenNew;
	}
	private void writeCacheToken() {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("access_token", accessToken);
			jsonObj.put("expires_time", expiresTime);
			FileReadAndWrite.writeFile(WeiChartConstants.AccessTokenFileName, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getCacheToken() {
		String cacheToken = FileReadAndWrite.readFile(WeiChartConstants.AccessTokenFileName);
		if(StringUtils.isBlank(cacheToken)) {
			return null;
		}
		JSONObject jsonObj = JSONObject.fromObject(cacheToken);
		if(jsonObj==null) return null;
		String access_token = jsonObj.getString("access_token");
		if(StringUtils.isBlank(access_token)) return null;
		long expiresTime = jsonObj.getLong("expires_time");
		if(expiresTime<System.currentTimeMillis()) return null;
		return access_token;
	}
	public static void main(String[] args) {
		String token = AccessToken.getInstance().getToken();
		System.out.println(token);
	}
}
