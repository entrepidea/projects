package com.entrepidea.java.concurrency.tests.basics;

/**
 * This program shows how to interrupt a thread. A broader question is: how to stop a thread?
 * To stop a thread, it's a normal practice to use a volatile boolean flag as a liaison among 
 * threads; that's not enough, though. The flag should be used in conjunction with isInteruppted()
 * checking, the reason is detailed in this post:
 * http://forums.oracle.com/forums/thread.jspa?threadID=2202605&tstart=30
 * 
 * this IBM article is also worth reading:
 * http://www.ibm.com/developerworks/java/library/j-jtp05236/index.html
 * 
 * What's important is about these three Thread APIs:
 * 
 * Thread#interrupt()
 * Thread#isInterrupted()
 * Thread#interrupted()
 * 
 * The difference of the last two, isInterrupted and interrupted, is the latter resets the internal 
 * interrupted flag while the former doesn't.  
 * 
 * in the catch block, try commenting out and uncommenting out those code to see a difference.
 * 
 * */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SharedObject {
	private boolean amIWrong = false;
	public synchronized void wrong()throws InterruptedException {
		amIWrong = true;
		wait();
		}
	public synchronized boolean isWrong(){return amIWrong;}
}

class Task11 implements Runnable{
	private SharedObject res;
	public Task11(SharedObject r){
		res = r;
	}
	public void run(){
		while(!Thread.currentThread().isInterrupted()){
			try{
				TimeUnit.SECONDS.sleep(3);
				System.out.println("start to admit... ");
				res.wrong();
			}
			catch(InterruptedException e){

				System.out.println("I was shutted up by the executor. :-(");
				
				//System.out.println(Thread.currentThread().isInterrupted());
				//Thread.currentThread().interrupt();
				//System.out.println(Thread.currentThread().isInterrupted());
				
				System.out.println(Thread.interrupted());
				Thread.currentThread().interrupt();
				System.out.println(Thread.interrupted());
			}
		} //~ end of while
		System.out.println("out!");
	}
}


class Task22 implements Runnable{
	private SharedObject res;
	public Task22(SharedObject r){
		res = r;
	}
	public void run(){
		while(!res.isWrong());//busy waiting
		System.out.println("I admit that I am wrong... :-( ");
	}
}

public class StopThreadTest {

	public static void main(String[] args) throws Exception {
		SharedObject res = new SharedObject();
		ExecutorService exec = Executors.newCachedThreadPool();
		
		Task11 t11 = new Task11(res);
		Task22 t22 = new Task22(res);
		
		exec.execute(t11);
		exec.execute(t22);
		TimeUnit.SECONDS.sleep(1);
		
		exec.shutdownNow();
	}

}
