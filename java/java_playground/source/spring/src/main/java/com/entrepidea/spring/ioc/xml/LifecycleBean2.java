package com.entrepidea.spring.ioc.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class LifecycleBean2 {

    private Logger log = LoggerFactory.getLogger(LifecycleBean2.class);

    @PostConstruct
    public void init(){
        log.debug("Post contruct ===> {}", this);
    }

    @PreDestroy
    public void destroy(){
        log.debug("Pre destroy ===> {}", this);
    }

}
