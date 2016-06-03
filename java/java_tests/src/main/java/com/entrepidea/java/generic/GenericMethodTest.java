package com.entrepidea.java.generic;

/**
 * @description: this is to test the generic method. In the example below, the method "union" is generic method with typed parameters.
 * Note that when this method is used, no concrete type needs to be specified, as shown on line 29. The compiler is smart enough
 * to figure it out, this is called type inference. 
 * 
 * @see also: item 27 in EJ: Favor generic methods  
 * */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class GenericMethodTest extends TestCase {

	public static <E> Set<E> union(Set<E> s1, Set<E> s2){
		Set<E> result = new HashSet<E>(s1);
		result.addAll(s2);
		return result;
	}
	
	@Test
	public static void testUnion(){
		Set<String> guys = new HashSet<String>(Arrays.asList("S&P","Moody", "FitchRatings"));
		Set<String> stooges = new HashSet<String>(Arrays.asList("S&P", "Dick","Mooley"));
		
		Set<String> s = union(guys,stooges);//no concrete type needs to be specified
		
		Assert.assertTrue(s.size()==5);
		
		System.out.println(s);
	}

}
