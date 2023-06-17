package com.entrepidea.core.concurrency.basics;

import org.junit.Test;

/**
 * Deadlock is caused by inconsistent locking order of resources.
 * 
 * */
public class DeadLockTests {

	@Test
	public void test() {
		
		final Object resource1 = "resouce1";
		final Object resource2 = "resouce2";
		
		Thread t1 = new Thread( ()->{
			synchronized(resource1){
				System.out.println("thread1: access resource1");
				try{
					Thread.sleep(50);
				}
				catch(InterruptedException e){}
				synchronized(resource2){
					System.out.println("thread1: access resource2");
				}
			}
		});

		Thread t2 = new Thread( ()-> {
				synchronized(resource2){
					System.out.println("thread2: access resource2.");
					try{
						Thread.sleep(50);
					}
					catch(InterruptedException e){}
					synchronized(resource1){
						System.out.println("thread2: access resource1.");
					}
				}
		});

		t1.start();
		t2.start();
	}

	//TODO  How to generate a dead lock? (10/15/14, Markit on site)


	//3 rounds of Interview with Neuberger Berman
	//TODO 1. explain dead lock and how to solve and detect

	/**
	 * BNP Paribas onsite, 02/18/20
	 * How do you detect Deadlock?
	 *
	 * */
	//use jps to get the pid, and use "jstack pid" to get a thread dump, look for key word such as "BLOCKED" or "DEADLOCK" and locate the offended threads.

	/**
	 *  BNP Paribas onsite, 02/18/20
	 * Can deadlock happen to Database? If it happens, what will result?
	 *
	 * */
	//Database will pick up one transaction and kill it.
	//What'll happen from the client code perspective?
	//Client will have an exception.
}

