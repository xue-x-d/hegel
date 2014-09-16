package com.shomop.exception;

public class BaseException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 系统异常
	 */
	public static final int ERROR_SYSTEM = 10000;
	
	private int code;
	private String message;
	
	public BaseException(int code){
		super();
		this.code  = code;
	}

	public BaseException(int code,String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.message = message;
	}

	public BaseException(int code ,String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(int code,Throwable cause) {
		super(cause);
		this.code = code;
		this.message = cause.getMessage();
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
