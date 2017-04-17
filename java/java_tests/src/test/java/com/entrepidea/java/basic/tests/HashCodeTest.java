package com.entrepidea.java.basic.tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashCodeTest {

	Logger log = LoggerFactory.getLogger(HashCodeTest.class);

	@Test
	public void test() {
		// TODO Auto-generated method stub
		String str = "abc";
		System.out.println(str.hashCode());
		System.out.println("abc".hashCode());
		
		assert(str.hashCode()=="abc".hashCode());
	}

	/**
	 * @description this is to identify the default hashcode even if we override the hashCode method.
	 * You can see from the below snippet that the default hashcode of an object is its memory address.
	 */
	@Test
	public void testIdentifyHashcode() {

		Object obj = new Object();
		int hashCode = System.identityHashCode(obj);
		log.debug("{}",Integer.toHexString(hashCode));
		log.debug("{}", obj);
	}

}
