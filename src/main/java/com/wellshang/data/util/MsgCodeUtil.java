package com.wellshang.data.util;

import java.util.HashMap;
import java.util.Map;

public class MsgCodeUtil {

	private static Map<String, String> codeMap = new HashMap<String, String>();

	static {
		codeMap.put("-1", "Error!");
		codeMap.put("0", "OK");
	}

	public static String getMsgString(Integer errCode) {
		if (errCode != null && codeMap.containsKey(errCode + "")) {
			return codeMap.get(errCode + "");
		}
		return null;
	}

}