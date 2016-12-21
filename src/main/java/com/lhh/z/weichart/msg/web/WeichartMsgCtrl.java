package com.lhh.z.weichart.msg.web;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lhh.z.weichart.CtrlUtils;
import com.lhh.z.weichart.Signature;
import com.lhh.z.weichart.msg.DocumentUtils;

@Controller
@RequestMapping("/weichart_msg")
public class WeichartMsgCtrl {
	private static String Token = "new3band";
	protected static final Logger log = Logger.getLogger("WeichartMsgLog");
	private boolean isRefreshing = false;
	/**
	 * 刷新股票对应的图片链接数据
	 * @param force 是否强制刷新
	 */
	private void refreshData(boolean force) {
		if(isRefreshing) return;
		isRefreshing = true;
		try {
			
			log.info("Images url has been refreshed.");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		isRefreshing = false;
	}

	@RequestMapping("/refresh_imgurl")
	public void refresh(HttpServletRequest req, HttpServletResponse res) {
		this.refreshData(true);
		CtrlUtils.putJSONResult(true, "已完成", res);
	}
	@RequestMapping("/receive")
	public void receive(HttpServletRequest req, HttpServletResponse res) {
		log.info("RemoteAddr:"+req.getRemoteAddr()+" QueryString:"+req.getQueryString());
		String checkString = req.getParameter("echostr");
		if(!check(req)) { //验证不通过
			log.error("Illegal request!");
			this.back("Illegal request!", res);
			return;
		}
		if(StringUtils.isNotBlank(checkString)) {//验证流程
			this.back(checkString, res);
			return;
		}
		this.refreshData(false);
		//自动回复流程
		Document document = DocumentUtils.processRequest(req);
		if(document==null) {
			this.back("", res);
			return;
		}
		try {
			res.getWriter().write(document.asXML());
		} catch (Exception e) {
		}
	}

	private boolean check(HttpServletRequest req) {
		String sn = req.getParameter("signature"); // 取验证请求信息
		String ts = req.getParameter("timestamp"); // 取验证请求信息
		String nonce = req.getParameter("nonce"); // 取验证请求信息
		if(StringUtils.isBlank(sn) || StringUtils.isBlank(ts) || StringUtils.isBlank(nonce)) {
			return false;
		}
		String[] tmpArray = { Token, ts, nonce };
		Arrays.sort(tmpArray);
		String tmpStr = ArrToStr(tmpArray);// 数组转字符串
		tmpStr = Signature.sha1(tmpStr); // 调用sha1加密
		if (tmpStr.equalsIgnoreCase(sn)) { // 将加密后的信息与服务器计算完的验证信息对比
			return true;
		} else {
			return false;
		}
	}
	// 数组转字符串
	public static String ArrToStr(String[] arra) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < arra.length; i++) {
			buffer.append(arra[i]);
		}
		return buffer.toString();
	}
	private void back(String msg, HttpServletResponse res) {
		try {
			res.getWriter().write(msg);
			res.getWriter().flush();
			res.getWriter().close();
		} catch (Exception e) {
		}
	}
	public static void main(String[] args) {
		log.info("test");
	}
}
