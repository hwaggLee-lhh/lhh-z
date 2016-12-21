package com.lhh.z.weichart.msg;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lhh.z.weichart.msg.model.Article;
import com.lhh.z.weichart.msg.model.MsgBaseModel;
import com.lhh.z.weichart.msg.model.MsgImageModel;
import com.lhh.z.weichart.msg.model.MsgLinkModel;
import com.lhh.z.weichart.msg.model.MsgLocationModel;
import com.lhh.z.weichart.msg.model.MsgNewsModel;
import com.lhh.z.weichart.msg.model.MsgTextModel;
import com.lhh.z.weichart.msg.model.MsgVideoModel;
import com.lhh.z.weichart.msg.model.MsgVoiceModel;

public class DocumentFactory {
	public static Document abstractBuild(MsgBaseModel model) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		root.addElement("FromUserName").addCDATA(model.getFromUserName());
		root.addElement("ToUserName").addCDATA(model.getToUserName());
		root.addElement("CreateTime").addCDATA(model.getCreateTime());
		root.addElement("MsgType").addCDATA(model.getMsgType().getValue());
		root.addElement("MsgId").addCDATA(model.getMsgId());
		return document;
	}
	public static Document build(MsgTextModel model) {
		Document document = abstractBuild(model);
		Element root = document.getRootElement();
		root.addElement("Content").addCDATA(model.getContent());
		return document;
	}
	public static Document build(MsgImageModel model) {
		Document document = abstractBuild(model);
		Element root = document.getRootElement();
		root.addElement("PicUrl").addCDATA(model.getPicUrl());
		root.addElement("MediaId").addCDATA(model.getMediaId());
		return document;
	}
	public static Document build(MsgLinkModel model) {
		Document document = abstractBuild(model);
		Element root = document.getRootElement();
		root.addElement("Title").addCDATA(model.getTitle());
		root.addElement("Description").addCDATA(model.getDescription());
		root.addElement("Url").addCDATA(model.getUrl());
		return document;
	}
	public static Document build(MsgLocationModel model) {
		Document document = abstractBuild(model);
		Element root = document.getRootElement();
		root.addElement("Location_X").addCDATA(model.getLocation_x());
		root.addElement("Location_Y").addCDATA(model.getLocation_y());
		root.addElement("Label").addCDATA(model.getLabel());
		return document;
	}
	public static Document build(MsgVideoModel model) {
		Document document = abstractBuild(model);
		Element root = document.getRootElement();
		root.addElement("MediaId").addCDATA(model.getMediaId());
		root.addElement("ThumbMediaId").addCDATA(model.getThumbMediaId());
		return document;
	}
	public static Document build(MsgVoiceModel model) {
		Document document = abstractBuild(model);
		Element root = document.getRootElement();
		root.addElement("MediaId").addCDATA(model.getMediaId());
		root.addElement("Format").addCDATA(model.getFormat());
		return document;
	}
	public static Document build(MsgNewsModel model) {
		List<Article> articles = model.getArticles();
		if(articles==null || articles.size()==0) return null;
		Document document = abstractBuild(model);
		Element root = document.getRootElement();
		root.addElement("ArticleCount").addCDATA(articles.size()+"");
		Element articlesE = root.addElement("Articles");
		for (Article article : articles) {
			Element item = articlesE.addElement("item");
			item.addElement("Title").addCDATA(article.getTitle());
			item.addElement("Description").addCDATA(article.getDescription());
			item.addElement("PicUrl").addCDATA(article.getPicUrl());
			item.addElement("Url").addCDATA(article.getUrl());
		}
		return document;
	}
}
