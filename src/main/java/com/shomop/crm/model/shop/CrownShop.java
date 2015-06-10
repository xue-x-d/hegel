package com.shomop.crm.model.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shomop.crm.model.Identifier;

@Entity
@Table(name = "crown_shop")
public class CrownShop implements Identifier<Integer> {

	/**
	 * alter table crown_shop add unique index index_sid(sid);
	 */
	private Integer id;
	private Integer cId;
	private Long sid;
	private String nick;
	private String title;
	private Integer level;
	private String type;
	private String company;
	private String locus;
	private String mobileNo;
	private String picPath;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}
	@Column(nullable=false)
	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@Column(columnDefinition = "varchar(50) not null")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}
	
	@Column(columnDefinition = "varchar(1) not null")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Column(columnDefinition = "varchar(20)")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
}
