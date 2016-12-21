package com.lhh.z.weichart.msg;

public enum MsgTypeEnum {
	text("text"),
	image("image"),
	voice("voice"),
	video("video"),
	shortvideo("shortvideo"),
	location("location"),
	link("link"),
	news("news");
	private String value;
	private MsgTypeEnum(String msgType) {
		this.value = msgType;
	}
	public static MsgTypeEnum parse(String msgType) {
		for (MsgTypeEnum msg : MsgTypeEnum.values()) {
			if(msg.value.equals(msgType)) return msg;
		}
		return null;
	}
	public String getValue() {
		return value;
	}
}
