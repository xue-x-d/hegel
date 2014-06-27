package com.shomop.http.rsp.resultset.handler.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.shomop.http.req.client.SimpleHttpClient;
import com.shomop.http.rsp.resultset.ResultFlag;
import com.shomop.http.rsp.resultset.RspResult;
import com.shomop.http.rsp.resultset.RspType;
import com.shomop.http.rsp.resultset.handler.RspHandler;

/**
 * default handler 
 * {@link RspType#PLAIN_HTML_TYPE} 
 * @author spencer.xue
 * @date 2014-5-5
 */
@SuppressWarnings("unused")
public class DefaultRspHandler implements RspHandler<RspResult> {
	
	private static final Log log = LogFactory.getLog(DefaultRspHandler.class);
	
	private SimpleHttpClient httpClient;
	
	@Override
	public <K extends SimpleHttpClient> void setHttpClient(K httpClinet) {
		httpClient = httpClinet;
	}

	@Override
	public RspResult handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		if(httpClient == null){
			throw new NullPointerException("httpClient");
		}
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		// 返回结果
		RspResult rspResult = new RspResult();
		// 大于400 出错
		if (statusLine.getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
			rspResult.setFlag(ResultFlag.RSP_SERVER_FAILED);
			EntityUtils.consume(entity);
			throw new HttpResponseException(statusLine.getStatusCode(),
					statusLine.getReasonPhrase());
		}
		rspResult.setFlag(ResultFlag.RSP_SERVER_SUCCESS);
		if(entity == null){
			return rspResult;
		}
		// 判断server响应是否启用了压缩 Content-Encoding:gzip
		httpClient.unzipResponse(response);
		// 返回的编码格式默认UTF-8
		String charset = EntityUtils.getContentCharSet(entity) == null ? HTTP.UTF_8
				: EntityUtils.getContentCharSet(entity);
		// 响应体
		String rspBody = EntityUtils.toString(entity, charset);
		// 判断返回文档类型 Content-Type:text/html; charset=UTF-8
		String contentType = EntityUtils.getContentMimeType(entity);
		// 普通格式
		if (RspType.PLAIN_TEXT_TYPE.equalsIgnoreCase(contentType)
				|| RspType.PLAIN_HTML_TYPE.equalsIgnoreCase(contentType)) {
			rspResult.setFlag(ResultFlag.RSP_RESULT_SUCCESS);
			Map<String,String> resultSet = new HashMap<String,String>();
			resultSet.put(RspResult.RESULT_KEY,rspBody);
		} else {
			// 没有返回约定的格式
			rspResult.setFlag(ResultFlag.RSP_SERVER_UNKNOWN);
		}
		return rspResult;
	}


}
