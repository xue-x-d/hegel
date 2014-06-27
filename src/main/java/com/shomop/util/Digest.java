package com.shomop.util;

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
			md.update(message.getBytes());
			byte b[] = md.digest();
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				if (b[i] < 16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
