package com.shomop.http.rsp.resultset;

/**
 * 返回json 格式
 * 暂不支持多条件判断
 * @author spencer.xue
 * @date 2014-5-7
 */
public class RspJsonForm {

	/**
	 * 判断响应成功的标识
	 */
	private String key;
	
	/**
	 * 成功对应key的值
	 */
	private String value;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
}
