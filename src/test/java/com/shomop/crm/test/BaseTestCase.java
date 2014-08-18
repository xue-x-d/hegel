package com.shomop.crm.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
/**
 * 带事务环境的测试基类
 * @author spencer.xue
 * @date 2014-6-19
 */
@ContextConfiguration(locations = "classpath:/application.xml")
@TransactionConfiguration(defaultRollback = true,transactionManager="txManager")
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	// applicationContext
	// logger
	// jdbcTemplate
	
	private static volatile boolean inited = false;

	@BeforeClass
	public static void init() {
		try {
			if (!inited) {
				System.out.println("Initializing running ...");
				
				inited = true;
			} else {
				System.out.println("inited: " + inited);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void destory() {
		System.out.println("test finished.");
	}
	
	@Autowired
	protected SessionFactory sessionFactory;
	
    /**
     * for hibernate save
     */
	protected void flushSession() {
		Session session = sessionFactory.getCurrentSession();
		if (session != null) {
			session.flush();
		}
	}
	
	/**
	 * This session is managed by spring framework.
	 */
	protected Session getCurrentSession(){
		
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * if you need more session in one method test
	 * and you must to close session which get by this method
	 */
	protected Session openSession(){
		
		return sessionFactory.openSession();
	}
	
}
