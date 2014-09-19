package com.shomop.util.mail;


import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.shomop.exception.MailException;
import com.shomop.exception.MailExceptionCode;
import com.shomop.util.ext.CustomProperty;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @author spencer.xue
 * @date 2014-9-18
 */
public class MailSender {

	private static Logger logger = Logger.getLogger(MailSender.class);
	
	private static final String DEFAULT_CONNECT_TIMEOUT = "2000";
	
	private static final String ATTACHMENT_ROOT_PATH = "";
	
	private static Session systemMailSession;
	
	
	public static boolean sendMail(MailInfo mailInfo) throws MailException{
		if(mailInfo == null){
			logger.error(">>> mailinfo not exist!");
			throw new MailException("mailInfo is null");
		}
		Session mailSession = null;
		if(StringUtils.isNotEmpty(mailInfo.getServerHost())){
			Authenticator authenticator = null;
			Properties prop = mailInfo.getProperties();  
			if (mailInfo.isValidate()) {
				authenticator = new MyAuthenticator(mailInfo.getUsername(),
						mailInfo.getPassword());
			}
			if(mailInfo.isSSL()){
				settingSSLConnect(prop);
			}
			mailSession = Session.getDefaultInstance(prop, authenticator);
		}else{
			mailSession = getSystemMailSession();
		    logger.debug(">>> user system default mail server.");
		}
		mailSession.setDebug(true); // 开启调试
		Message mailMessage = new MimeMessage(mailSession);
		// 创建邮件消息体
		try {
			if(StringUtils.isNotBlank(mailInfo.getFromPersonal())){
				InternetAddress address = new InternetAddress();
				address.setAddress(mailSession.getProperty("mail.from"));
				address.setPersonal(mailInfo.getFromPersonal(), Charset.forName("UTF-8").name());
				mailMessage.setFrom(address);
			}
			if (mailInfo.getTo().size() > 0) {
				Address[] toAdds = new InternetAddress[mailInfo.getTo().size()];
				for (int i = 0; i < mailInfo.getTo().size(); i++) {
					toAdds[i] = new InternetAddress(mailInfo.getTo().get(i));
				}
				mailMessage.setRecipients(Message.RecipientType.TO,toAdds);
			}
			if (mailInfo.getCc().size() > 0) {
				Address[] ccAdds = new InternetAddress[mailInfo.getCc().size()];
				for (int i = 0; i < mailInfo.getCc().size(); i++) {
					ccAdds[i] = new InternetAddress(mailInfo.getTo().get(i));
				}
				mailMessage.setRecipients(Message.RecipientType.CC,ccAdds);
			}
//			mailMessage.setRecipients(Message.RecipientType.TO, mailInfo.getTo().toArray(new Address[0]));
//			mailMessage.setRecipients(Message.RecipientType.CC, mailInfo.getCc().toArray(new Address[0]));
			mailMessage.setSubject(mailInfo.getTitle());
			mailMessage.setSentDate(new Date());
			if(mailInfo.isHtmlBody()){
				// 正文 
	            Multipart mainPart = new MimeMultipart();  
	            BodyPart mainBody = new MimeBodyPart();
	            mainBody.setContent(mailInfo.getContent(), "text/html; charset=utf-8");  
	            mainPart.addBodyPart(mainBody);
	            // 附件
				for (String path : mailInfo.getAttachments()) {
					BodyPart attachBody = new MimeBodyPart();
					attachBody.setFileName(path.substring(path.lastIndexOf("/")));
					FileDataSource fds = new FileDataSource(path);
					DataHandler dh = new DataHandler(fds);
					attachBody.setDataHandler(dh);
					mainPart.addBodyPart(attachBody);
				}
	            // 将MiniMultipart对象设置为邮件内容  
	            mailMessage.setContent(mainPart);  
			}else{
				mailMessage.setText(mailInfo.getContent());	
			}
			//mailMessage.saveChanges(); 
			Transport.send(mailMessage);
		} catch (Exception e) {
			logger.error(">>> send mail fail. ",e);
			throw new MailException(MailExceptionCode.MESSAGE_FAILED,e);
		}
		return true;
	}
	
	/**
	 * system default mail session
	 * @return  a global session
	 * @throws MailException
	 */
	private static synchronized Session getSystemMailSession() throws MailException{
		if(systemMailSession != null){
			return systemMailSession;
		}
		String serverHost = CustomProperty.getContextProperty("mailServerHost");
		String serverPort = CustomProperty.getContextProperty("mailServerPort");
		if(StringUtils.isBlank(serverHost) || StringUtils.isBlank(serverPort)){
			throw new MailException(MailExceptionCode.CONFIG_MISS,"serverHost or serverPort");
		}
		Properties props = new Properties();
		props.put("mail.smtp.host", serverHost);// 设置邮件服务器的域名或IP
		props.put("mail.smtp.port", serverPort);// 设置邮件服务器的端口
		String timeout = CustomProperty.getContextProperty("mailConnectTimeout");
		props.put("mail.stmp.timeout",StringUtils.isBlank(timeout)?DEFAULT_CONNECT_TIMEOUT:timeout);
		Boolean isSSL = Boolean.parseBoolean(CustomProperty.getContextProperty("mailIsSSL"));
		if (isSSL.booleanValue()) {
			// ssl 链接发送配置
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.socketFactory.port",serverPort);
	        // 暂时不做服务器证书校验  
			props.put("mail.smtp.ssl.checkserveridentity", "false");
			try {
				// 方式一： 使用自定义socketFactory
				MailSSLSocketFactory sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true); // 暂时信任所有邮件服务器
				// 添加信任的服务器地址
				// sf.setTrustedHosts(new String[]{"smtp.163.com"});
				props.put("mail.smtp.ssl.socketFactory", sf);
				// 方式二： 
				// props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				// props.put("mail.smtp.ssl.trust", "smtp.163.com");// 多个地址之间用空格分开    
			} catch (GeneralSecurityException e) {
				throw new MailException(MailExceptionCode.SSL_ERROR.getDesc(),e);
			}
		}
        Boolean isAuth = Boolean.parseBoolean(CustomProperty.getContextProperty("mailIsAuth"));
        String username = CustomProperty.getContextProperty("mailAuthUsername");
		String password = CustomProperty.getContextProperty("mailAuthPassword");
		// 是否需要身份验证
		Authenticator auth = null;
		if (isAuth.booleanValue() && StringUtils.isNotBlank(username)
				&& StringUtils.isNotBlank(password)) {
			props.put("mail.smtp.auth", "true");// 授权邮件
			auth = new MyAuthenticator(username,password);
		}
		String fromEmail = CustomProperty.getContextProperty("mailFromAddress");
		if(StringUtils.isBlank(fromEmail)){
			throw new MailException(MailExceptionCode.CONFIG_MISS,"mailFromAddress");
		}
		props.put("mail.from", fromEmail);
		// debug调试
		// props.setProperty("mail.debug", "true");
		// A single default session can be shared by multiple applications on the desktop.
		return Session.getDefaultInstance(props, auth);
	}
	
	/**
	 * 配置实用SSL链接
	 * @param props
	 * @throws MailException
	 */
	private static void settingSSLConnect(Properties props) throws MailException{
		// ssl 链接发送配置
		props.put("mail.smtp.ssl.enable", "true");
		// 暂时不做服务器证书校验(使用信任列表)
		props.put("mail.smtp.ssl.checkserveridentity", "false");
		try {
			// 方式一： 使用自定义socketFactory
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true); // 暂时信任所有邮件服务器
			// 添加信任的服务器地址
			// sf.setTrustedHosts(new String[]{"smtp.163.com"});
			props.put("mail.smtp.ssl.socketFactory", sf);
			// 方式二：
			// props.put("mail.smtp.socketFactory.class",
			// "javax.net.ssl.SSLSocketFactory");
			// props.put("mail.smtp.ssl.trust", "smtp.163.com");// 多个地址之间用空格分开
		} catch (GeneralSecurityException e) {
			throw new MailException(MailExceptionCode.SSL_ERROR.getDesc(), e);
		}
	}
	
	static class MyAuthenticator extends Authenticator{
		final String userName;
		final String password;
		public MyAuthenticator(String username, String password) { 
			this.userName = username; 
			this.password = password; 
		} 
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(userName, password);
		}
	}
	 
}
