package com.shomop.crm.model.si;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name="si_item_extra")
public class SI_ItemRate {

	private Long numIExid;
	private Long numIid;
	private Integer rateNumber;//累计评价数量
	private Integer rateGrade;
	private Integer totalSale;//销量
	private Integer status;//商品售卖状态
	
	@Id
	@Column(name = "numIExid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getNumIExid() {
		return numIExid;
	}
	public void setNumIExid(Long numIExid) {
		this.numIExid = numIExid;
	}
	@Index(name = "index_numIid")
	public Long getNumIid() {
		return numIid;
	}
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	public Integer getRateNumber() {
		return rateNumber;
	}
	public void setRateNumber(Integer rateNumber) {
		this.rateNumber = rateNumber;
	}
	public Integer getRateGrade() {
		return rateGrade;
	}
	public void setRateGrade(Integer rateGrade) {
		this.rateGrade = rateGrade;
	}
	public Integer getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(Integer totalSale) {
		this.totalSale = totalSale;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
