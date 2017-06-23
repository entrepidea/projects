package com.entrepidea.spring.core.support;

import org.springframework.stereotype.Component;

/**
 * Created by jonat on 4/22/2017.
 */
public  class Student {
    private String name;

    public Student(){
    }

    public Student(String name){
        this.name = name;
    }
    public void setName(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return "my name is "+name;
    }
}