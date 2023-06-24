package com.entrepidea.ioc;

import com.entrepidea.ioc.support.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by jonat on 4/22/2017.
 * Interface InitializingBean can help inject a foreign bean into the owning class.
 */
public class InjectionService implements InitializingBean, ApplicationContextAware {

    private Logger LOGGER = LoggerFactory.getLogger(InjectionService.class);
    private ApplicationContext ctx;
    private Student student;
    @Override
    public void afterPropertiesSet() throws Exception {
        student = (Student)ctx.getBean("John");
        //student.setName("Peter");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }


    public void printStudentName(){
        LOGGER.info("{}",student);
    }
}
