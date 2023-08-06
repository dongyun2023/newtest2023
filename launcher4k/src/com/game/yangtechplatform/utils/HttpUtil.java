package com.game.yangtechplatform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class HttpUtil {
	static int timeout = 1500000;

	public static String http(String urlStr, String method, String postData) {
		byte tmpByte = 0;
		URL url = null;
		HttpURLConnection httpConn = null;
		InputStream inputStream = null;
		String responseText = null;
		try { 
			Log.v("tag", "接口请求地址：" + urlStr + "，参数：" + postData);
			url = new URL(urlStr);
			httpConn = (HttpURLConnection) url.openConnection();

			HttpURLConnection.setFollowRedirects(true);
			httpConn.setRequestMethod(method);
			httpConn.setRequestProperty("content-type", "application/json");
			httpConn.setRequestProperty("Accept", "application/json");
			httpConn.setRequestProperty("Session",
					CommonUtil.getMD5(postData.getBytes("UTF-8")));
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
			httpConn.setReadTimeout(timeout);

			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);

			OutputStreamWriter out = new OutputStreamWriter(
					httpConn.getOutputStream(), "UTF-8");
			String string = null;
			try {
				string = new DesCode().encrypt(postData);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.v("tag", "..." + string);
			out.write(string);
			out.flush();
			out.close();

			inputStream = httpConn.getInputStream();
			List<Byte> tmpStream = new ArrayList<Byte>();
			while ((tmpByte = (byte) inputStream.read()) != -1) {
				tmpStream.add(tmpByte);
			}
			inputStream.close();
			httpConn.disconnect();

			responseText = new String(toByteArray(tmpStream), "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.v("tag", "接口返回信息：" + responseText);
		return responseText;
	}

	private static byte[] toByteArray(List<Byte> byteList) {
		int index = byteList.size();
		if (index > 0) {
			byte[] bytes = new byte[index];
			for (int i = 0; i < index; i++) {
				bytes[i] = byteList.get(i);
			}
			return bytes;
		} else {
			return null;
		}
	}
}