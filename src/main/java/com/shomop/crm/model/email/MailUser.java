package com.shomop.crm.model.email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shomop.crm.model.Identifier;
/**
 * 使用email服务的用户标示
 * @author spencer.xue
 * @date 2014-9-22
 */
@Entity
@Table(name = "mail_user")
public class MailUser implements Identifier<Integer> {

	private Integer id;
	private String username;
	private String accessToken;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "accessToken", length = 32)
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
