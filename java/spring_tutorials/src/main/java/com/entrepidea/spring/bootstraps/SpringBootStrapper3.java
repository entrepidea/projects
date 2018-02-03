package com.entrepidea.spring.bootstraps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.entrepidea.spring.bootstraps.supports.Client;
/**
 * http://entrepidea.com/blogs/tech/index.php/2016/09/30/various-ways-to-bootstrap-a-spring-container/
 * */
public class SpringBootStrapper3 {
	private static final Logger log = LoggerFactory.getLogger(SpringBootStrapper3.class);
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.entrepidea.spring.bootstraps.supports.SpringConfig");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(clazz);
        //or you can simple bootstrap the container like below, in this case you don't call #refresh, 
        //as it's already done implicitly 
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(clazz);

        context.registerShutdownHook();
        context.refresh();
         
        Client c = context.getBean(Client.class);
        String dsName = c.getDasourceName();
        String platform = c.getEnv().getProperty("platform");
        String beanName = c.getName();
        log.info("Bean name: {}, platform: {}, datasource name: {}",beanName, platform, dsName);
         
        context.close();
         
    }
}
