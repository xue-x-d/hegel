package com.shomop.rmi.service.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiServer {

	public static void main(String[] args) throws InterruptedException {
		new ClassPathXmlApplicationContext("rmi.xml");
		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
	}
}