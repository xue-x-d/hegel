package com.shomop.crm.model.si;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "si_trade")
public class SI_Trade {

	private Long tid;
	private Long userId;// 用户id
	private Long shopId;// 店铺id
	private String shopTitle;// 店铺名称
	private String receiverAddress; // 收货地址
	private Double totalPrice;// 商品总价
	private Double yfFee;// 运费价格
	private Double payment;// 实付金额
	private String receiverName;// 收货人
	private String receiverPhone;// 收货电话
	private String receiverSchool;// 收货校区
	private String receiverCampus;// 校区
	private String receiverFlat;// 公寓
	private String receiverDormitory;// 宿舍
	private Integer tradeStatus;// 状态
	private String payType;// 支付方式 cod online
	private Date requiredDeliveryTime;// 医院送达时间
	private Date actualDeliveryTime;// 实际支付时间
	private Date createdTime;// 创建时间
	private Date consignTime;// 送货时间
	private Date endTime;// 订单完成时间
	private String remark;// 备注
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	@Index(name = "index_user_createdTime",columnNames={"userId","createdTime"})
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getYfFee() {
		return yfFee;
	}

	public void setYfFee(Double yfFee) {
		this.yfFee = yfFee;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverSchool() {
		return receiverSchool;
	}

	public void setReceiverSchool(String receiverSchool) {
		this.receiverSchool = receiverSchool;
	}

	public String getReceiverCampus() {
		return receiverCampus;
	}

	public void setReceiverCampus(String receiverCampus) {
		this.receiverCampus = receiverCampus;
	}

	public String getReceiverFlat() {
		return receiverFlat;
	}

	public void setReceiverFlat(String receiverFlat) {
		this.receiverFlat = receiverFlat;
	}

	public String getReceiverDormitory() {
		return receiverDormitory;
	}

	public void setReceiverDormitory(String receiverDormitory) {
		this.receiverDormitory = receiverDormitory;
	}

	public Integer getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getRequiredDeliveryTime() {
		return requiredDeliveryTime;
	}

	public void setRequiredDeliveryTime(Date requiredDeliveryTime) {
		this.requiredDeliveryTime = requiredDeliveryTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getActualDeliveryTime() {
		return actualDeliveryTime;
	}
	
	public void setActualDeliveryTime(Date actualDeliveryTime) {
		this.actualDeliveryTime = actualDeliveryTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Index(name = "index_user_createdTime",columnNames={"userId","createdTime"})
	public Date getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getConsignTime() {
		return consignTime;
	}
	
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
