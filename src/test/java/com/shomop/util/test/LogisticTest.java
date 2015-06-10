package com.shomop.util.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.jd.open.api.sdk.internal.JSON.JSON;
import com.jd.open.api.sdk.internal.parser.JsonParser;
import com.jd.open.api.sdk.internal.parser.Parser;
import com.shomop.util.sendRequestUtils;

public class LogisticTest {


	private static final HttpClient httpClient = new DefaultHttpClient();;
	private static final String JSON_MIME = "application/json";
	private static final String URL = "http://api.open.baidu.com/pae/channel/data/asyncqury";

	@Test
	public void testBaiduWuliu() throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("cb", "jQuery11020529261679854244_1419846496282");
		params.put("appid", "4001");
		params.put("com", "shentong");
		params.put("nu","968515326442");
		params.put("_",System.currentTimeMillis()+"");
		String queryParams = sendRequestUtils.buildQuery(params, HTTP.UTF_8);
		URL rul = sendRequestUtils.buildUrl(URL,queryParams);
		HttpGet httpGet = new HttpGet(rul.toURI());
		httpGet.setHeader("Cookie","Cookie:SSUDB=dmb1IwT3pSeVRBLTkxZFdyRVJHc0IxV0JxaFI5MXpSakcxOGViTVgzUHdIMmxSQVFBQUFBJCQAAAAAAAAAAAEAAADn-SYKNzEwNzA5NTEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPCSQVHwkkFRak; BDUT=jtzsA40AACBA560FB89412A3CAAA9979A2AD13d68a87e4c0; BAIDUPSID=0F4739F92FD0A14722BFD4D2E47F881C; BDUSS=GtHMFRQWlR2SUM0bGdsdn52VHRNVDJkc01rMkxJMEtzendndkFURjBycWt1b3BVQVFBQUFBJCQAAAAAAAAAAAEAAADn-SYKNzEwNzA5NTEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKQtY1SkLWNUd; __zpspc=188.6.1418703402.1418703402.1%233%7Cwww.google.com.hk%7C%7C%7C%7C%23; BAIDUID=3E0F993D2179DF11C0054A42C894AC28:FG=1; MCITY=-%3A; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; H_PS_PSSID=1428_10406_10491_10874_10503_10501_10497_10753_10645_10458_10795_10219_10356_10666_10095_10657_10442_10699_10461_10761_10360_10619_10626");
		httpGet.setHeader("Referer","http://www.baidu.com/s?ie=utf-8&wd=%E7%89%A9%E6%B5%81");
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36");
		// response
		HttpResponse rsp = httpClient.execute(httpGet);
		StatusLine status = rsp.getStatusLine();
		if (status.getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
			// close stream
			EntityUtils.consume(rsp.getEntity());
			throw new HttpResponseException(status.getStatusCode(),
					status.getReasonPhrase());
		}
		HttpEntity entity = rsp.getEntity();
		String body = EntityUtils.toString(entity);
		body = body.substring(body.indexOf("(")+1,body.lastIndexOf(")"));
		System.out.println(JSON.toString(JSON.parse(body)));
		/*String charset = EntityUtils.getContentCharSet(entity);
		String contentType = EntityUtils.getContentMimeType(entity);
		System.out.println(charset +"  "+contentType);
		if (JSON_MIME.equalsIgnoreCase(contentType)) {
			String body = EntityUtils.toString(entity);
			body = body.substring(body.indexOf("(")+1,body.lastIndexOf(")"));
			System.out.println(JSON.toString(JSON.parse(body)));
		}*/
	}

	public static Map<Object,String> toMap(String jsonString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		Map<Object,String> result = new HashMap<Object,String>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;

	}
	
	public static void main(String[] args) {
		String body = "\"fullname\":\"\u7533\u901a\u5feb\u9012\"";
		Pattern message_pattern =  Pattern.compile("([\u4E00-\u9FA5]){1}",Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		Matcher matcher = message_pattern.matcher(body);
		while (matcher.find()) {
			System.out.println(matcher.group(0));
		}
		System.out.println("\u5feb");
	}


}
