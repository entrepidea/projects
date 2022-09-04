package com.entrepidea.aop.example1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * This is the first sample program showing how to create an aspect (in this case: LoggingAspect) in Spring context and how it's used.
 * The code is pretty much based on this article (https://howtodoinjava.com/spring-aop/aspectj-around-annotation-example/) with
 * slight changes.
 *
 * All in all, Aspect-Oriented programming is to address crosscutting concerns by implementing actions in the vicinity of
 * join points that are predicated by respective pointcuts.
 *
 * NOTE: Spring boot has a starter that takes care of libs and accessories supporting AOP programming
 * (https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop/2.6.1)
 *
 * Date: 12/01/21
 *
 * */

@SpringBootApplication
@EnableAspectJAutoProxy
public class Main {
    //@SpringBootApplication already supports java style configuration, so that we can define beans as below.
    //For better modularization, a separate config class might be more suitable though.
    //https://www.javaguides.net/2018/09/spring-boot-java-based-configuration-example.html
    @Bean(name = "employeeManager")
    public EmployeeManager newEmployeeManager(){return new EmployeeManagerImpl();}
    @Bean
    public LoggingAspect newLoggingAspect(){return new LoggingAspect();}

    //program entry point.
    public static void main(String[] args){
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        EmployeeManager manager = (EmployeeManager)applicationContext.getBean("employeeManager");
        manager.getEmployeeById(1);
        manager.createEmployee(new EmployeeDTO());

    }
}
