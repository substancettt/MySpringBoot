package com.wellshang.data.service.impl;

import java.io.IOException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wellshang.data.config.WeiChatConfig;
import com.wellshang.data.entity.MsgRequest;
import com.wellshang.data.entity.MsgResponseText;
import com.wellshang.data.service.IWxMsgService;
import com.wellshang.data.util.MsgCodeUtil;
import com.wellshang.data.util.MsgXmlUtil;
import com.wellshang.data.util.TulingUtil;
import com.wellshang.data.util.WxApiUtil;

@Service("wxService")
@Scope("prototype")
public class WxMsgServiceImpl implements IWxMsgService {

	protected static final Logger LOG = LoggerFactory
			.getLogger(WxMsgServiceImpl.class);

	private static final String EnentMsgType = "event";
	private static final String SubscribeEventTyep = "subscribe";
	private static final String ClickEventTyep = "CLICK";

	@Autowired
	private WeiChatConfig weiChatConfig;

	@Override
	public String test() {
		try {
			return TulingUtil.getContentStr("测试", "weixinuser", weiChatConfig.tuLingApiKey).getText();
		} catch (IOException e) {
			return MsgCodeUtil.getMsgString(10002);
		}
	}

	@Override
	public boolean validSign(String signature, String timestamp, String nonce) {
		return WxApiUtil.validSign(signature, weiChatConfig.tocken, timestamp, nonce);
	}

	@Override
	public String handleMsg(MsgRequest msgRequest) {
		if (EnentMsgType.equals(msgRequest.getMsgType())) {
			return handleEnventMsg(msgRequest);
		} else {
			return handleTextMsg(msgRequest);
		}
	}

	@Override
	public String handleError(Integer errCode) {
		String errStr = MsgCodeUtil.getMsgString(errCode);
		LOG.error("[{}] {}", errCode, errStr);
		return errStr;
	}

	private String getResponseString(String content,
			MsgRequest msgRequest) {
		MsgResponseText reponseText = new MsgResponseText();
		reponseText.setToUserName(msgRequest.getFromUserName());
		reponseText.setFromUserName(msgRequest.getToUserName());
		reponseText.setMsgType("text");
		reponseText.setCreateTime(new Date().getTime());
		reponseText.setContent(content);
		msgRequest.setMsgResponse(reponseText);

		return MsgXmlUtil.textToXml(reponseText);
	}

	private String handleEnventMsg(MsgRequest msgRequest) {
		if (SubscribeEventTyep.equals(msgRequest.getEvent())) {
			return getResponseString(MsgCodeUtil.getMsgString(10003), msgRequest);
		} else if (ClickEventTyep.equals(msgRequest.getEvent())) {
			String content = "Click Menu" + msgRequest.getEventKey();
			return getResponseString(content, msgRequest);
		} else {
			return getResponseString(MsgCodeUtil.getMsgString(10004), msgRequest);
		}
	}
	
	private String handleTextMsg(MsgRequest msgRequest) {
		try {
			return getResponseString(
					TulingUtil.getContentStr(
							msgRequest.getContent(),
							msgRequest.getFromUserName(),
							weiChatConfig.tuLingApiKey).getResult(),
					msgRequest);
		} catch (IOException e) {
			return getResponseString(MsgCodeUtil.getMsgString(10005), msgRequest);
		}
	}

}
