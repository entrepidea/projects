package com.entrepidea.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entrepidea.spring.core.ioc.DummyService;
import com.entrepidea.spring.core.ioc.DummyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpringIOCAnnotationTestCases {

	
	/** when using annotation, this SpringConfig class acts like the spring xml file.
	 *  You can define bean classes inside, like what we did to DummyService;
	 *  or when @ComponentScan is added, all class in its scope "com.entrepidea.spring.ioc" and which are 
	 *  annotated with @component, @resource, @service would be automatically injected, like DummyBean 
	 *  into Spring container.  
	 */
	@Configuration
	@ComponentScan("com.entrepidea.spring.core.ioc")
	@PropertySource("classpath:/config/application.properties")
	public static class SpringConfig {
		@Bean
		//put bean definition here
		DummyService createDummyService(){
			return new DummyServiceImpl();
		}
	}

	
	@Test
	public void testAnnotationConfiguration(){
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
		DummyService dummy = ac.getBean(DummyService.class);
		dummy.doStuff();
		ac.refresh();
		ac.registerShutdownHook();
		ac.close();
	}
	
	/**
	 *  class AnnotatedBean is annotated with @component
	 * */
	/*@Autowired
	AnnotatedBean bean;
	@Test
	public void testAnnotatedBean(){
		Assert.assertEquals("I am an annotated bean.",bean.getMessage());
	}*/
	

}
