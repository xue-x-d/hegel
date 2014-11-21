package com.shomop.crm.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.shomop.crm.service.additional.AutowireTest;
import com.shomop.crm.service.additional.TestInjection2;
@ActiveProfiles("develop")
@ContextConfiguration(locations = {"classpath:application.xml","classpath:spring-hibernate.xml"})
public class AbstractBeanTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private TestInjection2 testInjection2;
	
	@Test
	public void testGet(){
		List<String> people = testInjection2.getPeople();
		for (String string : people) {
			System.out.println(string);
		}
		AutowireTest autowire = testInjection2.getAutowireTest();
		System.out.println(autowire.getName());
	}
}
