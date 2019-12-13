package com.entrepidea.java.basic;

import org.junit.Test;

/**
 * 
 * @author Hai Yi
 * @description this is an interview question in Barclays Capital. Question: if there is an inner class defined in a method, is it doable to access the variables
 * defined in that method? (as shown in the testMethod below ), the answer should be: only when those variables are final.
 * updated on 01/14/2018: it doesn't seem to be the right answer - removing final doesn't appear to cause any problems. Try for yourself.
 *
 * Think more: 
 * 1.when the inner class is defined outside the method but inside the parent class (like InnerClass2 below), such restriction is removed; 
 * 2. and even the inner class defined inside the method, it seems having no problem accessing the variables defined in parent class.
 * 
 */
public class InnerClassTests {

	public InnerClassTests(){}
	
	private String str1 = "str1";
	
	public void testMethod(){ 
		
		final String str2 = "str2";

		class InnerClass1{
			
			//only doable when str2 is final
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


	//TODO 5. What're the disadvantage of using anonymous inner class?
	//phone Interview with Ted from BNP Paribas, 09/26/14



}
