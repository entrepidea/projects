package com.entrepidea.core.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

/**
 * 
 *
 * this is to create a generic (type parameterized) stack class; this class, together with its brother, Stack2, 
 * shows two ways of creating a generic class. 
 * 
 * @see: Effective Java, 2nd Edition. Item #26, and #28: "Use bounded wildcards to increase API flexibility"             
 * 
 * @note: pay attention to the wildcard type used in methods pushAll and popAll, and the PECS mnemonic.  
 * 
 */
class Stack<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	@SuppressWarnings("unchecked")
	public Stack() {
		this.elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
	};

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}

	public E pop()  {
		if (size == 0) {
			return null;
		}
		E result = elements[--size];
		elements[size] = null;
		return result;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Stack<E> clone() {
		try {
			Stack<E> result = (Stack<E>) super.clone();
			result.elements = elements.clone();
			return result;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
	
	public void pushAll(Iterable<? extends E> src){
		for(E e: src){
			push(e);
		}
	}
	
	public void popAll(Collection<? super E> dst){
		E e = null;
		while((e=pop())!=null){
			dst.add(e);
		}
	}
}

class Stack2<E> {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	public Stack2(){
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
	
	@SuppressWarnings("unchecked")
	public Object pop() {
		if(size==0){
			return null;
		}
		E result = (E)elements[--size];
		elements[size] = null;
		return result;
	}
	public boolean isEmpty(){
		return (size==0);
	}
	
	@SuppressWarnings("unchecked")
	@Override public Stack2<E> clone(){
		try{
			Stack2<E> result = (Stack2<E>)super.clone();
			result.elements = elements.clone();
			return result;
		}
		catch(CloneNotSupportedException e){
			throw new AssertionError();
		}
	}
}


public class StackTests extends TestCase{

	private Stack<Integer> s = new Stack<Integer>();
	
	@org.junit.Test
	public void testStack() {
		//fail("Not yet implemented");		
		assertTrue(s!=null);
	}

	@org.junit.Test
	public void testPush() {
		//fail("Not yet implemented");
		s.push(new Integer(10));
		assertTrue(s.isEmpty()==false);
	}

	@org.junit.Test
	public void testPop() {
		//fail("Not yet implemented");
		s.push(10);
		Integer val = s.pop();
		assertTrue(val!=null && val.intValue()==10);
		
	}

	@org.junit.Test
	public void testIsEmpty() {
		for(int i=0;i<10;i++)
			s.push(i);
		assertTrue(!s.isEmpty());
	}

	@org.junit.Test
	public void testClone() {
		for(int i=0;i<10;i++)
			s.push(i);
		Stack<Integer> s2 = s.clone();
		assertTrue(!s2.isEmpty());
		//assertTrue(s2.pop().intValue()==9);		
	}	
	
	@org.junit.Test
	public void testWildcardType(){
		Stack<Number> s = new Stack<Number>();
		Collection<Integer> c = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			c.add(i);
		}
		s.pushAll(c);
		
		assertTrue((Integer)s.pop()==9);
	}
}
