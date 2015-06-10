package com.shomop.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"com/shomop/aop/introdution.xml");
		GreetingImpl greetingImpl = (GreetingImpl) context
				.getBean("greetingProxy"); // 注意：转型为目标类，而并非它的 Greeting 接口
		greetingImpl.sayHello("Jack");
		Apology apology = (Apology) greetingImpl; // 将目标类强制向上转型为 Apology
		apology.saySorry("Jack");
	}
}
