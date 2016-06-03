package com.entrepidea.java.misc;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class Stack<E> {

	private int size = 0;
	private static final int DEFAULT_BUFFER_SIZE= 20;
	private E[] buffer = (E[])new Object[DEFAULT_BUFFER_SIZE];
	Stack(){		
	}
	
	public void push(E element){
		ensureCapacity();
		buffer[size++] = element;
	}
	
	public E pop() throws Exception {
		if(size==0)
			throw new Exception();
		E ret = buffer[--size];
		buffer[size] = null;
		return ret;
	}
	
	private void ensureCapacity(){
		if(buffer.length==size)
			buffer = Arrays.copyOf(buffer, 2*size+1);		
	}
	
	public boolean isEmpty(){
		return (size==0);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack<String> s  = new Stack<String>();
		for(String arg :args)
			s.push(arg);
		
		try{
			while(!s.isEmpty()){
				String ele = s.pop();
				System.out.println(ele);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
