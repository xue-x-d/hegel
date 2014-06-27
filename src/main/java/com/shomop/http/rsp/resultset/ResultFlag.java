package com.shomop.http.rsp.resultset;

/**
 * 请求到响应过程状态码
 * @author spencer.xue
 * @date 2014-5-8
 */
public enum ResultFlag {

	REQ_SEND_SUCCESS(1, "请求发送成功"), 
	REQ_SEND_FAILED(-1, "请求发送失败"), 
	RSP_RESULT_SUCCESS(2, "响应结果成功"), 
	RSP_RESULT_FAILED(-2, "响应结果失败"), 
	RSP_SERVER_SUCCESS(3, "服务器响应成功"), 
	RSP_SERVER_FAILED(-3, "服务器响应失败"), 
	RSP_SERVER_UNKNOWN(4, "服务器响应格式未知");

	private int flag;
	
	private String desc;
	
	private ResultFlag(int flag, String desc) {
		this.flag = flag;
		this.desc = desc;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}