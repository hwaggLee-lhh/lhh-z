package com.lhh.z.weichart.msg.model;

import com.lhh.z.weichart.msg.MsgTypeEnum;

public class MsgLocationModel extends MsgBaseModel {
	public MsgLocationModel() {
		super.setMsgType(MsgTypeEnum.location);
	}
	private String location_x;
	private String location_y;
	private String label;
	
	public void setLocation_x(String location_x) {
		this.location_x = location_x;
	}
	public void setLocation_y(String location_y) {
		this.location_y = location_y;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLocation_x() {
		return location_x;
	}
	public String getLocation_y() {
		return location_y;
	}
	public String getLabel() {
		return label;
	}
}
