package com.entrepidea.java.concurrency.basics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import sun.security.x509.SerialNumber;

/**
 * 
 * @author Hai Yi
 * @description this is to demo ThreadLocal. ThreadLocal enables each thread to own a local copy of some variables, thereby prevent the variables from being 
 * tempted by other threads, which is a normal mistake in the multithreading environment if synchronization is not enforced.
 * 
 * Synchronization is heavy, and extreme care is called for. In many cases, ThreadLocal provides a simple exit.
 * 
 * @reference: http://www.builder.com.cn/2007/0529/404695.shtml
 *
 */


public class ThreadLocalDemo{
	static class ThreadId {
	    // Atomic integer containing the next thread ID to be assigned
	    private static final AtomicInteger nextId = new AtomicInteger(0);

	    // Thread local variable containing each thread's ID
	    private static final ThreadLocal<Integer> threadId =
	        new ThreadLocal<Integer>() {
	            @Override protected Integer initialValue() {
	                return nextId.getAndIncrement();
	        }
	    };

	    // Returns the current thread's unique ID, assigning it if necessary
	    public static int get() {
	        return threadId.get();
	    }
	}
	static class Task implements Runnable {
		@Override public void run(){
			System.out.println(Thread.currentThread().getName()+" - sn: "+ThreadId.get());
		}
	}

	public static void main(String args[]){
		ExecutorService exec = Executors.newFixedThreadPool(100);
		for(int i=0;i<100;i++){
			exec.submit(new Task());
		}
		exec.shutdown();
	}
	
}
