package com.entrepidea.spring.ioc.annotation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by jonat on 4/21/2017.
 * This class is used to test the usage of ApplicationContextAware interface.
 * ApplicationContextAware interface provides a means of retaining a reference to the application context it works in.
 * The @Component annotation guaranttees this class to be picked up by SpringConfig.
 * See unit test "testApplicationContextAware" how it's used.
 */
@Component
public class DummyUtilities implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
