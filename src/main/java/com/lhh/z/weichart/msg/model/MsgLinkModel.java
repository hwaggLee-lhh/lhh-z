package com.lhh.z.weichart.msg.model;

import com.lhh.z.weichart.msg.MsgTypeEnum;

public class MsgLinkModel extends MsgBaseModel {
	public MsgLinkModel() {
		super.setMsgType(MsgTypeEnum.link);
	}
	private String title;
	private String description;
	private String url;
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getUrl() {
		return url;
	}
}
