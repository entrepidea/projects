package com.entrepidea.spring.core.ioc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import com.entrepidea.spring.core.ioc.supports.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.scripting.groovy.*;

/**
 * The tests under this package uses Spring XML style configuration
 * Be noted that this kind of configuration is no longer recommended by Spring
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/config/META-INF/spring/core-config-context.xml",
		"/config/META-INF/spring/jdbc-config-context.xml"
})
/**
 * or
 * 
 * 
@ContextConfiguration
(
  {
   "classpath:beans.xml",
   "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
   "file:src/main/webapp/WEB-INF/spring/dispatcher-data-servlet.xml",
   "file:src/main/webapp/WEB-INF/spring/dispatcher-servlet.xml"
  }
)
 * */

public class SpringIOCXmlTests {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringIOCXmlTests.class);

	//this is the easiest way to retrieve the application context.
	@Autowired
	private ApplicationContext ac;


	//Spring is capable of register a bean via its static factory method, as shown below.
	//check the core-config-context.xml to see how ClientService is defined.
	@Test
	public void testStaticMethodInstantilization(){
		ClientService clientService = ac.getBean(ClientService.class);
		LOGGER.info("Testing Spring to instantialize a bean through static factory method - {} ",clientService.getServiceType());
	}





	//test Spring's constructor DI
	@Test
	public void testFoo(){
		Foo foo = (Foo)ac.getBean("foo");
		Assert.assertEquals("i=1,str=hello world",foo.print());

	}


	//properties of two beans are merged if these two beans have a parent-child relation
	@Test
	public void testMergeCollection(){
		ComplexObject co = (ComplexObject)ac.getBean("child");
		Properties prop = co.getAdminEmails();

		Set<Entry<Object, Object>> s = prop.entrySet();

		Assert.assertTrue(s.size() == 3 );

		for(Iterator<Entry<Object, Object>> iter = s.iterator(); iter.hasNext();){
			Entry<Object, Object> ele = iter.next();
			LOGGER.debug("name={},value={}", ele.getKey(), ele.getValue());
		}
	}

	//test p-namespace. p namespace is just a shortcut for property
    @Test
    public void testP_namespace(){
	    ClientService c = (ClientService)ac.getBean("p_namespace");
	    Assert.assertEquals("support@FakeCompany.com",c.getEmail());
    }

    //test  c-namespace. c namespace is just a shortcut for constructor-arg in the xml config
    @Test
    public void testC_namespace(){
	    Foo foo = (Foo)ac.getBean("c_namespace");
	    Assert.assertEquals("BAR",foo.getBar());
        Assert.assertEquals("BAZ",foo.getBaz());
    }

    //test bean's dependent-on attribute. This attribute forces a mandatory initialization of the designated referenced beans
    @Test
    public void testDependentOnAttr(){
	    Foo foo = (Foo)ac.getBean("foo_dependents");
        Assert.assertEquals("BAR",foo.getBar());
        Assert.assertEquals("BAZ",foo.getBaz());
    }

    //Test method injection. Method injection is used in a scenario that a singleton bean having a dependency of a prototype bean.
	//The lifecycle of singleton bean and prototype bean are different. See the relevant section in the official document for details
	//also refer to this article: https://dzone.com/articles/method-injection-spring
	@Test
	public void testMethodInjection(){
		CommandManager cm = (CommandManager)ac.getBean("commandManager");
		cm.execute();
	}


	//two most common scopes attributes of a bean are singleton and prototype. singleton is the default
	//below will return true. The singleton in Spring is one per id per container, since Spring use a quasi key-value to cache singleton
	// see explanation from: https://dzone.com/articles/an-interview-question-on-spring-singletons
	@Test
	public void testBeanScope(){
		Assert.assertNotEquals(ac.getBean("clientService"), ac.getBean("clientService2"));
	}

	//Spring's environment abstraction covers system.getproperty and system.getenv
	@Test
	public void testPropertySource(){
		System.setProperty("foo", "val");
		ApplicationContext ctx = new GenericApplicationContext();
		Environment env = ctx.getEnvironment();
		Assert.assertTrue(env.containsProperty("foo"));
	}


	//Testing Spring bean's lifecycle interface: InitializingBean
	@Test
	public void testBeanLifecycle1(){
		ac.getBean("lifecycleBean1");
	}
	// test bean's lifecycle callbacks can be annotated, @PostConstructor and @PreDestroy as seen in LifecycleBean2's definition.
	@Test
	public void testBeanLifecycle2(){
		ac.getBean("lifecycleBean2");
	}

	//test autowire on property
	//when @Autowired is applied on a class's property, no <property> is required from xml configuration, thus no setter is required from the code.
	//Spring will look up the autowired property and find the proper bean that was registered with xml config (based on  its name or type) and inject it into the property.
	@Test
	public void testAutowiredOnProp(){
		Student s = (Student) ac.getBean("student");
		Assert.assertEquals("John", s.getName());
	}


	//test propertyPlaceholderConfigurer
	@Test
	public void testPropertyPlaceHolderConfigurer(){

		BasicDataSource db = (BasicDataSource) ac.getBean("basicDataSource");
		Assert.assertEquals("org.hsqldb.jdbcDriver",db.getDriverClassName());
	}

	//10/08/14 phone interview with BNP Paribas, GWT UI developer position, Jersey City
	//TODO 6. By default, Spring bean is singlton, what's the other type?
	//TODO 7. What's autowired?

	//BNP Paribas onsite, Jersey City, GWT UI programmer position, 10/14/2014
	//TODO 2. How do you create a bean for a singleton class?
	/*
	* TODO 3. What's the difference b/w the Spring bean whose scope is "singleton" and a real singleton instance in Java?
			We have:
			<Bean id="id1" class="MyClass"/>
			<Bean id="id2" class="MyClass"/>
			Code like below, what is the result?
			ApplicationContext ac = ...
			MyClass b1 = ac.getBean("id1");
			MyClass b2 = ac.getBean("id1");
			MyClass b3 = ac.getBean("id2");

			b1==b2  //return true or false?
			b1==b3 //return true or false?

			and Explain your answers.
	* */

	/*
	* TODO 4. Setter injection and contructor injection? Whats the difference?

			<Bean id="xyz" class="Xyzclass">
			<property name="..." value="..."/>
			<property name="..." value="..."/>
			<property name="..." value="..."/>
			</Bean>

			What's sequence of instantiating the Xyzclass? Will its constructor be called? How exactly does it mean by "setter injection" v.s "constructor injection"?
	* */

	/*
	* TODO 5. Will the below code throw an exception? if so, is it a compile error or a runtime exception? If not, will the spring resolve the issue?
			Spring bean service1 has dependency on service2;
			service2 has dependency on service 3;
			service 3 has dependency on service 1;
	*
	* */

	//TODO 7. Whats Spring's class to process property files? Say I have a DataSource bean in spring xml, it has properties such as url. The URL are in different property files per environment. How Spring uses properties files? Which class is to be used.

}
