package com.entrepidea.java.new_features.tests.v8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOpTests {

	//this shows the right way to convert an array to a list
	@Test
	public void testProperConvert() {
		// TODO Auto-generated method stub
		String[] weekDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednsday", "Thursday", "Friday","Satuaday"};
		
		//the following commented line would have thrown UOE because Arrays.asList returns a fix-sized list
		//see: http://stackoverflow.com/questions/27587672/encountered-in-java-8-jdk-8023339-j-u-arrays-aslist-removeif-is-lazy-i
		//List<String> listWeekDays = Arrays.asList(weekDays);
		
		List<String> listWeekDays = new ArrayList<>(Arrays.asList(weekDays));
		listWeekDays.removeIf(p -> p.equalsIgnoreCase("Monday"));
		listWeekDays.forEach(System.out::println);
	}

}
