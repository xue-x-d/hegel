package com.shomop.crm.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RefreshTokenServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2618851549452127539L;

	private static final String AUTH_TOKEN_URL_PREFIX = "https://auth.360buy.com/oauth/token?";

	private static String params = "client_id=A26A075F7B5C7827BC3EBD318E836506&client_secret=37fb9f271c764ccba06b488627b6896a&grant_type=refresh_token&refresh_token=";
	
	private volatile String _token = "f743e560-7a29-4d75-a5d4-216e2ec80e06";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String newToken = req.getParameter("token");
		if(newToken == null || "".equals(newToken)){
			resp.sendRedirect("index.jsp");
			return;
		}
		resp.setCharacterEncoding("gbk");
		resp.setHeader("content-type","text/html;charset=gbk");
		PrintWriter writer = resp.getWriter();
		try {
			writer.println(handleRefreshToken(newToken));
			_token = newToken;
		} catch (IOException e) {
			writer.println("刷新失败！"+e.getMessage());
		}finally{
			writer.flush();
			writer.close();
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	
	public void updateAccessToken(){
		RefreshTokenServlet refreshTokenServlet = new RefreshTokenServlet();
		try {
			refreshTokenServlet.handleRefreshToken(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String handleRefreshToken(String token) throws IOException {
		if (token == null || "".equals(token.trim())) {
			token = _token;
		}
		URL url = new URL(AUTH_TOKEN_URL_PREFIX + params + token);
		HttpsURLConnection connHttps = null;
		try {
			connHttps = (HttpsURLConnection) url.openConnection();
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0],
					new DefaultTrustManager[] { new DefaultTrustManager() },
					new SecureRandom());
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			connHttps.setRequestMethod("POST");
			connHttps.setDoInput(true);
			connHttps.setDoOutput(false);
			connHttps.setConnectTimeout(3000);
			connHttps.setReadTimeout(2000);
			connHttps.setRequestProperty("content-type","text/html;charset=utf-8");
			connHttps.setRequestProperty("accept", "application/json");
//			connHttps.setRequestProperty("accept-charset","utf-8");
			String result = getResponseAsString(connHttps);
			System.out.println("jd refresh result: " + result);
			return result;
		} catch (Exception e) {
			throw new IOException("刷新请求失败：" + e.getMessage());
		} finally {

		}
	}
	
	private String getResponseAsString(HttpURLConnection conn)
			throws IOException {
		//application/json;charset=gbk
		String charset = "gbk";
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsString(conn.getInputStream(), charset);
		}
		String msg = getStreamAsString(es, charset);
		if (msg == null || "".equals(msg.trim())) {
			throw new IOException(conn.getResponseCode() + ":"
					+ conn.getResponseMessage());
		}
		throw new IOException(msg);
	}
	
	private static String getStreamAsString(InputStream stream, String charset)
			throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
			StringWriter writer = new StringWriter();
			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}
			String str = writer.toString();
			return str;
		} finally {
			if (stream != null)
				stream.close();
		}
	}
	
	public class DefaultTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	}
}
