package com.entrepidea.bootstraps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
/**
 * http://entrepidea.com/blogs/tech/index.php/2016/09/30/various-ways-to-bootstrap-a-spring-container/
 * */
public class SpringBootStrapper2 {
    private static final Logger log = LoggerFactory.getLogger(SpringBootStrapper2.class);
    public static void main(String[] args){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext
        		("classpath:/config/META-INF/spring/spring-bootstrap.xml");
        ac.registerShutdownHook();
        ac.refresh();
        SimpleDriverDataSource ds = (SimpleDriverDataSource)ac.getBean("dataSource");
        log.info("user name:={}",ds.getUsername());
        ac.close();
    }
}
