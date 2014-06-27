package com.shomop.http.req.client;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.shomop.http.factory.HttpClientFactory;
import com.shomop.http.rsp.resultset.RspJsonForm;
import com.shomop.http.rsp.resultset.RspResult;
import com.shomop.http.rsp.resultset.handler.RspHandler;
import com.shomop.http.rsp.resultset.handler.impl.DefaultRspHandler;

/**
 * enhanced HttpClient
 * inclode default request header and response handler
 * @author spencer.xue
 * @date 2014-5-8
 */
public abstract class SimpleHttpClient extends DefaultHttpClient{
	/**
	 * json response form
	 * default null
	 */
	protected RspJsonForm rspJsonForm;
	
	public SimpleHttpClient(){
		super();
		initRspForm();
	}
	
	public SimpleHttpClient(final ClientConnectionManager conman){
		super(conman, null);
		initRspForm();
	}
	
	/**
	 * if response type is json
	 * please override this method
	 * otherwise empty method body
	 */
	abstract void initRspForm();
	
	/**
	 * response resolver
	 * @return
	 */
	public RspHandler<RspResult> getResponseHandler(){
		RspHandler<RspResult> handler = new DefaultRspHandler();
		handler.setHttpClient(this);
		return handler;
	}
	
	/**
	 * 返回 httpClient connection 相关的参数集合
	 */
    protected HttpParams createHttpParams() {
    	HttpParams params = new BasicHttpParams();
    	setHttpParams(params);
    	return params;
    }
    
	/**
	 * 连接参数设置{@link CoreConnectionPNames}
	 */
	protected void setHttpParams(HttpParams params){
		setDefaultHttpParams(params);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,HttpClientFactory.CONNECT_TIMEOUT);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, HttpClientFactory.READ_TIMEOUT);
		// HttpConnectionParams.setConnectionTimeout(params,HttpClientFactory.WAIT_TIMEOUT);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,HttpClientFactory.WAIT_TIMEOUT);
		params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,HTTP.UTF_8);
		params.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
	}
	
	public final HttpGet getMethod() {
		HttpGet httpGet = new HttpGet();
		setHeaders(httpGet);
		return httpGet;
	}
	
	public final HttpPost postMethod() {
		HttpPost httpGet = new HttpPost();
		setHeaders(httpGet);
		return httpGet;
	}
	
	/**
	 * default request header setting
	 * 遵循RFC2616中定义的规范{@link http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html}
	 * @param httpRequest
	 */
	protected void setHeaders(HttpRequestBase httpRequest) {
		String reqCharset =(String)getParams().getParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET);
		// 表单提交
		String ctype = "application/x-www-form-urlencoded;charset="+reqCharset;
		httpRequest.setHeader("Content-Type", ctype);
		// 可以处理的类型
		httpRequest.setHeader("Accept","application/json,text/plain,text/xml,text/javascript,text/html");
		// 模拟浏览器
		httpRequest.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36");
//		httpRequest.setHeader("Connection", "close");
	}
	
	/**
	 * 压缩request(zip)
	 */
	public void zipRequest(HttpRequestBase httpRequest) {
		httpRequest.addHeader("Accept-Encoding", "gzip");
	}
	
	/**
	 * 解压response(zip)
	 */
	public void unzipResponse(HttpResponse httpResponse) {
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			Header ceheader = entity.getContentEncoding();
			if (ceheader != null) {
				HeaderElement[] codecs = ceheader.getElements();
				for (int i = 0; i < codecs.length; i++) {
					if (codecs[i].getName().equalsIgnoreCase("gzip")) {
						httpResponse.setEntity(new GzipDecompressingEntity(httpResponse.getEntity()));
						return;
					}
				}
			}
		}
	}
	 
	/**
	 * get response json form
	 * @return
	 */
	public RspJsonForm getRspJsonForm() {
		return rspJsonForm;
	}
	
 
}
