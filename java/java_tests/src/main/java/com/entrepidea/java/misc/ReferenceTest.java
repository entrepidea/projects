package com.entrepidea.java.misc;

class Stud {
	public String name;
}

public class ReferenceTest {

	/**
	 * @param args
	 */
	
	
	private static void swap(Stud a, Stud b){
		Stud temp;
		temp = a;
		a = b;
		b = temp;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stud a = new Stud();
		Stud b = new Stud();
		
		a.name = "A";b.name = "B";
		
		swap(a,b);
		
		System.out.println(a.name+" "+b.name);
	}

}
