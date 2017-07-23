package com.entrepidea.java.concurrency.basics;

/**
 * Deadlock is caused by inconsistent locking order of resources.
 * 
 * */
public class DeadLockDemo {

	public static void main(String[] args) {
		
		final Object resource1 = "resouce1";
		final Object resource2 = "resouce2";
		
		Thread t1 = new Thread(){
			public void run(){
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
				
			}
		};
		
		Thread t2 = new Thread(){
			public void run(){
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
			}
		};
		t1.start();
		t2.start();
		

	}
}
