package com.wellshang.data.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.wellshang.data.entity.TulingResult;

public class TulingUtil {

	public static TulingResult getContentStr(String request, String userid, String key)
			throws IOException {
		Gson gson = new Gson();
		String INFO = URLEncoder.encode(request, "utf-8");
		String USER_ID = URLEncoder.encode(userid, "utf-8");
		String getURL = "http://www.tuling123.com/openapi/api?key="
				+ key + "&info=" + INFO + "&userid=" + USER_ID;
		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		connection.disconnect();
		
		return gson.fromJson(sb.toString(),TulingResult.class);
	}

}