package com.entrepidea.java.basic;
/**
 * @description: This test is about Overloading. Be mindful that overloading is happening at compile time, meaning the below 
 * checking of parameters of CollectionClassfier happened when the program is compiled. In the eyes of a compiler, these 3 
 * overloadings are no different - their inputs are always Collection. This is why in runtime, only "classify(Collection<?> c)"
 * is picked up. 
 * 
 * The bottom line to use overloading is, make sure the overloadings's signature types and numbers are radically
 * different.
 * 
 * @see: EJ, item #41, "Use overloading judiciously"
 *   
 * */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;


public class CollectionClassfierTest {

	static class CollectionClassfier{
		public static String classify(Set<?> s){
			return "Set";
		}
		public static String classify(List<?> l){
			return "List";
		}
		public static String classify(Collection<?> c){
			return "Unknown collection";
		}		
	}
	
	@Test
	public void testCollectionClassfier(){
		Collection<?>[] collections = {
				new HashSet<String>(),
				new ArrayList<BigInteger>(),
				new HashMap<String, String>().values()
		};
		
		for(Collection<?> c:collections){
			System.out.println(CollectionClassfier.classify(c));
		}
	}
}
