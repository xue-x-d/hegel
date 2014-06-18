package com.shomop.crm;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.shomop.crm.model.User;
import com.shomop.crm.test.BaseTestCase;

/**
 * hibernate 缓存测试
 * @author spencer.xue
 * @date 2014-6-18
 */
@SuppressWarnings("unchecked")
public class UserTest extends BaseTestCase {

	/**
	 * The first-level cache is the Session cache and is a mandatory cache through which all requests must pass.
	 * 一级缓存是session级别的，强制性要求所有请求必须通过session缓存
	 * Hibernate tries to delay doing the update as long as possible to reduce the number of update SQL statements issued. 
	 * hibernate 试图延迟更新数据库，减少sql的次数或者数量
	 * 如果session关闭后，缓存就没了。此时就会再次发sql去查数据库。
	 */
	//@Test
	public void testCache1(){
	 Session session = getCurrentSession();
	 try {
		/**
         * 此时会发出一条sql，将所有全部查询出来，并放到session的一级缓存当中
         * 当再次查询用户信息时，会首先去缓存中看是否存在，如果不存在，再去数据库中查询
         * 这就是hibernate的一级缓存(session缓存) 默认提供
         */
		Query query = session.createQuery("from User");
		List<User> users = (List<User>) query.list();
        User user = (User)session.load(User.class, "00065702C8364CFBAD54A61B2AEE5CF8");
	    }catch (Exception e){
            e.printStackTrace();
        }
        finally
        {
        	session.close();
        }
        /**
         * 当session关闭以后，session的一级缓存也就没有了，这时就又会去数据库中查询
         */
        session = sessionFactory.openSession();
        User user = (User)session.load(User.class, "00065702C8364CFBAD54A61B2AEE5CF8");
        System.out.println(user.getUsername() + "-----------");
	}
	
	/**
	 * 二级缓存是sessionFactory级别的缓存
	 * 因为二级缓存是sessionFactory级别的缓存，在配置了二级缓存以后，当session关闭以后，
	 * 再去查询对象的时候，此时hibernate首先会去二级缓存中查询是否有该对象，有就不会再发sql了。
	 * 二级缓存缓存的仅仅是对象，如果查询出来的是对象的一些属性，则不会被加到缓存中去
	 * 
	 * Iterator<User> iterator = session.createQuery("from User").iterate();
	 * 通过二级缓存来解决 N+1 的问题
	 */
	//@Test
	public void testCache2() {
		Session session = sessionFactory.openSession();
		try {
			// 使用load表明数据库一定有，没有就会抛异常
			User user = (User) session.load(User.class,"00065702C8364CFBAD54A61B2AEE5CF8");
			System.out.println(user.getUsername() + "-----------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		try {
			/**
			 * 即使当session关闭以后，因为配置了二级缓存，而二级缓存是sessionFactory级别的，
			 * 所以会从缓存中取出该数据,只会发出一条sql语句
			 */
			session = sessionFactory.openSession();
			User user = (User) session.load(User.class,
					"00065702C8364CFBAD54A61B2AEE5CF8");
			System.out.println(user.getUsername() + "-----------");
			/**
			 * 因为设置了二级缓存为read-only，所以不能对其进行修改
			 */
			session.beginTransaction();
			user.setUsername("test");
			// 修改只读缓存会报错 java.lang.UnsupportedOperationException: Can't write to a readonly object
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		 /**
         * 注意：二级缓存中缓存的仅仅是对象，而下面这里只保存了姓名和性别两个字段，所以 不会被加载到二级缓存里面
         */
        List<Object[]> ls = (List<Object[]>) session
                .createQuery("select id,username from User user")
                .setFirstResult(0).setMaxResults(30).list();
	}

	
	/**
	 * 当我们如果通过 list() 去查询两次对象时，二级缓存虽然会缓存查询出来的对象，
	 * 但是我们看到发出了两条相同的查询语句，这是因为二级缓存不会缓存我们的hql查询语句，
	 * 要想解决这个问题，我们就要配置我们的查询缓存了
	 * hibernate.cache.use_query_cache <code>true</code>
	 * 
	 * 
	 * 查询缓存也是sessionFactory级别的缓存
	 * 只有当 HQL 查询语句完全相同时，连参数设置都要相同，此时查询缓存才有效
	 * 如果hql查询语句不同的话，查询缓存也没有作用
	 * 
	 * 
	 * 我们看到，当我们将二级缓存注释掉以后，在使用查询缓存时，也会出现 N+1 的问题，为什么呢？
	 * 因为查询缓存缓存的也仅仅是对象的id，所以第一条 sql 也是将对象的id都查询出来，
	 * 但是当我们后面如果要得到每个对象的信息的时候，此时又会发sql语句去查询，
	 * 所以，如果要使用查询缓存，我们一定也要开启我们的二级缓存，这样就不会出现 N+1 问题了
	 * 
	 */
	@Test
	public void testQueryCache() {
		Session session = null;
		try {
			/**
			 * 此时会发出一条sql取出所有的用户信息
			 */
			session = getCurrentSession();
			List<User> ls = session.createQuery("from User").setCacheable(true) // 开启查询缓存
					.setFirstResult(0).setMaxResults(50).list();
			Iterator<User> users = ls.iterator();
			for (; users.hasNext();) {
				User user = users.next();
				System.out.println(user.getUsername());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		try {
			/**
			 * 此时会发出一条sql取出所有的用户信息
			 */
			session = sessionFactory.openSession();
			List<User> ls = session.createQuery("from User").setCacheable(true) // 开启查询缓存
					.setFirstResult(0).setMaxResults(50).list();
			Iterator<User> users = ls.iterator();
			for (; users.hasNext();) {
				User user = users.next();
				System.out.println(user.getUsername());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
}
