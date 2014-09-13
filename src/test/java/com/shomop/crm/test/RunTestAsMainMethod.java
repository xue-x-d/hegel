package com.shomop.crm.test;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

/**
 * 测试
 */
public class RunTestAsMainMethod {
	
	@Test
	public void testMethod() {
		System.out.println("test success!");
	}

	public static void main(String[] args) {
		new JUnitCore().run(Request.method(RunTestAsMainMethod.class, "testMethod")); 
		new JUnitCore().run(Request.classes(RunTestAsMainMethod.class));// 整个测试类的所有方法
	}
}
