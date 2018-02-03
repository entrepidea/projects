package com.entrepidea.spring.core.ioc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import com.entrepidea.spring.core.ioc.supports.CommandManager;
import com.entrepidea.spring.core.ioc.supports.Foo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entrepidea.spring.core.ioc.supports.ClientService;
import com.entrepidea.spring.core.ioc.supports.ComplexObject;


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


	//Spring's environment abstraction covers system.getproperty and system.getenv
	@Test
	public void testPropertySource(){
		System.setProperty("foo", "val");
		ApplicationContext ctx = new GenericApplicationContext();
		Environment env = ctx.getEnvironment();
		Assert.assertTrue(env.containsProperty("foo"));
	}


	//<!-- this is supposed to be used for a MessageResource test -->
	//Spring ApplicationContext's additional functionalities. MessageSource for I18N
	//failed to work on 05/24/15 - fix it!
	@Test
	@Ignore
	public void testMessageSource(){
		MessageSource msgSrc = new ClassPathXmlApplicationContext("/META-INF/spring/messageresource.xml");
		String msg = msgSrc.getMessage("message", null, "Default", null);
		LOGGER.info("message is {}",msg);
		
		Assert.assertEquals("Alligators rock!", msg);
	}	
	
	@Autowired
	private DataSource dataSource;
	@Test
	public void testH2InMemDataBase() throws SQLException, InterruptedException{
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("Insert into TEST(FIELD1,FIELD2,FIELD3) values(?,?,?)");
		ps.setString(1, "test1");
		ps.setString(2, "test2");
		ps.setString(3, "test3");
		ps.addBatch();		
		
		ps.setString(1, "test11");
		ps.setString(2, "test22");
		ps.setString(3, "test33");
		ps.addBatch();
		
		ps.setString(1, "test111");
		ps.setString(2, "test222");
		ps.setString(3, "test3333");
		ps.addBatch();
		
		int[] batchSize = ps.executeBatch();
		conn.commit();
		System.out.println(batchSize);
		

		Thread.sleep(6000*10);
		
		
	}
}
