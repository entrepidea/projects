package com.entrepidea.java.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import junit.framework.TestCase;

public class ReentrantLockTest extends TestCase {
	
	private ReentrantLockTest test;
	
	private final ReentrantLock lock = new ReentrantLock();
	private final Object oldLock = new Object();
	
	private int count=0;
	
	public void oldM(){
		//synchronized(oldLock){
			count++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
	}
	
	public void m() {
		
		try{
			lock.tryLock(0, TimeUnit.MILLISECONDS);
			System.out.println(lock.getHoldCount());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	
	@Override
	public void setUp(){
		test = new ReentrantLockTest();
		
	}

	class Task implements Runnable{
		@Override
		public void run() {
			m();
		}
	}
	
	
	@org.junit.Test
	public void testM2(){
		
		Thread t = new Thread(new Runnable(){
			public void run(){
				lock.lock();
				System.out.println(lock.getHoldCount());
			}
		});
		t.start();
		System.out.println(t.getName()+"	started!");

		t = new Thread(new Runnable(){
			public void run(){
				if(lock.isLocked()){
					System.out.println("the lock is locked!");
				}
				else{
					try{
						lock.tryLock();
						System.out.println("lock count: "+lock.getHoldCount());
					}
					finally{
						lock.unlock();
					}
				}
			}
		});
		
		t.start();
		System.out.println(t.getName()+"	started!");

	}
	
	@org.junit.Test
	public void testOldM(){
		for(int i=0;i<60;i++){
			Thread t = new Thread(new Runnable(){
				public void run(){
					oldM();
				}
			});
			t.start();
		}
		System.out.println("count is: "+count);
	}	
}
