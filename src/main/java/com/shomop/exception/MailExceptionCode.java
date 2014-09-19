package com.shomop.exception;

public enum MailExceptionCode {

	CONFIG_MISS(20001,"邮件配置项缺失异常"),
	SSL_ERROR(20002,"邮件服务器ssl链接异常"),
	MESSAGE_FAILED(20003,"邮件消息体封装异常");
	
	private int code;
	private String desc;
	
	public int getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}

	private MailExceptionCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
}
	
