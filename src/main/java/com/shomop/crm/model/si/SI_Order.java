package com.shomop.crm.model.si;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "si_order")
public class SI_Order {
	
	private Long oid;
	private Long tid;
	private Long numIid;//商品编号
	private String title;//
	private Double price;//
	private Integer number;
	private Integer type;// 商品类型 （ 店铺商品 店铺赠 网站赠品）
    private Boolean isRate;// 是否评价
	private String remark;//
	private Integer orderStatus;// 状态
	
	
	@Id
	@Column(name = "oid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	@Index(name = "index_tid_status",columnNames={"tid","orderStatus"})
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getIsRate() {
		return isRate;
	}

	public void setIsRate(Boolean isRate) {
		this.isRate = isRate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Index(name = "index_tid_status",columnNames={"tid","orderStatus"})
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	

}
