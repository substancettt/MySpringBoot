package com.wellshang.data.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wellshang.data.entity.Article;
import com.wellshang.data.entity.MsgRequest;
import com.wellshang.data.entity.MsgResponseNews;
import com.wellshang.data.entity.MsgResponseText;

@SuppressWarnings("unchecked")
public class MsgXmlUtil {

	public static MsgRequest parseXml(HttpServletRequest request)
			throws Exception {
		MsgRequest msgReq = new MsgRequest();

		InputStream inputStream = request.getInputStream();

		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element e : elementList) {
			String name = e.getName();
			String text = e.getText();

			if ("MsgType".equals(name)) {
				msgReq.setMsgType(text);
			} else if ("MsgId".equals(name)) {
				msgReq.setMsgId(text);
			} else if ("FromUserName".equals(name)) {
				msgReq.setFromUserName(text);
			} else if ("ToUserName".equals(name)) {
				msgReq.setToUserName(text);
			} else if ("CreateTime".equals(name)) {
				msgReq.setCreateTime(text);
			} else if ("Content".equals(name)) {
				msgReq.setContent(text);
			} else if ("Recognition".equals(name)) {
				msgReq.setRecognition(text);
			} else if ("PicUrl".equals(name)) {
				msgReq.setPicUrl(text);
			} else if ("Location_X".equals(name)) {
				msgReq.setLocation_X(text);
			} else if ("Location_Y".equals(name)) {
				msgReq.setLocation_Y(text);
			} else if ("Scale".equals(name)) {
				msgReq.setScale(text);
			} else if ("Label".equals(name)) {
				msgReq.setLabel(text);
			} else if ("Event".equals(name)) {
				msgReq.setEvent(text);
			} else if ("EventKey".equals(name)) {
				msgReq.setEventKey(text);
			}
		}
		inputStream.close();
		inputStream = null;
		return msgReq;
	}

	public static String textToXml(MsgResponseText text) {
		xstream.alias("xml", text.getClass());
		return xstream.toXML(text);
	}

	public static String newsToXml(MsgResponseNews news) {
		xstream.alias("xml", news.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(news);
	}

	public static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean CDATA = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (CDATA) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

}