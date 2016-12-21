package com.lhh.z.weichart.msg.model;

import com.lhh.z.weichart.msg.MsgTypeEnum;

public class MsgTextModel extends MsgBaseModel {
	public MsgTextModel() {
		super.setMsgType(MsgTypeEnum.text);
	}
	private String content;
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
