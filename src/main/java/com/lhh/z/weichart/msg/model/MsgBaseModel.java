package com.lhh.z.weichart.msg.model;

import com.lhh.z.weichart.msg.MsgTypeEnum;

public abstract class MsgBaseModel {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private MsgTypeEnum msgType;
	private String msgId;
	
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setMsgType(MsgTypeEnum msgType) {
		this.msgType = msgType;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getToUserName() {
		return toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public MsgTypeEnum getMsgType() {
		return msgType;
	}
	public String getMsgId() {
		return msgId;
	}
}
