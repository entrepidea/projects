package com.entrepidea.java.v8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//lambda is a function. It can be passed as data
//functional interface: one single method interface
//when implementing a functional interface, lambda is used
//JDK 8 introduce APIs that support lambda parameters such as forEAch removeIf, etc
//default methods

//source: https://www.youtube.com/watch?v=a450CqNXFgs
public class LambdaTest2 {

	static class Person{
		String name;
		public String getName(){return name;};
		public Person(String n){
			name = n;
		}
		
	}
	public static void main(String[] args) {
		List<Person> persons = Arrays.asList(new Person[]{
				new Person("Jonathan"), new Person("Aaron")
		});
		
		Collections.sort(persons, (p1,p2) -> p1.getName().compareTo(p2.getName()));
		
		persons.forEach(p -> System.out.println(p.getName()));
		
		//this line will fail due to UOE, this is a bug. UOE occurs when list 
		//was created out of asList
		//http://stackoverflow.com/questions/27587672/encountered-in-java-8-jdk-8023339-j-u-arrays-aslist-removeif-is-lazy-i
		persons.removeIf(p -> "Aaron".equals(p.getName()));
		
		persons.forEach(p -> System.out.println(p.getName()));

	}

}
