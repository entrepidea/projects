package com.entrepidea.ioc;

import com.entrepidea.ioc.supports.annotation.DummyUtilities;
import com.entrepidea.ioc.supports.annotation.SpringConfig;
import com.entrepidea.ioc.supports.annotation.SingletonBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= SpringConfig.class, loader=AnnotationConfigContextLoader.class)
public class SpringIOCAnnotationTests {

	/**
	 * Test the annotation @Qualifier.
	 * Qualifier is used to identify a single bean or filter out a subset of beans from a bunch of beans of the same class.
	 * */
	@Test
	public void testQualifier(){
		ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
		SpringConfig.StudentRecord sr = ac.getBean(SpringConfig.StudentRecord.class);
		sr.printName();
	}


	/**
	 * Test ApplicationContextAware interface, see the comment in DummyUtilities class.
	 * */
	@Autowired
    DummyUtilities dummyUtilities;
	@Test
	public void testApplicationContextAware(){
		ApplicationContext ctx = dummyUtilities.getApplicationContext();
		SpringConfig.StudentRecord sr = ctx.getBean(SpringConfig.StudentRecord.class);
		sr.printName();
	}


    /**
     * Test two of the bean lifecycle interfaces: InitializingBean and DisposableBean
     * Check the comments on InitialDisposeService for more details
     * */
	@Autowired
    private InitialDisposeService initialDisposeService;
	@Test
    public void testInitializingBeanDisposableBean(){
	    initialDisposeService.doSomething();
    }


    /**
     * Test to use Interface InitializingBean to inject a foreign bean into the owning class.
     * See the comments in InjectionService for more details.
     * */
    @Autowired
    private InjectionService injectionService;
    @Test
    public void testInjectionService(){
        injectionService.printStudentName();
    }


    @Autowired
    private SingletonBean singletonBean;
    @Test
    public void testMethodInjection(){
        singletonBean.showMe();
    }
}
