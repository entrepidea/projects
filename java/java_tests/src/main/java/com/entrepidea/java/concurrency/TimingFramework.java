package com.entrepidea.java.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimingFramework {

	static class IntenseCalc implements Runnable {
		private double d = 0;
		public void run(){
			for(int i=0;i<25000000;i++)
				d = d+(Math.PI+Math.E);
		}
	}
	
	public static long time(Executor executor, int concurrency, final Runnable action)
	throws InterruptedException {
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch ready = new CountDownLatch(concurrency);
		final CountDownLatch done = new CountDownLatch(concurrency);
		
		for(int i=0;i<concurrency;i++){
			executor.execute(new Runnable(){
				public void run(){
					ready.countDown();//tell timer we are ready
					try{
						start.await();//wait until peers are ready
						action.run();
					}
					catch(InterruptedException ie){
						Thread.currentThread().interrupt();
					}
					finally{
						done.countDown();
					}
				}
			});
		}
		ready.await();//wait for all workers to be ready
		long startNanos = System.nanoTime();
		start.countDown(); //and they're off!
		done.await();//wait for all workers to finish
		return System.nanoTime() - startNanos;		
	}
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		int concurrencies = 100;
		IntenseCalc task = new IntenseCalc();
		ExecutorService exec = Executors.newFixedThreadPool(concurrencies);
		long inter = time(exec,concurrencies,task);
		System.out.println("interval: "+inter);
		exec.shutdown();
		
	}

}
