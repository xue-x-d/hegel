package com.shomop.util.test;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.shomop.crm.model.crm.User;
import com.shomop.http.factory.HttpClientFactory;

public class TestHttpClient {

	// 使用gson
	private static Gson gson;
	private static HttpClient httpClient;
	private static final String url ="http://localhost:8080/jd-service/req/login.do";
	private static final String JSON_MIME = "application/json";
	
	@BeforeClass    
    public static void beforeClass() {     
		httpClient = HttpClientFactory.getHttpClient();
		gson = new Gson();
    }
    
	//@Test
	public void testAjax() {
		// post
		HttpPost httpPost = new HttpPost(url);
		try {
			// 使用gson转换(一定要指定编码格式)
			StringEntity inputEntity = new StringEntity(getJSONString(),JSON_MIME,HTTP.UTF_8);
			// 方式2 from source
//			entity.setContentType(JSON_MIME+HTTP.CHARSET_PARAM+HTTP.UTF_8);
			httpPost.setEntity(inputEntity);
			HttpResponse rsp = httpClient.execute(httpPost);
			// response
			StatusLine status = rsp.getStatusLine();
			if (status.getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
				// close stream
				EntityUtils.consume(rsp.getEntity());
				throw new HttpResponseException(status.getStatusCode(),
						status.getReasonPhrase());
			}
			HttpEntity entity = rsp.getEntity();
			String charset = EntityUtils.getContentCharSet(entity) == null ? HTTP.UTF_8
					: EntityUtils.getContentCharSet(entity);
			// rsp body
			String rspBody = EntityUtils.toString(entity,charset);
			System.out.println("rsp body： "+rspBody);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getGsonString() {
		User user = new User();
		user.setId("1");
		user.setUsername("shomop");
		return gson.toJson(user);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	private String getJSONString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", "诸葛亮");
		jsonObject.put("id", "1");
//		jsonObject.put("array",new String[]{"a","b"});
		return jsonObject.toString();
	}
	
	//@Test
	public void sendsms() {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			String[] tels = new String[1];
			tels[0] = "13065708660";
			JSONObject jobj = new JSONObject();
			jobj.put("username", "shomop");
			jobj.put("id", "1");
			// String jsonArr =
			// "{\"username\":\"justucc\",\"password\":\"j20140822U\",\"mobiles\":[\"15058191683\"],\"content\":\"尊敬的客户您好，您的验证码是123456\"}";
			// System.out.println(jsonArr);
			StringEntity se = new StringEntity(jobj.toString());
			se.setContentType("application/json");
			post.setEntity(se);
			HttpResponse res = client.execute(post);
			String result = EntityUtils.toString(res.getEntity());
			System.out.println(result);
			int code1 = res.getStatusLine().getStatusCode();
			System.out.println(code1);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSession() {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://localhost:8080/jd-service/req/session?id=refuser&username=xxd");
		try {
			HttpResponse res = client.execute(get);
			int code1 = res.getStatusLine().getStatusCode();
			System.out.println(code1);
			String result = EntityUtils.toString(res.getEntity());
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		JSONObject jsonObject = new JSONObject("{\"extends\":[{\"content\":\"尊敬的用户：您有一条锁定订单，请您关注。订单号为：1234567898，下单时间为：2014-08-28 14:37:33 000：收货人为：张三，订单金额为：5090.0。请您及时处理，谢谢。\",\"pin\":\"zhangsan\",\"time\":\"2014-08-28 14:47:05 860\",\"type\":\"orderlock\",\"venderid\":\"12345\"}]}");
		System.out.println(jsonObject.opt("extends"));
		User user = new User();
		user.setUsername("张飞");
		List<Object> params = new ArrayList<Object>();
		params.add("1");
		params.add("2");
		user.setParams(params);
		JSONObject jo = new JSONObject(user);
		System.out.println(jo.toString());
		String jsonStr = "{\"sid\":0,\"username\":\"张\\t飞\",\"subscribeBriefing\":false,\"mktBalance\":0,\"isPermit\":false,\"lastDownBuyerTime\":0,\"userId\":0,\"appBalance\":0,\"params\":[\"1\",\"2\"],\"deleted\":false,\"purchaseVersion\":0}";
		System.out.println(jsonStr);
		JSONObject jo2 = new JSONObject(jsonStr);
		for (Object object : jo2.keySet()) {
			System.out.println(object+"  "+jo2.optString(object.toString()));
		}
		
	}
	
}
