package com.entrepidea.java.concurrency.tests.basics;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTests {

	/**
	 * This is to checkBalancedBinaryTree when a thread from a thread pool changes its priority,
	 * will it remain that level after returning to the pool? (Barcap)
	 * The answer is YES, as proved by the following code
	 * */
	@Test
	public void testPriority() {
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for(int i=0;i<20;i++)
			exec.submit(()->{
				System.out.println("[name="+Thread.currentThread().getName()
						+",state="+Thread.currentThread().getState()
						+",priority="+Thread.currentThread().getPriority()
						+"]");
				Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
			});
		exec.shutdown();
	}

	/**
	 * (Morgan Stanley Interview 05/17/17)
	 * TODO  How Threadpool is implemented? Can you write sth similar to ExecuteService#submit() ?
	 * */
}
