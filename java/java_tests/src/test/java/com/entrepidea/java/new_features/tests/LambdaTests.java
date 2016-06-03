package com.entrepidea.java.new_features.tests;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

import com.entrepidea.java.design_pattern.Gender;
import com.entrepidea.java.design_pattern.Person;

public class LambdaTests {

	@Test
	public void PredicateTest() {
		List<Person> pl = Person.createShortList();
		RoboContactLambda robo = new RoboContactLambda();
	    
		//
	    // 	Predicate is a function interface, others include
		//	Consumer, Supplier, Function, UnaryOperator, BinaryOperator
		//	see this link:
		//	http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html
		//
	    Predicate<Person> allDrivers = p -> p.getAge() >= 16;
	    Predicate<Person> allDraftees = p -> p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
	    Predicate<Person> allPilots = p -> p.getAge() >= 23 && p.getAge() <= 65;
	    
	    System.out.println("\n==== Test 04 ====");
	    System.out.println("\n=== Calling all Drivers ===");
	    robo.phoneContacts(pl, allDrivers);
	    
	    System.out.println("\n=== Emailing all Draftees ===");
	    robo.emailContacts(pl, allDraftees);
	    
	    System.out.println("\n=== Mail all Pilots ===");
	    robo.mailContacts(pl, allPilots);
	    
	    // Mix and match becomes easy
	    System.out.println("\n=== Mail all Draftees ===");
	    robo.mailContacts(pl, allDraftees);  
	    
	    System.out.println("\n=== Call all Pilots ===");
	    robo.phoneContacts(pl, allPilots);    
	    
	}
	
	@Test
	public void StreamTest(){
		List<Person> people = Person.createShortList();
		people.stream().filter(p->p.getAge()>=45&&p.getGender()==Gender.MALE).forEach(p->System.out.println(p));
	}
	
	
	//stream is discharged after being used. To keep a mutable list, we will create a list copy
	//this is what collect is used for
	@Test
	public void CollectTest(){
		List<Person> people = Person.createShortList();
		List<Person> newGroup = people.stream().filter(p->p.getAge()<45).collect(Collectors.toList());
		newGroup.forEach(System.out::println); //method reference
	}
}
