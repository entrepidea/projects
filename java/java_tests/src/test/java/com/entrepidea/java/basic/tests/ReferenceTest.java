package com.entrepidea.java.basic.tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

class Stud {
	public String name;
}

public class ReferenceTest {

    /*
    * When passing a reference as an argument list, it's a copy of the value of the reference being used inside the method body
    * */
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
	public void test() {

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
}
