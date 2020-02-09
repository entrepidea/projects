package com.entrepidea.core.basic;

import org.junit.Test;

/**
 * 
 *
 * @desc:
 * TODO write some words about Inner classes
 *
 * Think more: 
 * 1.when the inner class is defined outside the method but inside the parent class (like InnerClass2 below), such restriction is removed; 
 * 2. and even the inner class defined inside the method, it seems having no problem accessing the variables defined in parent class.
 * 
 */
public class InnerClassTests {

	public InnerClassTests(){}
	
	private String str1 = "str1";

	/**
	 *
	 *	This is an interview question in Barclays Capital:
	 *	if there is an inner class defined in a method, is it doable to access the variables defined in that method? (as shown in the testMethod below )
	 *	The answer should be: only when those variables are final.
	 *
	 *
	 *
	*/
	public void testMethod(){ 
		
		final String str2 = "str2";

		class InnerClass1{
			
			//only doable when str2 is final
	   		// Not necessary now: the Java compile implicitly recognize the variable str2 as a final String.
			public String test2(){
				return str2;
			}
			
			//no problem accessing the str1 in the parent class.
			public String test1(){
				return str1;
			}
		}
		
		System.out.println(new InnerClass1().test1());
		System.out.println(new InnerClass1().test2());
	}
	
	@Test
	public void test() {
	    new InnerClassTests().testMethod();
	}


	/**
	 * Phone Interview with Ted from BNP Paribas, 09/26/14
	 * What're the disadvantage of using anonymous inner class?
	 * */
	// Anonymous class is an inner class without a name, it's normally defined inside a method serving as a callback.
	// It can access the local "final" variables in the enclosing method, thus limiting its flexibility.
	// Since it doesn't have a name it can't be reused, mostly serving as a throw-away callback that brevity is essential. Anyway, when we choose to create
	// anonymous class we already knew it is not going to be reused, limiting to the closing class, didn't we?
	// If you want to reuse the class, you can think of converting it into an inner class even a full-brown class having its own file.
	// Since JDK 1.8, lambda expression is encouraged to replace anonymous class.
	// See also: https://stackoverflow.com/questions/5148208/what-are-the-advantages-of-anonymous-inner-class-over-non-anonymous-inner-class, which it says
	// "The anonymous inner class has advantage over the inner class (as in the question example code) in that it closes over the local variables of the method"
	// digest this line, pay particular attention the word "close over".





}
