package com.entrepidea.java.misc;

public class HashCodeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "abc";
		System.out.println(str.hashCode());
		System.out.println("abc".hashCode());
		
		assert(str.hashCode()=="abc".hashCode());
	}

}
