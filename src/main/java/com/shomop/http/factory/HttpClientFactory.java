package com.shomop.http.factory;

import java.lang.reflect.Constructor;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;

import com.shomop.http.req.client.SimpleHttpClient;

/**
 * httpclient连接池
 * TIME_WAIT The socket is waiting after close to handle packets still in the network. 
 * CLOSE_WAIT The remote end has shut down, waiting for the socket to close.
 * @author spencer.xue
 * @date 2014-5-5
 * http://stackoverflow.com/questions/10673517/setmaxforroute-does-not-work-in-threadsafeclientconnmanager
 */
@SuppressWarnings("unused")
public class HttpClientFactory {

	private static final Log log = LogFactory.getLog(HttpClientFactory.class);
	
	private static ThreadSafeClientConnManager cm;
	
	private static ScheduledExecutorService scheduler;
	
//	private static HttpParams params;
	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 100;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 60000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 20;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 5000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 3000;
	/**
	 * 关闭空闲连接时间
	 */
	private static final int IDLE_TIMEOUT = 1000;
	
	private static final int PORT = 80;
	private static final int HTTPS_PORT = 443;
	
	/**
	 * 检查空闲连接开始延迟时间
	 */
	private static final long INITIAL_DELAY = 200*1000;
	/**
	 * 每次检查间隔时间
	 */
	private static final long DELAY = 60000;
	/**
	 * 特例查询短链接地址点击率
	 */
	private static final String DWZ_URL = "somo.so";
	
	private static final int DWZ_URL_MAX_CONNECTIONS = 50;

	static {
		try {
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PORT, PlainSocketFactory.getSocketFactory()));
			SSLContext ssl_ctx = SSLContext.getInstance("TLS");
			TrustManager[] managers = new TrustManager[] { new DefaultTrustManager() };
			ssl_ctx.init(new KeyManager[0], managers, new SecureRandom());
			SSLSocketFactory sslSf = new SSLSocketFactory(ssl_ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			schemeRegistry.register(new Scheme("https", HTTPS_PORT, sslSf));
			cm = new ThreadSafeClientConnManager(schemeRegistry);
			// Increase max total connection to 200
			cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
			// Increase default max connection per route to 10
			cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
			// Increase max connections for shomop to 30
			cm.setMaxForRoute(new HttpRoute(new HttpHost(DWZ_URL)),DWZ_URL_MAX_CONNECTIONS);
			// daemon thread clean idle connections
//			scheduler = Executors.newScheduledThreadPool(1, new DaemonThreadFactory("httpClient-con-monitor"));
//			scheduler.scheduleWithFixedDelay(new IdleConnectionMonitor(cm),INITIAL_DELAY, DELAY, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(">>> http connection pool initialize exception.",e);
		}
	}
	
	/**
	 * 设置某个路由的最大连接数
	 * @param host
	 * @param maxSize
	 */
	public static void setMaxForRoute(HttpHost host,int maxSize){
		maxSize = Math.max(MAX_ROUTE_CONNECTIONS,maxSize);
		cm.setMaxForRoute(new HttpRoute(host), maxSize);
	}
	
	
	/**
	 * 获取一个连接有指定超时时间限制的连接
	 * @param conTimeout
	 * @param socketTimeout
	 * @return
	 */
	public static HttpClient getHttpClient(int conTimeout,int socketTimeout){
		HttpClient httpClient = new DefaultHttpClient(cm);
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,conTimeout);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
		return httpClient;
	}
	
	/**
	 * 获取一个client
	 * @return
	 */
	public static HttpClient getHttpClient() {
		HttpClient httpClient = new DefaultHttpClient(cm);
        return httpClient;
    } 
	
	/**
	 * 获取特定的httpClient
	 * @param clazz
	 * @return
	 */
	public static <T extends SimpleHttpClient> T getHttpClient(Class<T> clazz) {
		T httpClient = null;
		try {
			Constructor<T> constructor = clazz
					.getConstructor(new Class[] { ClientConnectionManager.class });
			httpClient = constructor.newInstance(new Object[] { cm });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpClient;
    } 
	
	/**
	 * shutdown pool
	 * This includes closing all connections, whether they are currently used or not.
	 */
	public static synchronized void close() {
		if (cm != null) {
			cm.closeExpiredConnections();
			cm.shutdown();
		}
	}

	public static ThreadSafeClientConnManager getConnectionManager() {
		return cm;
	}
	
	/**
	 * 释放空闲连接线程
	 * @author spencer.xue
	 * @date 2014-5-5
	 */
    private static class IdleConnectionMonitor implements Runnable {
    	
    	final ThreadSafeClientConnManager cm;
    	
        public IdleConnectionMonitor(ThreadSafeClientConnManager cm) {
            this.cm = cm;
        }
        
        @Override
        public void run() {
        	if (log.isInfoEnabled()) {
				log.info("release start connect count : "+ cm.getConnectionsInPool());
			}
			/**
			 * 连接池连接数超过最大连接数1/2开始检查
			 */
//			if (cm.getConnectionsInPool() < MAX_ROUTE_CONNECTIONS >> 1) {
//				log.info("release start connect abort.cause by enough connections in the pool");
//				return;
//			}
            // Close expired connections
            cm.closeExpiredConnections();
            // Closes idle connections in the pool
            // that have been idle longer than readTimeout 
    		cm.closeIdleConnections(IDLE_TIMEOUT,TimeUnit.MILLISECONDS);
            if (log.isInfoEnabled()) {
                log.info("release end connect count : " + cm.getConnectionsInPool());
            }
        }
    }
    
    private static class DefaultTrustManager implements X509TrustManager {
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
