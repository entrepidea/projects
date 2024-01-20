package com.entrepidea.spring.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public  class Student {

    private String name;

    public Student(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}