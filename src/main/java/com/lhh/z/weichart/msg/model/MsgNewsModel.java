package com.lhh.z.weichart.msg.model;

import java.util.ArrayList;
import java.util.List;

import com.lhh.z.weichart.msg.MsgTypeEnum;

public class MsgNewsModel extends MsgBaseModel {
	public MsgNewsModel() {
		super.setMsgType(MsgTypeEnum.news);
	}
	private List<Article> articles = new ArrayList<Article>();
	public List<Article> getArticles() {
		return articles;
	}
}
