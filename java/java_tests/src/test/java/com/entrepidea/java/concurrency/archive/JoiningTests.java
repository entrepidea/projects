package com.entrepidea.java.concurrency.archive;

import org.junit.Test;

/**
 * 
 * 
 * Thread.Join() is to allow one thread (the joining thread) to take over the CPU control from another
 * one. It'll release the resource until it completed its task, or timeout (the timeout can be set for join())
 * 
 * @author Hai Yi
 * @reference: Think in Java (2006 version), p1143
 *
 */

public class JoiningTests {
	class Sleeper extends Thread{
		private long ti;
		Sleeper(String name, long ti){
			super(name);
			this.ti = ti;
			start();
		}
		public void run(){
			try{
				System.out.println("sleeper starts to sleep...");
				Thread.sleep(ti);
			}
			catch(InterruptedException e){
				System.out.println(getName()+" is interrupted! IsInterrupted: "+isInterrupted());
			}
		}
	}

	class Joiner extends Thread{
		private String name;
		private Thread theOther;
		Joiner(String name, Thread theOther){
			this.name = name;
			this.theOther = theOther;
			start();
		}
		public void run(){
			try{
				long start = System.currentTimeMillis();
				//subjective is theOther, verb is 'join' - the other joins to intercept the normal
				//cause of action of THIS thread.
				//It's opposite to the 'yield' which is a threading method of courtesy
				theOther.join();
				long end = System.currentTimeMillis();
				System.out.println("the sleeper is awaken after "+(end-start)+" millseconds.");
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	@Test
	public  void test() {
		Sleeper s = new Sleeper("sleeper",1000);
		new Joiner("joiner", s);
		//s.interrupt();
	}

}
