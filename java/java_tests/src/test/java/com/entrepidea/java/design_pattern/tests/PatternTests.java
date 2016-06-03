package com.entrepidea.java.design_pattern.tests;

import java.util.List;

import org.junit.Test;

import com.entrepidea.java.design_pattern.Person;

public class PatternTests {

	//builder pattern might be a good way to load data from datasource(db or plain file) 
	//into a collection, and then we can use lambda to effectively manipulate the collection.
	//see how Person class is created.
	@Test
	public void BuilderTest() {
		List<Person> people = Person.createShortList();
		
		people.forEach(p->System.out.println(p));
		
	}

}
