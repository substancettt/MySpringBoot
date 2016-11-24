package com.wellshang.data.service;

import com.wellshang.data.entity.MsgRequest;

public interface IWxMsgService {
	public abstract String test();
	public abstract boolean validSign(String signature, String timestamp, String nonce);
	public abstract String handleMsg(MsgRequest msgRequest);
	public abstract String handleError(Integer errCode);

}
