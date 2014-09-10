package com.shomop.dd.sdk;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;

public class DDClient implements TaobaoClient {

	private final String appKey;
	private final String appSecret;
	private final String serverUrl;
	
	public DDClient(String serverUrl, String appKey, String appSecret){
		this.serverUrl = serverUrl;
    	this.appKey = appKey;
    	this.appSecret = appSecret;
    }
	
	public String getAppKey() {
		return appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	
	@Override
	public <T extends TaobaoResponse> T execute(TaobaoRequest<T> arg0)
			throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends TaobaoResponse> T execute(TaobaoRequest<T> arg0,
			String arg1) throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}
}
