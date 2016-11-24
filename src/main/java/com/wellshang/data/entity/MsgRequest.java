package com.wellshang.data.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MsgRequest {

	private Long id;

	private String MsgType;

	private String MsgId;

	private String FromUserName;

	private String ToUserName;

	private String CreateTime;

	private String Content;

	private String Recognition;

	private String PicUrl;

	private String Location_X;

	private String Location_Y;

	private String Scale;

	private String Label;

	private String Event;

	private String EventKey;

	private MsgResponse msgResponse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MsgResponse getMsgResponse() {
		return msgResponse;
	}

	public void setMsgResponse(MsgResponse msgResponse) {
		this.msgResponse = msgResponse;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public String getScale() {
		return Scale;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	public void setScale(String scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String toString() {
		String str = "MsgRequest: id is " + id;
		Field[] field = this.getClass().getDeclaredFields();
		for (int i = 0; i < field.length; i++) {
			String filedName = field[i].getName();
			String filedType = field[i].getType().getTypeName();
			if (filedType.equals(String.class.getTypeName())) {
				try {
					Method getter = this.getClass()
							.getMethod("get" + filedName);
					Object filedValue = getter.invoke(this);
					if (filedValue == null) {
						continue;
					} else {
						str += ", " + filedName + " is " + filedValue.toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return str;
	}

}