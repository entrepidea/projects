package com.entrepidea.java.basic;

/**
 * Overloading methods would have different argument list, thats how compilers tell one overloading from another.
 * However, compilers dont rely on their return type for distinction. Thats why the below two methods wouldn't compile.
 * */
import org.junit.Test;

import junit.framework.TestCase;

public class OverloadingTest extends TestCase {

	public void foo(){};
	public String foo(){return null;};
	
	@Test
	public void testOverLoading(){
		
	}
}
