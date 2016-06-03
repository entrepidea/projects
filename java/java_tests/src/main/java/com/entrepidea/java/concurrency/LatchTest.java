package com.entrepidea.java.concurrency;

/**
 * CountDownLatch is used to block the workflow until all threads are completing their jobs.
 * */
import java.util.concurrent.CountDownLatch;

class IntenseCalc implements Runnable {
	private double d = 0;
	public void run(){
		for(int i=0;i<25000000;i++)
			d = d+(Math.PI+Math.E)/d;
	}
}

public class LatchTest {
	public static long timeTask(int nThreads, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		for(int i=0;i<nThreads;i++){
			Thread t = new Thread(){
				public void run(){
					try{
						startGate.await();
						try{
							task.run();
						}
						finally{
							System.out.println("thread is done.");
							endGate.countDown();
						}
					}
					catch(InterruptedException ignore){}
				}
			};
			t.start();
		}//~ for ~
		
		long start = System.nanoTime();
		startGate.countDown();
		System.out.println("start waiting for all threads to complete.");
		endGate.await();
		System.out.println("all mission completed.");
		long end = System.nanoTime();
		
		return end - start;
	}
	
	public static void main(String[] args){
		try{
			System.out.println("the process has taken: "+timeTask(10, new IntenseCalc())+" nano seconds. ");
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
