package com.entrepidea.aop.example2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * This example shows the usage of @Pointcut. Pointcut is the predicate matching a join point, often used as parameter of
 * an advice (see the Aspect class Logging). Code is based on: <a href="https://www.tutorialspoint.com/springaop/springaop_around_aspect1.htm">...</a>
 *
 * NOTE: this article show more about @Pointcut: <a href="https://www.baeldung.com/spring-aop-pointcut-tutorial">...</a> (TODO: read it!)
 *
 * Date: 12/02/21
 * */
@SpringBootApplication
@EnableAspectJAutoProxy
public class Main {

    @Bean
    public Student newStudent(){return new Student();}
    @Bean
    public Logging newLoggingAspect(){return new Logging();}

    public static void main(String[] args){
        ApplicationContext context = SpringApplication.run(Main.class, args);
        Student student = context.getBean(Student.class);
        student.setName("Jonathan");
        student.setAge(47);
        student.getAge();
    }
}
