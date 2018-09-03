package com.entrepidea.java.collection.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTests {

	//this shows the right way to convert an array to a list
	//Since JDK 8
	@Test
	public void testProperConvert() {
		String[] weekDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednsday", "Thursday", "Friday","Satuaday"};
		//the following commented line would have thrown UOE because Arrays.asList returns a fix-sized list
		//see: http://stackoverflow.com/questions/27587672/encountered-in-java-8-jdk-8023339-j-u-arrays-aslist-removeif-is-lazy-i
		//List<String> listWeekDays = Arrays.asList(weekDays);
		
		List<String> listWeekDays = new ArrayList<>(Arrays.asList(weekDays));
		listWeekDays.removeIf(p -> p.equalsIgnoreCase("Monday"));
		listWeekDays.forEach(System.out::println);
	}

	//TODO What's the difference b/w ArrayList and LinkedList? (Blackrock, phone interview with Kenny Ma, 08/18/14)

	//10/01/14, 5:30PM, BofA phone interview with Wilson
	//TODO 4. Can you remove an element during iteration? How do you do it properly?
	//TODO 5. Can you update elements in an arrayList if its reference is defined as "final". If so, what do you do to prevent it?

	//HSBC 12/17/13 interview (2nd round, video conf with London)
	//TODO Why do you choose array over arrayList and vice versa?
	//TODO Name the methods in ArrayList and HashSet. In term of the contains() method of both data structures, which one has better performance? Why?


	//TODO
	//Morgan Stanley, onsite, 05/09/12
	/*
	* 1.Write customized list class that will work as general list but with version history info. The signatures are
			public String get(int index, int verNum)
			public int set(int index, String val) it will return new version number;
	* */
	//TODO  List<String>, break down to a batch of lists, list<list<string>>, the size of inner list is about 10.
}


