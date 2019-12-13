package com.entrepidea.java.concurrency.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTests {

	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
	    List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
	    long start = System.nanoTime();
	    int runs = 500 * 1000;
	    for(int i=0;i<runs;i++){
	        final int finalI = i;
	        futures.add(executor.schedule(new Callable<Integer>() {
	            public Integer call() throws Exception {
	                    return finalI;
	                }
	            }, 0, TimeUnit.MICROSECONDS));
	    }
	    for (Future<Integer> future : futures) {
	        future.get();
	    }
	    executor.shutdown();
	    long time = System.nanoTime() - start;
	    System.out.printf("The average time per task is %4.1f us%n", time/runs/1000.0);
	}

}
