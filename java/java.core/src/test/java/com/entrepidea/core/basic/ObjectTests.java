package com.entrepidea.core.basic;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @Desc:
 * These tests focus on the Object class, the mother of all objects in Java.
 * We try to explore all the methods in this class and other relevant knowledges
 *
 * @Date: unknown, 02/04/20,
 *
 * */
public class ObjectTests {

	Logger log = LoggerFactory.getLogger(ObjectTests.class);

	//if two objects are equal according to their equals method, they must return the same hashcode!
	//Note: this is the contract, however nothing stops you from breaking it. Breaking it has grieve consequence though. Surely the built-in classes like String honor this contract.
	@Test
	public void testHashcode() {
		String str = "abc";
		System.out.println(str.hashCode());
		System.out.println("abc".hashCode());
		Assert.assertTrue(str.equals("abc"));
		Assert.assertTrue(str.hashCode()=="abc".hashCode());
	}

	/**
	 * JPMorgan vide-conf interview 01/22/2020: What are the default implementations of hashcode and equals if neither has been overridden?
	 *
	 */
	//I believe you asked about user defined class. In that case it uses the ones inherited from Object class.
	// For hashCode, it's by default the memory address of the object. If you use System#identifyHashCode to compare with hashCode(), there will be the same.
	// Equals is simply one liner: "return this==obj;"
	static class Foo{
		int i;
		public Foo(int id){
			i = id;
		}

		public int getId(){
			return i;
		}
		public void setId(int id){
			i = id;
		}

	}
	@Test
	public void testFoo(){
		Foo foo = new Foo(1);
		System.out.println(Integer.toHexString(System.identityHashCode(foo)));
		System.out.println(Integer.toHexString(foo.hashCode()));
		Assert.assertTrue(System.identityHashCode(foo) == foo.hashCode());

	}

	//Now suppose you have a user-defined class but with hashCode/equals implementations.
	static class Foo2 extends Foo{

		public Foo2(int id) {
			super(id);
		}

		@Override
		public int hashCode(){
			return i;
		}

		@Override
		public boolean equals(Object obj){
			return i == ((Foo)obj).getId();
		}

	}
	@Test
	public void testFoo2() {

		Foo foo = new Foo2(10);
		int memAdr = System.identityHashCode(foo);
		int hc = foo.hashCode();
		System.out.println("value returned from identityHashCode on foo: "+ memAdr);
		System.out.println("value returned from foo's hashcode: "+ hc);
		Assert.assertNotEquals(memAdr,hc);
		//update internal state of foo1, and test again. I bet the results are the same
		foo.setId(20);
		System.out.println("value returned from identityHashCode on foo remains the same even after its internally updated (because the memory address hasn't been changed.: "
				+ System.identityHashCode(foo));

	}

	//built-in classes in JDK always override hashCode/equals therefore identifyHashCode() and hashCode() hardly return the same value
	@Test
	public void testBuiltInClassHashCode(){
		Integer i1 = new Integer(0);
		Assert.assertTrue(i1.hashCode()==0);
		Assert.assertNotEquals(System.identityHashCode(i1), i1.hashCode());
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

	/**
	 * BNP Paribas onsite 02/18/20
	 * equals() contract with compareTo()
	 *
	 * */
	//equals override leads to compareTo override. Not sure.

}
