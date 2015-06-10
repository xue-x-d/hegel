package com.shomop.crm.model.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.http.annotation.NotThreadSafe;

import com.shomop.crm.model.Identifier;
import com.shomop.crm.model.annotation.BatchSaveIgnore;

/**
 * 邮件发送体
 * @author spencer.xue
 * @date 2014-11-19
 */
@NotThreadSafe
@Entity
@Table(name = "mail_task")
public class MailTask implements Identifier<Integer> {

	private Integer id;
	/**
	 * @see MailUser
	 */
	private Integer userId;
	/**
	 * @see MailTaskStatus
	 */
	private Integer status; // 邮件发送任务状态
	private Long created; // 创建件时间
	private String serverHost;// 邮件服务器域名或IP
	private String serverPort; // 邮箱服务器端口
	private String fromAddress;// 发件人地址
	private String fromPersonal;// 发件人名称
	private String username;// 发件人用户名
	private String password;// 发件人密码
	private String title;// 邮件的主题
	private String content;// 邮件的内容
	private boolean validate; // 是否需要身份验证
	private boolean htmlBody;// html格式邮件
	private boolean IsSSL; // ssl链接方式
	
	@BatchSaveIgnore
	@Transient private List<String> to;// 收件人列表
	
	@BatchSaveIgnore
	@Transient private List<String> cc;// 抄送人列表默认为空
	
	@BatchSaveIgnore
	@Transient private List<String> attachments;// 附件路径列表默认为空
	
	@BatchSaveIgnore
	@Transient private Properties prop;
	
	public MailTask() {
		this(false, false);
	}

	public MailTask(boolean validate) {
		this(validate, false);
	}
	
	public MailTask(boolean validate, boolean isHtmlBody) {
		this.htmlBody = isHtmlBody;
		this.validate = validate;
		this.prop = new Properties();
		this.to = new ArrayList<String>();
	}
	
	public Properties properties() {
		prop.put("mail.smtp.host",this.serverHost);
		prop.put("mail.smtp.port",this.serverPort);
		prop.put("mail.smtp.auth", validate ? "true" : "false");
		prop.put("mail.from", this.fromAddress);
		return prop;
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
	@Transient
	public List<String> getTo() {
		return to;
	}

	public void addTo(String to) {
		this.to.add(to);
	}
	@Transient
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

	@Column(columnDefinition = "varchar(150)",nullable=false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(columnDefinition = "TEXT")
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
	
	@Transient
	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		if (attachments != null) {
			this.attachments = attachments;
		}
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

	public boolean getIsSSL() {
		return IsSSL;
	}

	public void setIsSSL(boolean IsSSL) {
		this.IsSSL = IsSSL;
	}
	
	@Column(columnDefinition="int(11) not null default 0")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}


	public static enum MailTaskStatus {

		CREATE_SUCCESS, WAIT_DELIVERY, DELIVERY_COMPLETE, DELIVERY_FAILED;
	}

}
