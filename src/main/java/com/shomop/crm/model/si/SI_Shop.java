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
@Table(name="si_shop")
public class SI_Shop {

	private Long shopId;
	private String shopName;
	private Integer shopStatus;
	private String description;
	private String shopPicUrl;
	private String shopLocation;
	private Date openTime;
	private Date closeTime;
	private Double deliveryPrice;
	private String shopTelephone;
	private String remark;
	@Id
	@Column(name = "shopId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getShopStatus() {
		return shopStatus;
	}
	public void setShopStatus(Integer shopStatus) {
		this.shopStatus = shopStatus;
	}
	@Column(columnDefinition = "TEXT")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getShopPicUrl() {
		return shopPicUrl;
	}
	public void setShopPicUrl(String shopPicUrl) {
		this.shopPicUrl = shopPicUrl;
	}
	public String getShopLocation() {
		return shopLocation;
	}
	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
	}
	@Temporal(TemporalType.TIME)
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	@Temporal(TemporalType.TIME)
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	
	public Double getDeliveryPrice() {
		return deliveryPrice;
	}
	public void setDeliveryPrice(Double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	public String getShopTelephone() {
		return shopTelephone;
	}
	public void setShopTelephone(String shopTelephone) {
		this.shopTelephone = shopTelephone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
