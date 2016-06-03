package com.entrepidea.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task1 implements Runnable{
	public void run(){
		try{
			synchronized(this){
				wait();
			}
		}
		catch(InterruptedException e){}
		System.out.println("The long wait ends. ");
	}
}

class Task2 implements Runnable{
	private Runnable theOther;
	public Task2(Runnable r){
		theOther = r;
	}
	public void run(){
		try{
			TimeUnit.SECONDS.sleep(3);
			synchronized(theOther){
				theOther.notify();
			}
		}
		catch(InterruptedException e){}
	}
}
public class WaitNotifyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		Runnable r;
		exec.execute(r = new Task1());
		exec.execute(new Task2(r));
		exec.shutdown();
	}
}

