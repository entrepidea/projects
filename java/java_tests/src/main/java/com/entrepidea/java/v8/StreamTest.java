package com.entrepidea.java.v8;

import java.util.List;
import java.util.stream.Collectors;

import com.entrepidea.java.design_pattern.Person;


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
	public static void main(String[] args) {
		List<Person> persons = Person.createShortList();		
		
		//persons.stream().filter(p->p.getAge()>30&&p.getAge()<70).forEach(System.out::println);
		
		//persons.stream().map(Student::new).forEach(System.out::println);
		
		List<Student> students = persons.stream().map(Student::new).collect(Collectors.toList());
		students.forEach(System.out::println);

	}

}
