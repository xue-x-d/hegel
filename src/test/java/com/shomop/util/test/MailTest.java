package com.shomop.util.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.shomop.util.mail.MailInfo;
import com.shomop.util.mail.MailSender;

@ActiveProfiles("develop")
@ContextConfiguration(locations = {"classpath:application.xml","classpath:spring-hibernate.xml"})
public class MailTest extends AbstractJUnit4SpringContextTests {

	static String reg = "\\<([\\S)]{6,})\\>";
	static String email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

	@Test
	public void testFromAddress() {
		try {
			String encode = MimeUtility.encodeWord("薛晓冬",Charset.forName("UTF-8").name(), null);
			String name = encode + "<jim@example.com>";
			Matcher matcher = Pattern.compile(reg).matcher(name);
			if (matcher.find()) {
				System.out.println(matcher.group(1));
			}
			System.out.println(name.substring(name.indexOf("<"),name.lastIndexOf(">")));
			InternetAddress address = new InternetAddress(name);
			System.out.println(address);
			InternetAddress address2 = new InternetAddress("710709510@qq.com",
					"薛晓冬", Charset.forName("UTF-8").name());
			System.out.println(address2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendMail(){
		MailInfo mailInfo = new MailInfo();
		mailInfo.getTo().add("710709510@qq.com");
		mailInfo.setTitle("系统默认personal");
		mailInfo.setContent("测试系统默认发件人姓名");
		MailSender.sendMail(mailInfo);
	}
	
	private static boolean isInetAddressLiteral(String addr) {
		boolean sawHex = false, sawColon = false;
		for (int i = 0; i < addr.length(); i++) {
			char c = addr.charAt(i);
			if (c >= '0' && c <= '9')
				; // digits always ok
			else if (c == '.')
				; // dot always ok
			else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
				sawHex = true; // need to see a colon too
			else if (c == ':')
				sawColon = true;
			else
				return false; // anything else, definitely not a literal
		}
		return !sawHex || sawColon;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		String host = null;
		InetAddress me = InetAddress.getLocalHost();
		if (me != null) {
			host = me.getHostName();
			if (host.length() > 0 && isInetAddressLiteral(host))
				host = '[' + host + ']';
		}
		System.out.println(host);
		
		System.out.println(System.getenv("NODE_ENV"));
	}
}
