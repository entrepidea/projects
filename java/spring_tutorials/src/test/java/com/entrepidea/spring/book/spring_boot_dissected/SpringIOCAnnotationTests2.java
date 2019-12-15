package com.entrepidea.spring.book.spring_boot_dissected;

import com.entrepidea.spring.core.ioc.SpringIOCXmlTests;
import com.entrepidea.spring.book.spring_boot_dissected.support.AppConfig2;
import com.entrepidea.spring.book.spring_boot_dissected.support.User2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Desc: Annotation configuration is the new norm in Spring boot.
 * @Date: 11/30/19
 * @Source: this is a set of test cases testing the how-hows from the book "深入浅出Spring Boot 2.x", chapter #3.
 * */
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringIOCAnnotationTests2 {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringIOCXmlTests.class);

    //create a test POJO
    static class User {

        public User(int id, String name){
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
    }

    //config file. The annotation "Configuration" tells Spring that this class contains info of registering beans with Spring.
    //the @Bean annotation tells Spring that  the  following POJO  is a bean to be registered  with Spring.
    //This  way  is common  used when we want to integrate a 3rd party (e.g DataSource) API into  Spring
    @Configuration
    static class AppConfig{
        @Bean(name = "user")
        public User initUser(){
            return new User(1,"John");
        }
    }

    @Test
    public void test1(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        User user = ac.getBean(User.class);
        LOGGER.info("User[id={}, name={}]", user.getId(), user.getName());

    }

    //user  Component and ComponentScan annotations
    //another POJO
    /*@Component
    public static class  User2{
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Value("2")
        private  int  id;
        @Value("Cathy")
        private  String  name;
    }*/

    //@ComponentScan(basePackageClasses = {User2.class})
    //@ComponentScan(basePackages = "com.entrepidea.spring.core.ioc")
    //public static  class AppConfig2{}

    @Test
    public void test2(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig2.class);
        User2 user = ac.getBean(User2.class);
        LOGGER.info("User[id={}, name={}]", user.getId(), user.getName());
    }


}
