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

	
	//TODO 4. Can you remove an element during iteration? How do you do it properly?
}
