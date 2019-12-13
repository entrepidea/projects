package com.entrepidea.java.generic;

import org.junit.Test;

/**
 * @description this is to demo generic singleton factory pattern.
 * Effective Java 2nd edition, Item 27.
 */
public class GenericSingletonFactory {
	
	public interface UnaryFunction<T> {
		T apply(T arg);
	}

	private static UnaryFunction<Object> IDENTITY_DUNCTION = new UnaryFunction<Object>(){
		public Object apply(Object arg){return arg;}
	};
	//IDENTITY_FUNCTION is stateless and its type parameter is 
	//unbounded so it's safe to share one instance across all types.
	@SuppressWarnings("unchecked")
	public static<T> UnaryFunction<T> identityFunction(){
		return (UnaryFunction<T>)IDENTITY_DUNCTION;
	}

	@Test
	public void test() {
		String[] strings = {"Jute", "hemp", "nylon"};
		UnaryFunction<String> sameString = identityFunction();
		for(String s : strings){
			System.out.println(sameString.apply(s));
		}
		
		Number[] numbers = {1,2.0,3L};
		UnaryFunction<Number> sameNumber = identityFunction();
		for(Number n: numbers){
			System.out.println(sameNumber.apply(n));
		}
	}

}
