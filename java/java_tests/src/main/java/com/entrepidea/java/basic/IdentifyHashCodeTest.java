package com.entrepidea.java.basic;

public class IdentifyHashCodeTest {

	/**
	 * @description this is to identify the default hashcode even if we override the hashCode method.
	 * You can see from the below snippet that the default hashcode of an object is its memory address.
	 */
	public static void main(String[] args) {
		Object obj = new Object();
		int hashCode = System.identityHashCode(obj);
		System.out.println(Integer.toHexString(hashCode));
		System.out.println(obj);
	}
}
