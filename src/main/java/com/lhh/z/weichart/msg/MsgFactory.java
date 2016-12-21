package com.lhh.z.weichart.msg;


import org.dom4j.Document;
import org.dom4j.Element;

import com.lhh.z.weichart.msg.model.MsgBaseModel;
import com.lhh.z.weichart.msg.model.MsgImageModel;
import com.lhh.z.weichart.msg.model.MsgLinkModel;
import com.lhh.z.weichart.msg.model.MsgLocationModel;
import com.lhh.z.weichart.msg.model.MsgTextModel;
import com.lhh.z.weichart.msg.model.MsgVideoModel;
import com.lhh.z.weichart.msg.model.MsgVoiceModel;

public class MsgFactory {
	private static void convert(Document document, MsgBaseModel mbm) {
		Element root = document.getRootElement();
		mbm.setToUserName(root.elementText("ToUserName"));
		mbm.setFromUserName(root.elementText("FromUserName"));
		mbm.setCreateTime(root.elementTextTrim("CreateTime"));
		mbm.setMsgType(MsgTypeEnum.parse(root.elementTextTrim("MsgType")));
		mbm.setMsgId(root.elementTextTrim("MsgId"));
	}
	public static MsgTextModel buildText(Document document) {
		MsgTextModel model = new MsgTextModel();
		convert(document, model);
		Element root = document.getRootElement();
		model.setContent(root.elementTextTrim("Content"));
		return model;
	}
	public static MsgImageModel BuildImage(Document document) {
		MsgImageModel model = new MsgImageModel();
		convert(document, model);
		Element root = document.getRootElement();
		model.setPicUrl(root.elementTextTrim("PicUrl"));
		model.setMediaId(root.elementTextTrim("MediaId"));
		return model;
	}
	public static MsgLinkModel buildLink(Document document) {

		MsgLinkModel model = new MsgLinkModel();
		convert(document, model);
		Element root = document.getRootElement();
		model.setTitle(root.elementTextTrim("Title"));
		model.setDescription(root.elementTextTrim("Description"));
		model.setUrl(root.elementTextTrim("Url"));
		return model;
	}
	public static MsgLocationModel buildLocation(Document document) {
		MsgLocationModel model = new MsgLocationModel();
		convert(document, model);
		Element root = document.getRootElement();
		model.setLocation_x(root.elementTextTrim("Location_X"));
		model.setLocation_y(root.elementTextTrim("Location_Y"));
		model.setLabel(root.elementTextTrim("Label"));
		return model;
	}
	public static MsgVideoModel buildVideo(Document document) {
		MsgVideoModel model = new MsgVideoModel();
		convert(document, model);
		Element root = document.getRootElement();
		model.setMediaId(root.elementTextTrim("MediaId"));
		model.setThumbMediaId(root.elementTextTrim("ThumbMediaId"));
		return model;
	}
	public static MsgVoiceModel buildVoice(Document document) {
		MsgVoiceModel model = new MsgVoiceModel();
		convert(document, model);
		Element root = document.getRootElement();
		model.setMediaId(root.elementTextTrim("MediaId"));
		model.setFormat(root.elementTextTrim("Format"));
		return model;
	}
}
