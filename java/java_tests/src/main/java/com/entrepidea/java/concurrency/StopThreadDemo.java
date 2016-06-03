package com.entrepidea.java.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Hai Yi
 * @description: Thread.stop() used to stop a running thread, but it's decrecated long time ago due to its inherent unsafty. A common way is to use a boolean 
 * variable as a flag. The Thread-to-be-stopped will poll this variable continuously, once its status is changed (by another thread), the thread will exit.
 * 
 * There are a few tricks using the flag: it needs to be synchronized for two purposes: 1. mutual exclusion; 2. inter-thread communication.
 * 
 * If the flag is not synchronized, the thread-to-be-stopped is not guaranteed to see the change of the flag. An alternative to using synchronization is to use
 * volatile modifier. volatile doesn't guarantee mutual exclusion but it guarantees that when a thread access a volatile variable, it'll see the latest copy of it.
 * In another word, it guarantees the inter-thread communication.
 * 
 *   @see Effective Java, Item# 66, Synchronize access to shared mutable data
 *
 */
public class StopThreadDemo {

	private static boolean  stopFlag = false;
	
	private static synchronized boolean requestStopFlag(){
		return stopFlag;
	}
	
	private static synchronized void stopThread(){
		stopFlag = true;
	}
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		new Thread(new Runnable(){
			public void run(){
				//poll the stop flag, once it's true, exit the loop
				int i=0;
				while(!requestStopFlag()){
					i++;
				}
				System.out.println("Stop signal received, jump out the loop and about to exit, the current i is: "+i);
			}
		}).start();
		
		TimeUnit.SECONDS.sleep(1);
		
		stopThread();
	}

}
