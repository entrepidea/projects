package com.entrepidea.java.basic.tests;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

	//Hashcode for an Integer object is the value that the object represents. if you look into Integer's source code, it's coded as such.
	@Test
	public void testIntegerHashCode(){
		Assert.assertEquals(4, new Integer(4).hashCode());
	}


	 //getClass() an instance method that returns a runtime class type that the reference points to.
	 //TODO: check out more from this link: https://stackoverflow.com/questions/13367173/getclass-vs-class
	@Test
	public void testGetClass() throws ClassNotFoundException {
		Class i = new Integer(0).getClass();
		Assert.assertEquals(i,Integer.class);

		Number n = new Integer(1);
		Assert.assertEquals(n.getClass(), Integer.class); //Integer.class is called Class Literal Notation.
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

	//checkBalancedBinaryTree the Void class's usage. Void class has a member TYPE to indicate a void type.
	//See: https://stackoverflow.com/questions/2352447/what-is-the-need-of-void-class-in-java
	public void foo(){}
	@Test
	public void testVoidClass() throws NoSuchMethodException {
		Assert.assertTrue(getClass().getMethod("foo").getReturnType()== Void.TYPE);
	}

	/**
	 * Mogan stanley and HSBC interviews (by tan bin) 07/18/17

		1. TODO

			class Base{
				print(){
					"base"
				}
			}
			class Derive extends Base{
				print(){
					"derive"
				}
			}

			Base o = new Derive();
			o.print -> ?
	*/

	class Base{
		public String print(){
			return ("base");
		}
	}
	class Derive extends Base{
		@Override
		public String print(){
			return ("derive");
		}
	}

	@Test
	public void test(){
		Base o = new Derive();
		Assert.assertEquals(o.print(), "derive");
	}

/*
2. TODO
	class Base{
		print(){"base"}
		foo(){print}
	}

	class Derive extend Base{
		print(){"derive"}
		foo(){print}
	}

	Base o = new Derive();
	o.print -> ?
*/





	/**
	 * TODO:(10/15/14, Markit on site)
	 * There is a class like below:

	 class Person{
	 Integer id;
	 String name;
	 Date dob;

	 //constructor
	 public Person(id, name, date) ...
	 //getter
	 }

	 There is a Map<Person, String> map;
	 map.put(new Person(1,"abc",t), "value");

	 How to make this class absolutely immutable so that mistake like: p.getDate().setTime(0) won't happen?

	 * */

	//phone Interview with Ted from BNP Paribas, 09/26/14
	//TODO 2. classical features of OO, and which one of them is the most important to you?
	//TODO 6. How do you explain to a first time Java learner about hashCode/equals?

	// 10/08/14 phone interview with BNP Paribas, GWT UI developer position, Jersey City
	//TODO 2. what's interface?

	// 10/08/14 phone interview with BNP Paribas, GWT UI developer position, Jersey City
	//TODO 4. hashing in Java, explain.

	//Morgan Stanley phone interview, 05/14/18
	//TODO 2. Explain anonymous class.




	//Renaissance Technologies, 05/08/14, 3PM
	//TODO How the public constructor, equals and hashCode are implemented.



}
