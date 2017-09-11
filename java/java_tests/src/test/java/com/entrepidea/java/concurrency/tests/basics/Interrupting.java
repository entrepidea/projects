package com.entrepidea.java.concurrency.tests.basics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class SleepBlocked implements Runnable {
	public void run(){
		try{
			TimeUnit.SECONDS.sleep(100);
		}
		catch(InterruptedException ignore){
			System.out.println("InterruptedException. ");
		}
		System.out.println("Exiting SleepBlock run(). ");
	}
}

public class Interrupting {

	/**
	 * @param args
	 */
	private static ExecutorService exec = Executors.newCachedThreadPool();
	static void test(Runnable r) throws InterruptedException {
		Future<?> f = exec.submit(r);TimeUnit.SECONDS.sleep(100);
		System.out.println("Interrupt sent to "+r.getClass().getName());
		f.cancel(true);//interrupt if running
	}
	public static void main(String[] args) throws Exception {
		test(new SleepBlocked());
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Aborting with System.exit(0)");
		System.exit(0);		
	}
}
