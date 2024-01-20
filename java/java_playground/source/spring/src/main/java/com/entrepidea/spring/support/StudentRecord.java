package com.entrepidea.spring.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StudentRecord {
    @Autowired
    @Qualifier("John")
    Student student;

    public StudentRecord(){}

    public void printName(){
        System.out.println(student);
    }
}
