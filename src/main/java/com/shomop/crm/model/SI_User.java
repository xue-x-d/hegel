package com.shomop.crm.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SI_User {

	private Integer id;
	
	private String username;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
