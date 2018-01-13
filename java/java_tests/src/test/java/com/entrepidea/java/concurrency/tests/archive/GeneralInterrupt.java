package com.entrepidea.java.concurrency.tests.archive;

import org.junit.Test;

/**
 * This is a clear demo about the {@link Thread#isInterrupted()} and {@link Thread#interrupt()} and {@link Thread#interrupted()}
 * - {@link Thread#interrupted()} is a static method, and it will clear the internal interrupt flag;
 * - {@link Thread#isInterrupted()} will NOT clear the internal interrupted flag.
 * 
 * read this post: http://forums.oracle.com/forums/thread.jspa?threadID=2202605&tstart=30
 * and this article: http://www.ibm.com/developerworks/java/library/j-jtp05236/index.html
 * 
 * */
public class GeneralInterrupt implements Runnable {
	   public void run() {
		      try {
		         System.out.println("in run() - about to work2()");
		         work2();
		         System.out.println("in run() - back from  work2()");
		      }
		      catch (InterruptedException x) {
		         System.out.println("in run() - interrupted in work2()");
		         return;
		      }
		      System.out.println("in run() -  doing stuff after nap");
		      System.out.println("in run() - leaving normally");
		   }
		   public void work2() throws InterruptedException {
		      while (true) {
		    	  if(Thread.currentThread().isInterrupted()){
		        	 System.out.println( "current thread is: "+Thread.currentThread().getName());
		            System.out.println("C isInterrupted()="
		            + Thread.currentThread().isInterrupted());//this WON'T clear the interrupted status, now is "true"
		            System.out.println("D interrupted: "+Thread.interrupted());//this WILL clear the interrupted status, now the status is "false"
		            Thread.sleep(2000);
		            System.out.println("E isInterrupted()="
		            + Thread.currentThread().isInterrupted());//status is changed from the above line, so this return false.
		    	  }
		      }
		   }

		   @Test
		   public void test() {
		      GeneralInterrupt si = new GeneralInterrupt();
		      Thread t = new Thread(si);
		      t.setName("working_thread");
		      t.start();
		      try {
		         Thread.sleep(2000);
		      }
		      catch (InterruptedException x) {
		      }
		      System.out.println("in main() - interrupting other thread");
		      t.interrupt();
		      System.out.println("in main() - leaving");
		   }
		}