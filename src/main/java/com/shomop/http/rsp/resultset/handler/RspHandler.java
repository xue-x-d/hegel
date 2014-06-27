package com.shomop.http.rsp.resultset.handler;

import org.apache.http.client.ResponseHandler;

import com.shomop.http.req.client.SimpleHttpClient;
/**
 * @see ResponseHandler
 * @author spencer.xue
 * @date 2014-5-6
 */
public interface RspHandler<T> extends ResponseHandler<T> {

	
	<K extends SimpleHttpClient> void setHttpClient(K httpClinet);
}
