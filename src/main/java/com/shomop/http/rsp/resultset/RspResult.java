package com.shomop.http.rsp.resultset;

import java.util.Map;

/**
 * 响应结果的包装类
 * @author spencer.xue
 * @date 2014-5-12
 */
public class RspResult {

	public static final String RESULT_KEY = "result";
	
	/**
	 * 请求是否成功
	 */
	private ResultFlag flag;
	/**
	 * 响应结果
	 * 非json格式 默认key为 {@link #RESULT_KEY}
	 */
	private Map<String,String> result;
	
	public ResultFlag getFlag() {
		return flag;
	}
	public void setFlag(ResultFlag flag) {
		this.flag = flag;
	}
	public Map<String, String> getResult() {
		return result;
	}
	public void setResult(Map<String, String> result) {
		this.result = result;
	}
	
	
}
