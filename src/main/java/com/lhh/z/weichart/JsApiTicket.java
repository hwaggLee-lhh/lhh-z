package com.lhh.z.weichart;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.lhh.connection.url.LhhUtilsHttp;

public class JsApiTicket {

	private static JsApiTicket instance;
	public synchronized static JsApiTicket getInstance() {
		if(instance==null) instance = new JsApiTicket();
		return instance;
	}
	private String ticket = null;
	private long expiresTime = 0l;
	private JsApiTicket() {
	}
	public String getTicket(){
		if(expiresTime>System.currentTimeMillis() && StringUtils.isNotBlank(ticket)) {
			return ticket;
		}
		String cacheToken = getCacheTicket();
		if(StringUtils.isNotBlank(cacheToken)) {
			return cacheToken;
		}
		StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/cgi-bin/ticket/getticket");
		sb.append("?access_token=");
		sb.append(AccessToken.getInstance().getToken());
		sb.append("&type=jsapi");
		String url = sb.toString();
		String res = LhhUtilsHttp.execute(url);
		JSONObject resObj = JSONObject.fromObject(res);
		if(!resObj.containsKey("ticket") || StringUtils.isBlank(resObj.getString("ticket"))) {
			return null;
		}
		String ticketNew = resObj.getString("ticket");
		ticket = ticketNew;
		//超时时间为２小时，即7200秒,7000秒小于２小时
		expiresTime = System.currentTimeMillis()+7000*1000;
		writeCacheTicket();
		return ticket;
	}
	private void writeCacheTicket() {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("ticket", ticket);
			jsonObj.put("expires_time", expiresTime);
			FileReadAndWrite.writeFile(WeiChartConstants.TicketFileName, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getCacheTicket() {
		String cacheToken = FileReadAndWrite.readFile(WeiChartConstants.TicketFileName);
		if(StringUtils.isBlank(cacheToken)) {
			return null;
		}
		JSONObject jsonObj = JSONObject.fromObject(cacheToken);
		if(jsonObj==null) return null;
		String access_token = jsonObj.getString("ticket");
		if(StringUtils.isBlank(access_token)) return null;
		long expiresTime = jsonObj.getLong("expires_time");
		if(expiresTime<System.currentTimeMillis()) return null;
		return access_token;
	}
	public static void main(String[] args) {
		String token = JsApiTicket.getInstance().getTicket();
		System.out.println(token);
	}

}
