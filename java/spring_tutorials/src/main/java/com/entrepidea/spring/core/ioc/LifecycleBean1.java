package com.entrepidea.spring.core.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class LifecycleBean1 implements InitializingBean, DisposableBean {

	Logger log = LoggerFactory.getLogger(LifecycleBean1.class);
	
	@Override
	public void destroy() throws Exception {
		log.debug("destroying ===> {}", this);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("initializing ===> {}", this);
	}
}
