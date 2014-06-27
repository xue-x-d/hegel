package com.shomop.http.rsp.resultset;

/**
 * support response content-type
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17
 * @author spencer.xue
 * @date 2014-5-7
 */
public interface RspType {

	public static final String PLAIN_TEXT_TYPE = "text/plain";
	public static final String PLAIN_HTML_TYPE = "text/html";
	public static final String JSON_TPYE = "application/json";

}
