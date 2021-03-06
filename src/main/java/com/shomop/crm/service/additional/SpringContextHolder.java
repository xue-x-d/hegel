package com.shomop.crm.service.additional;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 以静态变量保存Spring ApplicationContext
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	/*@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}*/

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	/*@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		Map<String, T> m = applicationContext.getBeansOfType(clazz);
		return (T) m.values().toArray()[0];//默认同一类型的Bean只有一个
	}*/

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml等文件中定义SpringContextHolder");
		}
	}
	
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	public static String[] getAliases(String name) {
		return applicationContext.getAliases(name);
	}
	
	public static void main(String[] args) {
//		WebApplicationContext webApplicationContext = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext();
	}
	
}