

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
		String[] array = {"a","b"};
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", "诸葛亮");
		jsonObject.put("id", "1");
//		jsonObject.put("array",array);
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
	
}
