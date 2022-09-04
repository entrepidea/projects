package com.entrepidea.ioc;

import com.entrepidea.ioc.supports.xml.HelloWorldParser;
import com.entrepidea.ioc.supports.xml.RootObjectParser;
import com.entrepidea.ioc.supports.xml.TypeConversion;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/META-INF/spring/core-config-context.xml" })
public class SpringSpELTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringSpELTests.class);
	
	@Autowired
	private ApplicationContext ac;
	private HelloWorldParser hwp;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		
	}

	@AfterClass
	public static void oneTimeTearDown() {
		// one-time cleanup code
		System.out.println("@AfterClass - oneTimeTearDown");
	}

	@Before
	public void setUp() {
		hwp = (HelloWorldParser) ac.getBean("spelParser");
	}

	@After
	public void tearDown() {
	}


	@Test
	public void testExpressionInterface() {
		String ret = hwp.parseIteral();
		Assert.assertEquals("Hello World", ret);
	}
	
	@Test
	public void testExpressionMethodInvoker(){
		String ret = hwp.parserMothedInvoker();
		Assert.assertEquals("Hello world!",ret);
	}

	@Test
	public void testConstructor(){
		String ret=hwp.parseConstructor();
		Assert.assertEquals("HELLO WORLD", ret);
	}
	

	@Test
	public void testGetStringBytes(){
		byte[] bytes =  hwp.getStringBytes();
		for (byte b : bytes){
			LOGGER.debug("{}",b);
		}
		Assert.assertArrayEquals(new byte[]{72,101,108,108,111,
				32,
				119,
				111,
				114,
				108,
				100}, bytes);
	}
	
	
	@Test
	public void testParseRootObject(){
		String name = RootObjectParser.parseInventorClass();
		Assert.assertEquals("Nikola Tesla", name);
	}
	
	@Test
	public void testTypeConversion(){
		Assert.assertTrue(!new TypeConversion().getBValue("false"));
	}
}
