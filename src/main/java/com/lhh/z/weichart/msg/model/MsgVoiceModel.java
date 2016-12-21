package com.lhh.z.weichart.msg.model;

import com.lhh.z.weichart.msg.MsgTypeEnum;

public class MsgVoiceModel extends MsgBaseModel {
	public MsgVoiceModel() {
		super.setMsgType(MsgTypeEnum.voice);
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	private String mediaId;
	private String format;
	public String getMediaId() {
		return mediaId;
	}
	public String getFormat() {
		return format;
	}
}
