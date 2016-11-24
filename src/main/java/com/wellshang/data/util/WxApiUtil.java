package com.wellshang.data.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WxApiUtil {

	public static boolean validSign(String signature, String tocken,
			String timestamp, String nonce) {
		String[] paramArr = new String[] { tocken, timestamp, nonce };
		Arrays.sort(paramArr);
		StringBuilder sb = new StringBuilder(paramArr[0]);
		sb.append(paramArr[1]).append(paramArr[2]);
		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(sb.toString().getBytes());
			ciphertext = StringUtil.byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return ciphertext != null ? ciphertext.equals(signature.toUpperCase())
				: false;
	}
}
