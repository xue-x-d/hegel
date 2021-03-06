package com.shomop.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
	
	/**
	 * 32位MD5摘要
	 * @param message
	 * @return
	 */
	public static String md5(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(message.getBytes("UTF-8"));
			byte result[] = md.digest();
			StringBuilder buf = new StringBuilder();
			for (byte b : result) {
				int t = b & 0xff;
				if (t < 16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(t));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static void main(String[] args) {
		// fffffffa
		// fffffffa
		System.out.println(Integer.toHexString((byte)250));
	}
}
