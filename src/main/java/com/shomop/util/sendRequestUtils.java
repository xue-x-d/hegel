package com.shomop.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import com.shomop.http.factory.HttpClientFactory;
import com.shomop.http.req.client.SimpleHttpClient;
import com.shomop.http.rsp.resultset.RspResult;
import com.shomop.http.rsp.resultset.handler.RspHandler;
/**
 * 发送http请求
 * @author spencer.xue
 * @date 2013-7-17
 */
public class sendRequestUtils {
	
	private static final Log logger = LogFactory.getLog(sendRequestUtils.class);
	
	/** 默认响应编码格式*/
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 向指定URL发送POST方法的请求
	 * JSON 格式参数
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader reader = null;
		StringWriter writer = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn =(HttpURLConnection)realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json");
			// 发送POST请求必须设置
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 读取URL的响应
//			System.out.println("code: "+conn.getResponseCode());
//			System.out.println("ResponseMessage: "+conn.getResponseMessage());
			// response headers (content-type:text/html; charset=UTF-8)
			String charset = getResponseCharset(conn.getContentType());
			InputStream is = conn.getInputStream();
			if (is.available() > 0) {
				reader = new BufferedReader(new InputStreamReader(is,charset));
				writer = new StringWriter();
				char[] chars = new char[256];
				int count = 0;
				while ((count = reader.read(chars)) > 0) {
					writer.write(chars, 0, count);
				}
				return writer.toString();
			}
			return "server has gone away.";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return "error";
	}
	
	/**
	 * response headers ——> Content-Type
	 * 如果指定编码，使用指定编码默认UTF-8
	 * @param ctype
	 * @return
	 */
	private static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;
		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}
		return charset;
	}
	
	/**
	 * 发送get请求
	 * @param url
	 * @param queryParams
	 * @param clazz
	 */
	public static <T extends SimpleHttpClient> RspResult getMethod(T httpClient,String url,Map<String,String> queryParams) {
		if(httpClient == null){
			throw new NullPointerException("http client should not be null.");
		}
		HttpGet httpGet = null;
		try {
			String requestCharset =(String)httpClient.getParams().getParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET);
			URL rul = buildUrl(url,buildQuery(queryParams,requestCharset));
			// 创建 httpGet 实例
			httpGet = httpClient.getMethod();
			httpGet.setURI(rul.toURI());
			RspHandler<RspResult> rspHandler = httpClient.getResponseHandler();
			RspResult resultSet = httpClient.execute(httpGet, rspHandler);
			return resultSet;
		} catch (Exception e) {
			e.printStackTrace();
			httpGet.abort();
		}
		return new RspResult();
	} 
	
	
	/**
	 * post 请求
	 * @param url
	 * @param postBody 请求体内容 可以为空
	 */
	public static void postMethod(String url,Map<String,String> postBody){
		// 创建一个默认的HttpClient
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		try {
			// 以post方式请求URL
			HttpPost httpPost = new HttpPost(url);
			// 添加HTTP POST参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (postBody == null || postBody.size() == 0) {
				logger.warn("post body was null! url: " + url);
			} else {
				for (Entry<String,String> entry : postBody.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			// 将POST参数以UTF-8编码并包装成表单实体对象
			httpPost.setEntity(new UrlEncodedFormEntity(nvps,DEFAULT_CHARSET));
            // 发送请求  
            HttpResponse rsp = httpClient.execute(httpPost);
            int statusCode = rsp.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				for(Header header : rsp.getAllHeaders()){
					System.out.println(header.getName()+" : "+header.getValue());
				}
				// 获取返回数据
				HttpEntity entity = rsp.getEntity();
				String charset = EntityUtils.getContentCharSet(entity) == null ? DEFAULT_CHARSET : EntityUtils.getContentCharSet(entity);
				String body = EntityUtils.toString(entity,charset);
				System.out.println(body);
			}
			// HttpClient对于要求接受后继服务的请求，POST和PUT等不能自动处理转发 Location:/v3
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY
					|| statusCode == HttpStatus.SC_SEE_OTHER
					|| statusCode == HttpStatus.SC_TEMPORARY_REDIRECT) {
				// 从头中取出转向的地址
				Header locationHeader = rsp.getLastHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					if (StringUtils.isEmpty(location)) {
						location = "/";
						HttpGet redirect = new HttpGet(location);
						HttpResponse rsp2 = httpClient.execute(redirect);
						System.out.println("Redirect:"+ rsp2.getStatusLine().toString());
					} else {
						System.out.println("Invalid redirect");
					}
				}
			}
			//处理服务器响应内容 EntityUtils.toString 默认编码  HTTP.ISO-8859-1
//			BasicResponseHandler rspHandler = new BasicResponseHandler();
//			String responseBody = httpClient.execute(httpPost, rspHandler);
//			System.out.println(new String(responseBody.getBytes(HTTP.ISO_8859_1), HTTP.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		    // 连接服务器失败
//			FIXME rspHandler.set
		} finally {
//			httpClient.getConnectionManager().shutdown();
		}
		
	}
	
	
	/**
	 * 将请求参数按照http协议标准拼接
	 * @param strUrl
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public static URL buildUrl(String strUrl, String query) throws IOException {
		URL url = new URL(strUrl);
		if (StringUtils.isEmpty(query)) {
			return url;
		}
		if (StringUtils.isEmpty(url.getQuery())) {
			if (strUrl.endsWith("?"))
				strUrl = strUrl + query;
			else {
				strUrl = strUrl + "?" + query;
			}
		} else if (strUrl.endsWith("&")){
			strUrl = strUrl + query;
		} else {
			strUrl = strUrl + "&" + query;
		}
		return new URL(strUrl);
	}
	
	/**
	 * 构造请求url格式附带参数编码
	 * @param params
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String buildQuery(Map<String,String> params,String charset) throws IOException {
		if ((params == null) || (params.isEmpty())) {
			return null;
		}
		StringBuilder query = new StringBuilder();
		Set<Entry<String,String>> entries = params.entrySet();
		boolean hasParam = false;
		for (Entry<String,String> entry : entries) {
			String name = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (areNotEmpty(new String[] { name, value })) {
				if (hasParam){
					query.append("&");
				} else {
					hasParam = true;
				}
				query.append(name).append("=").append(URLEncoder.encode(value,charset));
			}
		}
		return query.toString();
	}
	
	private static boolean areNotEmpty(String[] values) {
		boolean result = true;
		if ((values == null) || (values.length == 0))
			result = false;
		else {
			for (String value : values) {
				result &= !(StringUtils.isEmpty(value));
			}
		}
		return result;
	}
	
	public static void main(String args[]) {
		
        long start = System.currentTimeMillis();
		 
		System.out.println((System.currentTimeMillis() - start)+" ms"); 
	}
}
