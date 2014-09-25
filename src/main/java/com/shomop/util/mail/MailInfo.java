package com.shomop.util.mail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5100819782699686204L;
	
	private String serverHost;// 邮件服务器域名或IP
	private String serverPort; // 邮箱服务器端口
	private String fromAddress;// 发件人地址
	private String fromPersonal;// 发件人名称
	private List<String> to;// 收件人列表
	private List<String> cc;// 抄送人列表
	private String username;// 发件人用户名
	private String password;// 发件人密码
	private String title;// 邮件的主题
	private String content;// 邮件的内容
	private boolean validate; // 是否需要身份验证
	private boolean htmlBody;// html格式邮件
	private boolean SSL; // ssl链接方式
	private List<String> attachments;// 附件路径列表
	private Properties prop; 
	
	public MailInfo() {
		this(false, false);
	}

	public MailInfo(boolean validate) {
		this(validate, false);
	}
	
	public MailInfo(boolean validate, boolean isHtmlBody) {
		this.htmlBody = isHtmlBody;
		this.validate = validate;
		this.prop = new Properties();
		this.to = new ArrayList<String>();
		this.cc = new ArrayList<String>();
		this.attachments = new ArrayList<String>();
	}
	
	public Properties getProperties() {
		prop.put("mail.smtp.host",this.serverHost);
		prop.put("mail.smtp.port",this.serverPort);
		prop.put("mail.smtp.auth", validate ? "true" : "false");
		prop.put("mail.from", this.fromAddress);
		return prop;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public List<String> getTo() {
		return to;
	}

	public void addTo(String to) {
		this.to.add(to);
	}

	public List<String> getCc() {
		return cc;
	}

	public void addcc(String cc) {
		this.cc.add(cc);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFromPersonal() {
		return fromPersonal;
	}

	public void setFromPersonal(String fromPersonal) {
		this.fromPersonal = fromPersonal;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public void setTo(List<String> to) {
		if (to != null) {
			this.to = to;
		}
	}

	public void setCc(List<String> cc) {
		if (cc != null) {
			this.cc = cc;
		}
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public boolean isHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(boolean htmlBody) {
		this.htmlBody = htmlBody;
	}

	public boolean isSSL() {
		return SSL;
	}

	public void setSSL(boolean sSL) {
		SSL = sSL;
	}

}
