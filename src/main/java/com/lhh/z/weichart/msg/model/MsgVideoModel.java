package com.lhh.z.weichart.msg.model;

import com.lhh.z.weichart.msg.MsgTypeEnum;

public class MsgVideoModel extends MsgBaseModel {
	public MsgVideoModel() {
		super.setMsgType(MsgTypeEnum.video);
	}	

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	private String mediaId;
	private String thumbMediaId;
	public String getMediaId() {
		return mediaId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
}
