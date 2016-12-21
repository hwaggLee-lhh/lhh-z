package com.lhh.z.weichart;

import java.util.UUID;

public class JsApiConfig {
	public static JsApiConfig getInstance(String url) {
		JsApiConfig instance = new JsApiConfig();
		instance.appId = WeiChartConstants.APP_ID;
		instance.timestamp = System.currentTimeMillis()/1000+"";
		instance.nonceStr = UUID.randomUUID().toString();
		StringBuffer sb = new StringBuffer();
		sb.append("jsapi_ticket=");
		sb.append(JsApiTicket.getInstance().getTicket());
		sb.append("&noncestr=");
		sb.append(instance.nonceStr);
		sb.append("&timestamp=");
		sb.append(instance.timestamp);
		sb.append("&url=");
		sb.append(url);
		instance.signature = Signature.sha1(sb.toString());
		return instance;
	}
	private JsApiConfig() {
		
	}
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;
	public String getAppId() {
		return appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public static void main(String[] args) {
		System.out.println(JsApiConfig.getInstance("www.baidu.com"));
	}
}
