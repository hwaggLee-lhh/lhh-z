package com.lhh.z.weichart.msg;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lhh.z.weichart.msg.model.Article;
import com.lhh.z.weichart.msg.model.MsgNewsModel;
import com.lhh.z.weichart.msg.model.MsgTextModel;

public class DocumentUtils {
	protected static final Logger log = Logger.getLogger("WeichartMsgLog");
	private static Map<String, String> imgMap = new HashMap<String,String>();
	public static Map<String, String> getImgMap() {
		return imgMap;
	}
	public static void setImgMap(Map<String, String> imgMap) {
		DocumentUtils.imgMap = imgMap;
	}
	public static Document processRequest(HttpServletRequest req) {
		Document document = null;
		try {
			SAXReader reader = new SAXReader();
			InputStream in = req.getInputStream();
			document = reader.read(in);
		} catch (Exception e) {
		}
		if(document==null) return null;
		Element root = document.getRootElement();
		if(root==null) return null;
		MsgTypeEnum msgType = MsgTypeEnum.parse(root.elementTextTrim("MsgType"));
		//目前只处理文本消息
		MsgTextModel model = MsgFactory.buildText(document);
		if(!MsgTypeEnum.text.equals(msgType)) { //非文本消息
			model = defaultModel(model, "系统暂不接收"+getMsgTypeName(msgType)+"类型的消息。");
			return DocumentFactory.build(model);
		} 
		//文本消息
		String content = model.getContent();
		
		if(StringUtils.isBlank(content)) {
			model = defaultModel(model, "您是否要查询啥？");
			return DocumentFactory.build(model);
		}
		log.info("OpenId:"+model.getFromUserName()+" 内容："+content);
				String code = null;
		
		if(StringUtils.isBlank(code)) {
			model = defaultModel(model, "您是否要查询？");
			return DocumentFactory.build(model);
		}
		MsgNewsModel m = new MsgNewsModel();
		m.setFromUserName(model.getToUserName());
		m.setToUserName(model.getFromUserName());
		m.setMsgId(model.getMsgId());
		m.setCreateTime(model.getCreateTime());
		Article article = new Article();
		
		article.setTitle("测试"+"("+code+")");
		article.setUrl("http://new3band.com/web/homeIndex?code="+code);
		String imgUrl = imgMap.get(code);
		if(StringUtils.isBlank(imgUrl)) {
			article.setPicUrl("http://new3band.com/app/micro_h5/images/360x200.jpg");
		} else {
			article.setPicUrl(imgUrl);
		}
		String desc = "点击查看详情(分享自)";
		
		article.setDescription(desc);
		m.getArticles().add(article);
		return DocumentFactory.build(m);
	}
	private static MsgTextModel defaultModel(MsgTextModel model, String content) {
		//将消息转换 from与to换位置
		String fromUserName = model.getFromUserName();
		model.setFromUserName(model.getToUserName());
		model.setToUserName(fromUserName);
		model.setContent(content);
		return model;
	}
	
	private static String getMsgTypeName(MsgTypeEnum msgType) {
		if( msgType == null ) return null;
		String typeName = "";
		switch (msgType) {
		case text:
			typeName = "文本";
			break;
		case image:
			typeName = "图片";
			break;
		case voice:
			typeName = "语音";
			break;
		case video:
			typeName = "视频";
			break;
		case shortvideo:
			typeName = "视频";
			break;
		case location:
			typeName = "位置";
			break;
		case link:
			typeName = "链接";
			break;
		default:
			break;
		}
		return typeName;
	}
}
