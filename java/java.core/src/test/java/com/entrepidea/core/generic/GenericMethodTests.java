package com.entrepidea.core.generic;

/**
 * @description: this is to checkBalancedBinaryTree the generic method. In the example below, the method "union" is generic method with typed parameters.
 * Note that when this method is used, no concrete type needs to be specified, as shown on line 33. The compiler is smart enough
 * to figure it out, this is called type inference. 
 * 
 * @see also: item 27 in EJ: Favor generic methods  
 * */

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

public class GenericMethodTests {

    //showcase how to write a generic method
	public static <E> Set<E> union(Set<E> s1, Set<E> s2){
		Set<E> result = new HashSet<E>(s1);
		result.addAll(s2);
		return result;
	}
	@Test
	public void testUnion(){
		Set<String> guys = new HashSet<String>(Arrays.asList("S&P","Moody", "FitchRatings"));
		Set<String> stooges = new HashSet<String>(Arrays.asList("S&P", "Dick","Mooley"));
		
		Set<String> s = union(guys,stooges);//no concrete type needs to be specified
		
		Assert.assertTrue(s.size()==5);
		
		System.out.println(s);
	}

	//all the algorithm methods in Collection are generic. Their parameters are typed parameters.
    //binarySearch on Java doc: https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#binarySearch(java.util.List,%20T)
	@Test
	public void testBinarySearch(){
		//parameter type is Integer
		List<Integer> l = new ArrayList<>();
		l.add(10);
		l.add(20);
		l.add(30);
		l.add(40);
		l.add(50);

		int index = Collections.binarySearch(l, 20);
		Assert.assertEquals(1, index);

		//Parameter type is String
		List<String> l2 = new ArrayList<>();
		l2.add("A");
		l2.add("B");
		l2.add("C");
		l2.add("C");
		l2.add("D");
		l2.add("E");

		index = Collections.binarySearch(l2,"C");
		Assert.assertEquals(2,index);
	}

	//checkBalancedBinaryTree another algorithm meothd: sort
    @Test
    public void testSort(){
        /*
        as a side note: there are multiple ways to instantialize and initialize a list from an array
        Arrays.asList
        or: Collections.addAll(l, "Geeks For Geeks", "Friends", "Dear", "Is", "Superb")
        or using Gurva: Lists.newArrayList.
        */
        List<String> l = Arrays.asList("Geeks For Geeks", "Friends", "Dear", "Is", "Superb");
        Collections.sort(l);
        Assert.assertEquals("Dear", l.get(0));
    }
}
