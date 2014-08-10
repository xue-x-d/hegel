package com.shomop.crm.model.si;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shomop.crm.model.Identifier;

@Entity
@Table(name = "si_user_address")
public class SI_UserAddress implements Identifier<Long> {

	private Long id;
	private Long userId;
	private String university;// 
	private String campus;// 校区
	private String flat;// 公寓
	private String dormitory;// 宿舍
	private boolean isSelected;
	private boolean deleted;//
	
	@Id
	@Column(name = "aid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {

		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public String getDormitory() {
		return dormitory;
	}

	public void setDormitory(String dormitory) {
		this.dormitory = dormitory;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	

}
