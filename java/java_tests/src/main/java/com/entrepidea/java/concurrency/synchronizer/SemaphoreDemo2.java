package com.entrepidea.java.concurrency.synchronizer;

/**
 * @author Hai yi
 * @description 20 clients completing for a thread pool of 5. Implemented using semaphore.
 * @reference http://www.360doc.com/content/08/0113/11/15643_969120.shtml
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo2 {
	
	volatile static int count = 0;
	static int clientID=0;
	public static void main(String[] args) {
		
		final Semaphore sem = new Semaphore(5);
		
		ExecutorService exe = Executors.newCachedThreadPool();
		//simulate 20 clients to share a thread pool of 5
		for(int i=0;i<20;i++){
			Runnable run = new Runnable(){
				public void run(){
					try{
						sem.acquire();/*acquire a thread*/
						System.out.println("client "+(clientID++)+": acquire thread: "+count++);
						Thread.sleep((long)Math.random()*10000);
						sem.release();
						count--;
					}
					catch(InterruptedException ie){
						ie.printStackTrace();
					}					
				}
			};
			exe.execute(run);
		}		
	}
}
