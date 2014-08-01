package com.shomop.crm.model.notify;

import java.io.Serializable;

public class DDNotifyTrade implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 3594935397484390635L;
	
	private Long tid;
	
	private Long userId;
	
	private Long insertTime;
	
	private Integer status;
	
	public DDNotifyTrade(){
		
	}
	
	public DDNotifyTrade(Long tid,Long userId,Long insertTime,String status){
		this.tid = tid;
		this.userId = userId;
		this.insertTime = insertTime;
		this.status = TradeStatus.status2Type.get(status) == null ? -1
				: TradeStatus.status2Type.get(status);
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Long insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}
