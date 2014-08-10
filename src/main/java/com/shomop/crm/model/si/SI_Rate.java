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

import org.hibernate.annotations.Index;

@Entity
@Table(name="si_rate")
public class SI_Rate {
	
	private Long rateId;
	private Long userId;
	private Long numIid;
	private Date rateTime;
	private Integer rateGrade;
	private Boolean validated;
	private String content;
	
	@Id
	@Column(name = "rateId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getRateId() {
		return rateId;
	}
	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getNumIid() {
		return numIid;
	}
	@Index(name = "index_numIid")
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getRateTime() {
		return rateTime;
	}
	public void setRateTime(Date rateTime) {
		this.rateTime = rateTime;
	}
	public Integer getRateGrade() {
		return rateGrade;
	}
	public void setRateGrade(Integer rateGrade) {
		this.rateGrade = rateGrade;
	}
	public Boolean getValidated() {
		return validated;
	}
	public void setValidated(Boolean validated) {
		this.validated = validated;
	}
	@Column(columnDefinition = "TEXT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
