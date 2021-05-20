package com.qiangdong.reader.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class SpringContextUtils {
	@Autowired
	private ApplicationContext applicationContext;

	public Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	public boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}

}