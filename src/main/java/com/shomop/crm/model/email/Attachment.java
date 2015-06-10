package com.shomop.crm.model.email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shomop.crm.model.Identifier;

/**
 * 邮件附件
 * @author spencer.xue
 * @date 2014-11-24
 */
@Entity
@Table(name = "attachment")
public class Attachment implements Identifier<Integer> {

	private Integer id;
	private Integer userId;
	private Integer taskId;
	private Integer size;
	private String location;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
}
