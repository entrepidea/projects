package com.entrepidea.java.basic.tests;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OverloadingTests {

	Logger logger = LoggerFactory.getLogger(OverloadingTests.class);

	class CollectionClassfier{

		public  String classify(Set<?> s){
			return "Set";
		}
		public  String classify(List<?> l){
			return "List";
		}
		public  String classify(Collection<?> c){
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
			logger.info("{}", new CollectionClassfier().classify(c));
		}
	}

	/*
	* A morgan Stanley question:
	* foo(Number n);
	* foo(Integer i);
	*
	* are these overriding or overloading methods?
	*
	* */
	public void foo(Number n){
        logger.info("this is a number: {}", n);
	}

    public void foo(Integer i){
        logger.info("this is a integer: {}", i);
    }

	@Test
    public void testOverloading(){
        foo(3.14);
        foo(9);
    }

    /**
	 * TODO static binding v.s dynamic binding, give examples. (10/15/14, Markit on site)
	 * */
}
