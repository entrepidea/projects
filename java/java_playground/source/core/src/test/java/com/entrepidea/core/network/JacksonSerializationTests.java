package com.entrepidea.core.network;

import com.entrepidea.core.network.supports.Person;
import com.entrepidea.core.network.supports.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

/*
* TODO
*  Efficient JSON serialization with Jackson and Java
*  https://blogs.oracle.com/javamagazine/post/java-json-serialization-jackson
* */
public class JacksonSerializationTests {


    @Test
    public void test1(){
        var grant = new Person("Grant", "Hughes", 19);

        var mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
        try {
            var json = mapper.writeValueAsString(grant);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        var dob = LocalDate.of(2002, Month.MARCH, 17);
        var graduation = LocalDate.of(2023, Month.JUNE, 5);
        var grant = Student.of("Grant", "Hughes", dob, graduation);

        var mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule());

        try {
            var json = mapper.writeValueAsString(grant);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
