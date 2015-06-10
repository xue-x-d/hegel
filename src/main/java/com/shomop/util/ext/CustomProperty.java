package com.shomop.util.ext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
/**
 * 配置文件中的信息
 * @author spencer.xue
 * @date 2014-11-24
 */
public class CustomProperty extends PropertyPlaceholderConfigurer {

	private final static Map<String, String> temp = new HashMap<String, String>();
	private final static Map<String, String> properties = Collections.unmodifiableMap(temp);

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			temp.put(keyStr, value);
		}
	}

	public static String getProperty(String key) {
		return properties.get(key);
	}
}
