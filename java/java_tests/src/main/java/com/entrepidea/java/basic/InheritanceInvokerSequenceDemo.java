package com.entrepidea.java.basic;
/**
 * @author Hai Yi
 * @description: this is to demo the invoking sequence of a derive class: static block, base, and derive class.
 */
class Base {
	public static int instance1;
	public static String instance2;
	static{
		instance1 = 10;
		instance2 = new String("AABCC");
		
		System.out.println("base class in static block: instance1 = "+instance1+" instance2 = "+instance2);
	}
	public Base(){
		System.out.println("Base::Base()");
	}
}
public class InheritanceInvokerSequenceDemo extends Base {

	static{
		System.out.println("derived class in static block...");
		
	}
	public InheritanceInvokerSequenceDemo(){
		System.out.println("Derive::Derive()");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InheritanceInvokerSequenceDemo();
	}

}
