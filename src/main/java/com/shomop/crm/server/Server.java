package com.shomop.crm.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shomop.crm.server.version.ClassPathXmlApplicationContextInit;
import com.shomop.crm.server.version.Config;
import com.shomop.crm.server.version.enums.VERSION;
/**
 * 目前有taobao,dd,jd
 * 启动读取环境变量VERSION
 * 如果没有默认为淘宝版本
 * @author shomop
 */
public class Server {

	private static Server instance;
	public static Config config; 
	// 加载版本信息
	// 本地测试修改配置文件，生效需要注销用户
	static {
		config = new Config(VERSION.getVersion(System.getenv("VERSION")));
		System.out.println("server initializing.... "+config.env + "," + config.version);
		instance = new Server(config);
	}
	private static ClassPathXmlApplicationContext springContext;
	private Server(Config config) {
		springContext = ClassPathXmlApplicationContextInit.getClassPathXmlApplicationContext(config);
	} 

	public static Server getInstance() {
		return instance;
	}
	
	public ClassPathXmlApplicationContext getAppContext() {
		return springContext;
	}

	public static Object findBean(String beanName) {
		return getInstance().getAppContext().getBean(beanName);
	}
	
	public static <T> T findBean(String beanName,Class<T> type) {
		return getInstance().getAppContext().getBean(beanName, type);
	}
	/**
	 * 主动监听启动
	 * 1.监听新增订单和订单状态发生变化的订单 
	 * 2.监听异步下载完成的消息,并下载内容插入数据库
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
        /*while (true) {
            Thread.sleep(100000L);
        }*/
	}
}
