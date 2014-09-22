package com.shomop.crm.model;

/**
 * DD授权响应数据封装实体类
 */
public class DDEditionInfo {

	private String sign;
	private String app_id;
	private String shop_id;
	private String edition_id;
	private String edition_status;
	private String edition_name;
	private String edition_end_date;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getEdition_id() {
		return edition_id;
	}
	public void setEdition_id(String edition_id) {
		this.edition_id = edition_id;
	}
	public String getEdition_status() {
		return edition_status;
	}
	public void setEdition_status(String edition_status) {
		this.edition_status = edition_status;
	}
	public String getEdition_name() {
		return edition_name;
	}
	public void setEdition_name(String edition_name) {
		this.edition_name = edition_name;
	}
	public String getEdition_end_date() {
		return edition_end_date;
	}
	public void setEdition_end_date(String edition_end_date) {
		this.edition_end_date = edition_end_date;
	}
	
}
