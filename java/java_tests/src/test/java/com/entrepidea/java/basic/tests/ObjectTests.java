package com.entrepidea.java.basic.tests;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * These tests focus on the Object class, the mother of all objects in Java.
 * We try to explore all the methods in this class and other relevant knowledges
 * */
public class ObjectTests {

	Logger log = LoggerFactory.getLogger(ObjectTests.class);

	//if two objects are equal according to their equals method, they must return the same hashcode!
	@Test
	public void testHashcode() {
		String str = "abc";
		log.debug("{}",str.hashCode());
		log.debug("{}","abc".hashCode());
		Assert.assertTrue(str.hashCode()=="abc".hashCode());
	}

	 // This is to identify the default hashcode even if we override the hashCode method.
	 // You can see from the below snippet that the default hashcode of an object is its memory address.
	@Test
	public void testIdentifyHashcode() {

		Object obj = new Object();
		int hashCode = System.identityHashCode(obj);
		log.debug("{}",Integer.toHexString(hashCode));
		log.debug("{}", obj);
	}

	//Hashcode for an Integer object is the value that the object represents.
	@Test
	public void testIntegerHashCode(){
		Assert.assertEquals(4, new Integer(4).hashCode());
	}


	 //getClass() an instance method that returns a runtime class type that the reference points to.
	 //TODO: check out more basing on this link: https://stackoverflow.com/questions/13367173/getclass-vs-class
	@Test
	public void testGetClass() throws ClassNotFoundException {
		Class i = new Integer(0).getClass();
		Assert.assertEquals(i,Integer.class);
	}

	//When passing a reference as an argument list, it's a copy of the value of the reference that is used inside the method
	class Stud {
		public String name;
	}
	private static void fakeSwap(Stud a, Stud b){
		Stud temp;
		temp = a;
		a = b;
		b = temp;
	}
	private static void swap(Stud a, Stud b){
		String temp = a.name;
		a.name = b.name;
		b.name = temp;
	}

	@Test
	public void testReferenceCopy() {
		Stud a = new Stud();
		Stud b = new Stud();
		a.name = "A";b.name = "B";
		fakeSwap(a,b);

		assertEquals(a.name, "A");
		assertEquals(b.name, "B");

		swap(a,b);
		assertEquals(a.name, "B");
		assertEquals(b.name, "A");
	}

	//test the Void class's usage. Void class has a member TYPE to indicate a void type.
	//See: https://stackoverflow.com/questions/2352447/what-is-the-need-of-void-class-in-java
	public void foo(){}
	@Test
	public void testVoidClass() throws NoSuchMethodException {
		Assert.assertTrue(getClass().getMethod("foo").getReturnType()== Void.TYPE);
	}
}
