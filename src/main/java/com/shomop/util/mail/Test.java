package com.shomop.util.mail;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

public class Test {

	static String reg = "\\<([\\S)]{6,})\\>";
	static String email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	public static void main(String[] args) {
		
		try {
			String encode = MimeUtility.encodeWord("薛晓冬",Charset.forName("UTF-8").name(),null);
			String name = encode+"<jim@example.com>";
			Matcher matcher = Pattern.compile(reg).matcher(name);
			if(matcher.find()){
				System.out.println(matcher.group(1));
			}
			System.out.println(name.substring(name.indexOf("<"),name.lastIndexOf(">")));
			InternetAddress address = new InternetAddress(name);
			System.out.println(address);
			InternetAddress address2 = new InternetAddress("710709510@qq.com","中国",Charset.forName("UTF-8").name());
			System.out.println(address2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
