package com.entrepidea.java.design_pattern;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * What's a monitor? Monitor is an threadsafe object. To make an object thread safe, we can use intrisitic  lock 
 * (which uses synchronized(monitor) acquire a monitor's lock), or we can use explicit lock (which uses a Lock
 * object (introduced since JDK 1.5)) to guarantee the monitor is not intervened by other threads when it's run
 * in one. 
 * 
 * The below is a demo how to create a monitor in a explicit lock way.
 * 
 * see this article for a complete explanation: 
 * http://www.baptiste-wicht.com/2010/09/java-concurrency-part-5-monitors-locks-and-conditions/
 * 
 *    @author John
 *    
 * */

class BoundedBuffer {
	
	private Object[] buffer;
	private Lock lock = new ReentrantLock();
	private int putPtr, getPtr, count;
	private Condition notFull, notEmpty;
	
	public BoundedBuffer(int capacity){
		buffer = new Object[capacity];
		notFull = lock.newCondition();
		notEmpty = lock.newCondition();
		putPtr = 0;
		getPtr = 0;
	}
	public void put(Object o) throws InterruptedException {
		try{
			lock.lock();
					
			while(count==buffer.length) notFull.await();
			buffer[putPtr] = o;
			if(++putPtr == buffer.length) putPtr = 0;
			count++;
			notEmpty.signal();
		}
		finally{
			lock.unlock();
		}
	}
	
	public Object get() throws InterruptedException{
		Object ret=null;
		try{
			lock.tryLock();
			while(count==0) notEmpty.await();
			ret = buffer[getPtr];
			if(++getPtr == buffer.length) getPtr=0;
			count--;
			notFull.signal();
			return ret;
		}
		finally{
			lock.unlock();
		}
	}
	
	public int getCapacity(){
		boolean locked = false;
		try{
			locked = lock.tryLock();
			return buffer.length;
		}finally{if(locked){
			lock.unlock();
			locked = false;
			}
		}
		
	}
}

class Producer implements Runnable {
	BoundedBuffer bb;

	public Producer(BoundedBuffer bb) {
		this.bb = bb;
	}

	public void run() {
		for (int i = 0; i < bb.getCapacity(); i++) {
			try {
				bb.put(new Integer(i));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("I am done producing.");
	}
}

class Consumer implements Runnable {
	BoundedBuffer bb;

	public Consumer(BoundedBuffer bb) {
		this.bb = bb;
	}

	public void run() {
		int size = bb.getCapacity();
		System.out.println("the size is: " + size);
		for (int i = 0; i < size; i++) {
			try {
				Integer in = (Integer) bb.get();
				if (in != null)
					System.out.println(in);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("I am done consuming.");
	}
}

public class BoundedBufferTests extends TestCase {

	@Test
	public void testBoundedBuffer() {
		BoundedBuffer bb = new BoundedBuffer(100);
		Thread t = new Thread(new Producer(bb));
		t.start();
		t = new Thread(new Consumer(bb));
		t.start();
	}
}