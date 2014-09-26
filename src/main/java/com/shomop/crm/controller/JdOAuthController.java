package com.shomop.crm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shomop.crm.service.UserManager;
import com.shomop.http.factory.HttpClientFactory;
import com.shomop.jd.sdk.JDClient;
import com.shomop.util.sendRequestUtils;
import com.taobao.api.internal.util.TaobaoUtils;

@Controller
@RequestMapping(value="/jd")
public class JdOAuthController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JDClient JdClient;
	@Autowired
	private UserManager userManager;
	
	private static final String AUTH_CODE_URL_PREFIX = "https://auth.360buy.com/oauth/authorize?";
	private static final String AUTH_TOKEN_URL_PREFIX = "https://auth.360buy.com/oauth/token?";
	// callback的顶级域名一致。
	private static final String REDIRECT_URL = "http://125.119.156.105:9090/jd-service/jd/callback";
	
	private static HttpClient httpClient = HttpClientFactory.getHttpClient(3000,3000);
	
	/**
	 * response_type 必须 此流程下，该值固定为code 
	 * client_id 必须 即创建应用时的Appkey（从JOS控制台->管理应用中获取） 
	 * redirect_uri 必须 即应用的回调地址，必须与创建应用时所填回调页面url一致 
	 * state 可选 状态参数，由ISV自定义，颁发授权后会原封不动返回 
	 * scope 可选 权限参数，API组名串。多个组名时，用"，"分隔，目前支持参数值：read
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String oauth(){
		StringBuilder pathParams = new StringBuilder();
		pathParams.append("response_type").append("=").append("code")
			.append("&").append("client_id").append("=").append(JdClient.getAppKey())
			.append("&").append("redirect_uri").append("=").append(REDIRECT_URL)
			.append("&").append("state").append("=").append("shomop");
		try {
			String _params = URLEncoder.encode(pathParams.toString(),"utf-8");
			return "redirect:" + AUTH_CODE_URL_PREFIX + _params;
		} catch (UnsupportedEncodingException e) {
			logger.error("授权地址编码失败.....");
		}
		return "oauth_failed";
	}
	
	/**
	 * grant_type 必须 授权类型，此流程下，该值固定为authorization_code 
	 * code 必须 授权请求返回的授权码
	 * redirect_uri 必须 应用的回调地址，必须与创建应用时所填回调页面url一致 
	 * client_id 必须
	 * client_secret 必须
	 * state 可选 状态参数，由ISV自定义，颁发授权后会原封不动返回
	 */
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public String callBack(@RequestParam(required = false) String code,
			@RequestParam(required = false) String state,
			@RequestParam(required = false) String error, Model model) {
		model.addAttribute("version","jd");
		if(StringUtils.isBlank(code) && StringUtils.isNotBlank(error)){
			model.addAttribute("error","您取消了授权");
		}else if(StringUtils.isNotBlank(code)){
			Map<String,String> params = new HashMap<String,String>();
			params.put("grant_type","authorization_code");
			params.put("code",code);
			// TODO 到底什么意思 仅仅是为了验证
			params.put("redirectUrl",REDIRECT_URL);
			params.put("client_id",JdClient.getAppKey());
			params.put("client_secret",JdClient.getAppSecret());
			params.put("state","shomop");
			try {
				URL rul = sendRequestUtils.buildUrl(AUTH_TOKEN_URL_PREFIX,
						sendRequestUtils.buildQuery(params,
								sendRequestUtils.DEFAULT_CHARSET));
				HttpPost postMethod = new HttpPost();
				postMethod.setURI(rul.toURI());
				HttpResponse rsp = httpClient.execute(postMethod);
				HttpEntity entity = rsp.getEntity();
				Map<String, ?> result = (Map<String, ?>) TaobaoUtils
						.parseJson(EntityUtils.toString(entity));
				/*
				 * uid：授权用户对应的京东ID 
				 * user_nick：授权用户对应的京东昵称
				 * expires_in：失效时间（从当前时间算起，单位：秒） 
				 * time：授权的时间点（UNIX时间戳，单位：毫秒）
				 * token_type：token类型（暂无意义）
				 */
				for (String key : result.keySet()) {
					System.out.println(key + "——>" + result.get(key));
				}
				model.addAttribute("map", result);
				return "oauth_success";
			} catch (IOException | URISyntaxException e) {
				logger.error("获取授权信息失败......");
			}
		}
		return "oauth_failed";
	}
	
	
	
	
}