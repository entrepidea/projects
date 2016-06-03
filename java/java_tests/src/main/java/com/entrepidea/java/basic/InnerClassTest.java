package com.entrepidea.java.basic;

/**
 * 
 * @author Hai Yi
 * @description this is an interview question in Barclays Capital. Question: if there is an inner class defined in a method, is it doable to access the variables
 * defined in that method? (as shown in the testMethod below ), the answer should be: only when those variables are final.
 *
 * Think more: 
 * 1.when the inner class is defined outside the method but inside the parent class (like InnerClass2 below), such restriction is removed; 
 * 2. and even the inner class defined inside the method, it seems having no problem accessing the variables defined in parent class.
 * 
 */
public class InnerClassTest {

	public InnerClassTest(){}
	
	private String str1 = "string1";
	
	public void testMethod(){
		
		final String str2 = "Testing...testing...";
		class InnerClass1{
			
			//only doable when str2 is final
			public String test1(){
				return str2;
			}
			
			//no problem accessing the str1 in the parent class.
			public String test11(){
				return str1;
			}
		}
		
		System.out.println(new InnerClass1().test1());
		System.out.println(new InnerClass1().test11());
	}
	
	
	class InnerClass2{
		
		//no problem accessing the str1 in the parent class.
		public String test2(){
			return str1;
		}
	}
	public static void main(String[] args) {
		new InnerClassTest().testMethod();
	}

}
