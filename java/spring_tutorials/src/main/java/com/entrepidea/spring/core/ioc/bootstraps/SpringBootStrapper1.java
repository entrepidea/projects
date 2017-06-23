package com.entrepidea.spring.core.ioc.bootstraps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * http://entrepidea.com/blogs/tech/index.php/2016/09/30/various-ways-to-bootstrap-a-spring-container/
 * */
public class SpringBootStrapper1 {
    private static final Logger log = LoggerFactory.getLogger(SpringBootStrapper1.class);
    public static void main(String[] args){
        //pay attention to see how directory is located in relation to the project.
        //use prefix "file:" if you want to use absolution path to the config xml file.
        //multiple config files can be loaded using wildcard * too.
        FileSystemXmlApplicationContext ac = new FileSystemXmlApplicationContext
        		("/src/main/resources/config/META-INF/spring/spring-bootstrap.xml");
        ac.registerShutdownHook();
        ac.refresh();
        SimpleDriverDataSource ds = (SimpleDriverDataSource)ac.getBean("dataSource");
        log.info("user name:={}",ds.getUsername());
        ac.close();
    }
}