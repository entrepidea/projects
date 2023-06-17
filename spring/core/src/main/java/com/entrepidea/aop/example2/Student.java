package com.entrepidea.aop.example2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Student {

    private static final Logger LOGGER = LoggerFactory.getLogger(Student.class);

    private Integer age;
    private String name;

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        LOGGER.info("Age : " + age );
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        LOGGER.info("Name : " + name );
        return name;
    }

    public void printThrowException(){
        LOGGER.info("Exception raised");
        throw new IllegalArgumentException();
    }
}
