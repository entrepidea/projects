package com.entrepidea.java.new_features.tests.v8;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/*
 * show how the stream is used
 * 
 * http://zeroturnaround.com/rebellabs/java-8-explained-applying-lambdas-to-java-collections/
 * 
 * */
public class StreamTest {

	static class Student{
		private Person person;
		public Student(Person p){
			person = p;
		}
		@Override
		public String toString(){
			return person.getGivenName()+","+person.getSurName()+", age: "+person.getAge();
		}
		
	}

	@Test
	public void test() {
		List<Person> persons = Person.createShortList();		

		List<Student> students = persons.stream().map(Student::new).collect(Collectors.toList());
		students.forEach(System.out::println);

	}

}
