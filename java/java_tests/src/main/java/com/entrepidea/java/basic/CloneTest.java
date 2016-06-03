package com.entrepidea.java.basic;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.StringTokenizer;
/**
 * @author: Hai Yi
 * @description: the clone method in Object is protected. To implement the clone of an object, a class should
 * implements Cloneable interface and make the clone method public, the method returns a type of the class itself
 * as shown below.
 * 
 * @notes: when the class's members are more than primitive ones, be careful with "deep copy", as shown below.
 * 
 *  @see also: Effective Java 2nd Edition, Item #11
 */
class Stack implements Cloneable{
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	public Stack(){
		this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
	};
	public void push(Object e){
		ensureCapacity();
		elements[size++] = e;
	}
	
	private void ensureCapacity() {
		if(elements.length==size){
			elements = Arrays.copyOf(elements, 2*size+1);
		}		
	}
	
	public Object pop() throws EmptyStackException{
		if(size==0){
			throw new EmptyStackException();
		}
		Object result = elements[--size];
		elements[size] = null;
		return result;
	}
	public boolean isEmpty(){
		return (size==0);
	}
	
	@Override public Stack clone(){
		try{
			Stack result = (Stack)super.clone();
			result.elements = elements.clone();
			return result;
		}
		catch(CloneNotSupportedException e){
			throw new AssertionError();
		}
	}
}

class EmptyStackException extends Exception{

	private static final long serialVersionUID = 1L;}


public class CloneTest {
	public static void main(String[] args) throws Exception {
		
			URL url = new URL("www.yahoo.com");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			System.out.println(conn);
		
	}
}
