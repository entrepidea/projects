package com.entrepidea.spring.core.ioc.annotation;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


/**
 * @SpringBootApplication has a few annotation to run internally and we will see a bunch of exception from the console if 
 * those annotation's jars are not included in the classpath, i guess that's OK. 
 * */
@SpringBootApplication
public class Main {
	
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Main.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            logger.debug("{}",beanName);
        }
	}

}
