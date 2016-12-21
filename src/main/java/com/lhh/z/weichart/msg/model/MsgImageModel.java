package com.lhh.z.weichart.msg.model;

import com.lhh.z.weichart.msg.MsgTypeEnum;

public class MsgImageModel extends MsgBaseModel {
	public MsgImageModel() {
		super.setMsgType(MsgTypeEnum.image);
	}
	private String picUrl;
	private String mediaId;
	
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public String getMediaId() {
		return mediaId;
	}
}
