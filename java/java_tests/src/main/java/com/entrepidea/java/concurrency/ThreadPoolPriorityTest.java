package com.entrepidea.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is to test when a thread from a thread pool changes its priority, 
 * will it remain that level after returning to the pool? (Barcap)
 * The answer is YES, as proved by the following code
 * 
 * @author John
 * 
 * */
public class ThreadPoolPriorityTest {

	static class Task implements Runnable{
		@Override public void run(){
			System.out.println("[name="+Thread.currentThread().getName()
					+",state="+Thread.currentThread().getState()
					+",priority="+Thread.currentThread().getPriority()
					+"]");
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for(int i=0;i<20;i++)
			exec.submit(new Task());
		
		exec.shutdown();
	}

}
