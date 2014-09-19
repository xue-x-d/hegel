package com.shomop.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class MailException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -876897940702556580L;
	
	
	public static final int MAIL_ERROR = 20000;
	
	private Throwable _cause;
	
	public MailException(String message) {
		super(MAIL_ERROR, message);
	}
	
	public MailException(Throwable cause) {
		super(MAIL_ERROR, cause.getMessage(), cause);
		this._cause = cause;
	}
	
	public MailException(String message, Throwable cause) {
		super(MAIL_ERROR, message, cause);
		this._cause = cause;
	}
	
	public MailException(MailExceptionCode mailCode, Throwable cause) {
		super(mailCode.getCode(), mailCode.getDesc(),cause);
	}
	
	public MailException(MailExceptionCode mailCode, String value) {
		super(mailCode.getCode(), mailCode.getDesc()+ ".MISS:["+value+"]");
	}
	

	public String getMessage() {
		if ((this._cause == null) || (this._cause == this)) {
			return super.getMessage();
		}
		return super.getMessage() + "; Cause by: " + this._cause.getClass().getName();
	}

	public void printStackTrace(PrintStream ps) {
		if ((this._cause == null) || (this._cause == this)) {
			super.printStackTrace(ps);
		} else {
			ps.println(this);
			this._cause.printStackTrace(ps);
		}
	}

	public void printStackTrace(PrintWriter pw) {
		if ((this._cause == null) || (this._cause == this)) {
			super.printStackTrace(pw);
		} else {
			pw.println(this);
			this._cause.printStackTrace(pw);
		}
	}
	
	
	public static void main(String[] args) {
		
		try {
			throw new MailException("abc",new NullPointerException("fsdf"));
		} catch (MailException e) {
			e.printStackTrace();
		}
		
	}
}
