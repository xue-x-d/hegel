package com.shomop.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shomop.http.factory.HttpClientFactory;
import com.shomop.util.Digest;
import com.shomop.util.sendRequestUtils;
import com.taobao.api.internal.util.TaobaoUtils;

@Controller()
@RequestMapping(value="/oauth")
@SuppressWarnings("unchecked")
public class OAuth {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String APP_ID = "XXXXX";
	
	private static final String APP_SECRET = "XXXXX";
	
	private static final String AUTH_CODE_URL_PREFIX = "http://oauth.dangdang.com/authorize?";
	
	private static final String AUTH_TOKEN_URL_PREFIX = "http://oauth.dangdang.com/token?";
	
	// 对于Server-side flow，redirect_uri是必选参数，并且要求redirect_uri与callback的顶级域名一致。
	private static final String REDIRECT_URL = "http://115.239.210.27:9090/jd-service/oauth/callback";
	
	private static final Map<String,String> req_params = new HashMap<String,String>();
	
	static {
		req_params.put("appId",APP_ID);
		req_params.put("redirectUrl",REDIRECT_URL);
		req_params.put("responseType","code");
		req_params.put("state","shomop");
		req_params.put("view","web");
	}
	
	private static HttpClient httpClient = HttpClientFactory.getHttpClient(3000,3000);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void oauth(HttpServletResponse rsp){
		try {
			String query = sendRequestUtils.buildQuery(req_params,sendRequestUtils.DEFAULT_CHARSET);
			logger.info(query);
			rsp.sendRedirect(AUTH_CODE_URL_PREFIX+query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public ModelAndView oauthCallback(String code,String state){
		// TODO 判断state
		ModelAndView view = new ModelAndView();
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("appId",APP_ID);
			params.put("grantType","code");
			params.put("code",code);
			params.put("appSecret",APP_SECRET);
			// TODO 到底什么意思 仅仅是为了验证
			params.put("redirectUrl",REDIRECT_URL);
			params.put("state","shomop");
			params.put("view","web");
			URL rul = sendRequestUtils.buildUrl(AUTH_TOKEN_URL_PREFIX,sendRequestUtils.buildQuery(params,sendRequestUtils.DEFAULT_CHARSET));
			HttpPost postMethod = new HttpPost();
			postMethod.setURI(rul.toURI());
			HttpResponse rsp = httpClient.execute(postMethod);
			HttpEntity entity = rsp.getEntity();
			Map<String,?> result =(Map<String,?>)TaobaoUtils.parseJson(EntityUtils.toString(entity,EntityUtils.getContentCharSet(entity)));
			for (String key : result.keySet()) {
				 System.out.println(key + "——>" + result.get(key));
			}
			view.addObject("map",result);
			view.setViewName("oauth_success");
		} catch (Exception e) {
			e.printStackTrace();
			view.setViewName("oauth_failed");
		}
		return view;
	}
	
	/**
	 * 刷新accessToken
	 * @param refreshToken
	 */
	@RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
//	@ResponseBody
	public void refreshToken(@RequestParam String refreshToken,PrintWriter writer){
		StringBuffer params = new StringBuffer(AUTH_TOKEN_URL_PREFIX);
		params.append("appId").append("=").append(APP_ID).append("&");
		params.append("grantType").append("=").append("token").append("&");
		params.append("refreshToken").append("=").append(refreshToken);
		HttpPost postMethod = new HttpPost(params.toString());
		HttpResponse rsp;
		try {
			rsp = httpClient.execute(postMethod);
			HttpEntity entity = rsp.getEntity();
			String body = EntityUtils.toString(entity,EntityUtils.getContentCharSet(entity));
			Map<String,String> result =(Map<String,String>)TaobaoUtils.parseJson(body);
			if (oauthSign(result)) {
				for (String key : result.keySet()) {
					 System.out.println(key + "——>" + result.get(key));
				}
			}else{
				logger.error("refresh failed.");
			}
			// 返回值void 返回到请求对应的jsp
			writer.write(body);
//			return body;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return "";
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public String refreshToken(){
		
		return "refresh";
	}
	
	/**
	 * 拼接必须按照顺序
	 * shop_id，app_id，edition_id，edition_end_date
	 * 按此顺序拼接参数名+参数值
	 * @param result
	 * @return
	 */
	private boolean oauthSign(Map<String,String> result){
		if(result == null){
			return false;
		}
		StringBuffer params = new StringBuffer();
		params.append(APP_SECRET);
		String shopId = result.get("shop_id") == null ? "" : result.get("shop_id");
		params.append("shop_id").append(shopId);
		
		String appId = result.get("app_id") == null ? "" : result.get("app_id");
		params.append("app_id").append(appId);
		
		String editionId = result.get("edition_id") == null ? "" : result.get("edition_id");
		params.append("edition_id").append(editionId);
		
		String editionEndDate = result.get("edition_end_date") == null ? "" : result.get("edition_end_date");
		params.append("edition_end_date").append(editionEndDate);
		
		params.append(APP_SECRET);
		String sign = Digest.md5(new String(params.toString().getBytes(),Charset.forName("UTF-8")));
		return sign.equals(result.get("sign"));
	}
	
    public static void main(String[] args) {
	    
	}
	
}
