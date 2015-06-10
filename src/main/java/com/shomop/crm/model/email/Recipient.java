package com.shomop.crm.model.email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shomop.crm.model.Identifier;

/**
 * 收件人信息
 * @author spencer.xue
 * @date 2014-11-24
 */
@Entity
@Table(name = "recipient")
public class Recipient implements Identifier<Long> {

	private Long id;
	private Integer userId;
	private Integer taskId;
	private String email;
	/**
	 * 0 -> To
	 * 1 -> Cc
	 */
	private Integer type;
	private Integer status;
	private Long updateTime;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(columnDefinition="varchar(60) not null")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(columnDefinition="int(11) not null default 0")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(columnDefinition="int(11) not null default 0")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public static enum RecipientStatus {
		
		DELIVERY_SUCCESS,ILLEGAL_EMAIL,DELIVERY_FAILED;
	}
}
