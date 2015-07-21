package factoryBean;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Cache;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class MainTest {
	public static void main(String[] args) {
		Resource res = new ClassPathResource("bean.xml");
		BeanFactory factory = new XmlBeanFactory(res);
		// 取到CacheManager类的实例
		CacheManager cacheManager = (CacheManager) factory
				.getBean("cacheManager");
		// 取到Cache类的实例
		Cache levelOneCache = cacheManager.getCache("levelOneCache");
	}
}