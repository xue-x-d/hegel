package com.shomop.jd.sdk;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.internal.parser.Parser;
import com.jd.open.api.sdk.internal.parser.ParserFactory;
import com.jd.open.api.sdk.internal.util.CodecUtil;
import com.jd.open.api.sdk.internal.util.HttpUtil;
import com.jd.open.api.sdk.internal.util.StringUtil;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.request.JdUploadRequest;
import com.jd.open.api.sdk.response.AbstractResponse;

/**
 * 改造JDClient方便注入
 * @author spencer.xue
 * @date 2014-9-26
 */
@SuppressWarnings("unchecked")
public class JDClient implements JdClient{

	private static final String CHARSET_UTF8 = "UTF-8";
	private static final String JSON_PARAM_KEY = "360buy_param_json";
	private static final String OTHER_PARAM_KEY = "other";
	private final String serverUrl;
	private final String appKey;
	private final String appSecret;
	private final int connectTimeout;
	private final int readTimeout;

	public JDClient(String serverUrl, String appKey,
			String appSecret) {
		this.connectTimeout = 3000;
		this.readTimeout = 15000;
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

	@SuppressWarnings("rawtypes")
	public <T extends AbstractResponse> T execute(JdRequest<T> request,String session)
			throws JdException {
		try {
			String url = buildUrl(request,session);
			Map<String,String> params = new HashMap<String,String>();
			String json = request.getAppJsonParams();
			params.put(JSON_PARAM_KEY, json);
			if (request.getOtherParams() != null) {
				params.put(OTHER_PARAM_KEY, request.getOtherParams());
			}
			String rsp = null;
			if (request instanceof JdUploadRequest) {
				rsp = HttpUtil.doPost(url, params,
						((JdUploadRequest) request).getFileParams(),
						this.connectTimeout, this.readTimeout);
			} else {
				rsp = HttpUtil.doPost(url, params, this.connectTimeout,
						this.readTimeout);
			}
			T resp = parse(rsp, request.getResponseClass());
			StringBuffer sb = new StringBuffer();
			sb.append(url).append("&").append("360buy_param_json").append("=")
					.append(json);
			resp.setUrl(sb.toString());
			return resp;
		} catch (Exception e) {
			throw new JdException("服务器连接超时，请重试");
		}
	}

	private <T extends AbstractResponse> String buildUrl(JdRequest<T> request,String session)
			throws Exception {
		Map<String,String> sysParams = request.getSysParams();
		Map<String,String> pmap = new TreeMap<String,String>();
		pmap.put(JSON_PARAM_KEY, request.getAppJsonParams());
		sysParams.put("method", request.getApiMethod());
		sysParams.put("access_token", session);
		sysParams.put("app_key", this.appKey);
		pmap.putAll(sysParams);
		String sign = sign(pmap, this.appSecret);
		sysParams.put("sign", sign);
		StringBuilder sb = new StringBuilder(this.serverUrl);
		sb.append("?");
		sb.append(HttpUtil.buildQuery(sysParams, CHARSET_UTF8));
		return sb.toString();
	}

	private <T extends AbstractResponse> T parse(String rsp,
			Class<T> responseClass) throws JdException {
		Parser parser = null;
		if (this.serverUrl.endsWith("json"))
			parser = ParserFactory.getJsonParser();
		else {
			parser = ParserFactory.getXmlParser();
		}

		return parser.parse(rsp, responseClass);
	}

	private String sign(Map<String, String> pmap, String appSecret)
			throws Exception {
		StringBuilder sb = new StringBuilder(appSecret);
		for (Map.Entry<String,String> entry : pmap.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			if (StringUtil.areNotEmpty(new String[] { name, value })) {
				sb.append(name).append(value);
			}
		}
		sb.append(appSecret);
		String result = CodecUtil.md5(sb.toString());
		return result;
	}

	@Override
	public <T extends AbstractResponse> T execute(JdRequest<T> paramJdRequest)
			throws JdException {
		 
		return null;
	}
 
}
