package com.wellshang.data.controllers;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wellshang.data.entity.MsgRequest;
import com.wellshang.data.service.IMessageService;
import com.wellshang.data.service.IWxMsgService;
import com.wellshang.data.util.MsgXmlUtil;

@RestController
@RequestMapping("/api/wx")
class WxApiController {

	private static final Logger LOG = LoggerFactory
			.getLogger(WxApiController.class);

	@Autowired
	@Qualifier("wxService")
	IWxMsgService wxService;

	@Autowired
	@Qualifier("msgService")
	IMessageService msgService;

	@RequestMapping(method = RequestMethod.GET)
	String doGet(
			@RequestParam(value="signature", required=false) String signature,
			@RequestParam(value="timestamp", required=false) String timestamp,
			@RequestParam(value="nonce", required=false) String nonce,
			@RequestParam(value="echostr", required=false) String echostr
			) {
//		msgService.sendMsg("weixin", LocalDateTime.now().toString() + " GET");
		if (timestamp == null || signature == null || nonce == null || echostr == null) {
			return wxService.test();
		} else {
			if (wxService.validSign(signature, timestamp, nonce)) {
				return echostr;
			} else {
				return wxService.handleError(10000);
			}
		}
	}

    @RequestMapping(method = RequestMethod.POST)
    String doPost(HttpServletRequest request) {
		try {
//			msgService.sendMsg("weixin", LocalDateTime.now().toString() + " POST");
			LOG.info("{}", request.getMethod());
			MsgRequest msgRequest = MsgXmlUtil.parseXml(request);
			return wxService.handleMsg(msgRequest);
		} catch (Exception e) {
			return wxService.handleError(10001);
		} finally {
			LOG.info("Done.");
		}
	}

}
