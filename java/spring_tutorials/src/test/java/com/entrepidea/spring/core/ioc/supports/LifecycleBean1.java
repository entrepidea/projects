package com.entrepidea.spring.core.ioc.supports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class LifecycleBean1 implements InitializingBean, DisposableBean {

    Logger log = LoggerFactory.getLogger(com.entrepidea.spring.core.ioc.supports.LifecycleBean1.class);

    @Override
    public void destroy() throws Exception {
        log.debug("destroying ===> {}", this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("initializing ===> [Bean instance={}]", this);
    }
}
