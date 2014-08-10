package com.shomop.crm.model.si;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "si_user")
public class SI_User{
	
	private Long userId;
	private String username; // 用户名
	private String password;// 密码 预留用于登录识别
	private String mobileNumber; // 手机号码
	private String accessToken; // 授权码
	private Date authDate; // 授权时间
	private Date updateTime; // 更新时间
	private Date expiresTime; // 过期时间
	private boolean deleted; // 帐号控制
	
	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getUserId() {
 		return this.userId;
	}
	 
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAuthDate() {
		return authDate;
	}
	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getExpiresTime() {
		return expiresTime;
	}
	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
