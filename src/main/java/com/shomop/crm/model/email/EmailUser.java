package com.shomop.crm.model.email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.shomop.crm.model.Identifier;
/**
 * 使用email服务的用户标示
 * @author spencer.xue
 * @date 2014-9-22
 */
@Entity
@Table(name = "email_user")
public class EmailUser implements Identifier<String> {

	private String userId;
	private String username;
	private String accessToken;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return userId;
	}

	public void setId(String id) {
		this.userId = id;
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
