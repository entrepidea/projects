package com.entrepidea.spring.ioc.annotation;


import com.entrepidea.spring.InitialDisposeService;
import com.entrepidea.spring.InjectionService;
import com.entrepidea.spring.support.Student;
import com.entrepidea.spring.support.StudentRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * when using annotation, this SpringConfig class acts like the spring xml file.
 * it makes this purpose known to Spring with its annotation: @Configuration
 *
 * Spring scans for beans to instantilize in two ways:
 * 1. those annotated with @Bean in the configuration class. Suitable for class that you don't own the source code, like a JDBC connection pool class.
 * 2. if @ComponentScan is used in the configuration class(like this does), Spring scan all classes which are annotated with @Component, @Service, etc
 * under its scope (in this case, "com.entrepidea.spring.core.annotation.support")
 *
 */

@Configuration
@ComponentScan("com.entrepidea.ioc")
@PropertySource("classpath:/config/application.properties")
public class SpringConfig {

    @Bean
    StudentRecord studentRecord(){ return new StudentRecord();}


    @Bean(name="John")
    Student createJohn(){return new Student("John");}

    @Bean(name="Mary")
    Student createMary(){return new Student("Mary");}


    /**
     *
     * register InitialDisposeService bean with the Spring container
     * InitialDisposeService implements two bean lifecycle interfaces.
     * See InitialDisposeService's comment for more details.
     *
     * */
    @Bean
    InitialDisposeService createInitlialDisposeService(){return new InitialDisposeService();}

    @Bean
    InjectionService createInjectionService(){return new InjectionService();}
}

