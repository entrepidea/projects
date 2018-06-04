package com.entrepidea.spring.core.ioc.supports;

import org.springframework.beans.factory.annotation.Autowired;

public  class Student {

    @Autowired
    private String name;

    public String getName() {
        return name;
    }
}