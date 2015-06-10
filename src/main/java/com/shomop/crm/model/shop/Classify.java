package com.shomop.crm.model.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shomop.crm.model.Identifier;

@Entity
@Table(name = "shop_classify")
public class Classify implements Identifier<Integer> {

	/**
	 * alter table shop_classify add unique index index_sid(name);
	 */
	private Integer id;
	private String name;
	private String path;
	private String logo;
	/**
	 * -1 同步失败
	 * 0 同步中
	 * 1 同步完成
	 */
	private int status;
	/**
	 * 当前同步的页数
	 */
	private int page;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(columnDefinition = "varchar(10) not null")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
