package com.game.yangtechplatform.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
/**
 * 新的
 * @author Administrator
 *
 */
public final class DesCode {
	private final String key = "inlee.cn";

	public  String encrypt(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,iv);
		byte[] encryptedData = cipher.doFinal(message.getBytes());
		return Base64.encode(encryptedData);

		///return toHexString(cipher.doFinal(message.getBytes("UTF-8")));
	}

public  String decrypt(String message) throws Exception {

		byte[] bytesrc = Base64.decode(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes());

		cipher.init(Cipher.DECRYPT_MODE, secretKey,iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

public static byte[] convertHexString(String ss) throws UnsupportedEncodingException {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}
	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);//b[ i ] & 0xFF将一个byte和 0xFF进行了与运算
			if (plainText.length() == 1)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}

	public static void main(String[] args) {
		String test="{\"userName\":\"admin\",\"code\":\"123123\",\"passWord\":\"admin\"}";
		 try {
			 
		 } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
