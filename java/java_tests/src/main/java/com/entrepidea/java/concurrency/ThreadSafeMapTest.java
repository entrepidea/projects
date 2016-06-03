package com.entrepidea.java.concurrency;

import java.util.HashMap;
import java.util.Map;

public class ThreadSafeMapTest {

	static class TestClass{
		private int count=0;
		
		public synchronized void increment(){
			int temp = count;
			
			temp++;
			
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			count = temp;
		}
		public synchronized void decrement(){
			
			int temp = count;
			
			temp--;
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			
			count = temp;
		}
		public synchronized int getCount(){
			return count;
		}
	}
	
	private Map<String, TestClass> map = new HashMap<String,TestClass>();
	private TestClass tc = new TestClass();
	
	public synchronized void add(){
		map.put("1", tc);
	}
	
	public synchronized TestClass get(String id){
		return map.get(id);
	}
	
	static class Task1 implements Runnable{
		private ThreadSafeMapTest tst;
		Task1(ThreadSafeMapTest tst){
			this.tst = tst;
		}
		
		@Override public void run(){
			tst.add();
			TestClass ts = tst.get("1");
			for(int i=0;i<10;i++)
				ts.increment();
		}
	}
	
	static class Task2 implements Runnable{
		private ThreadSafeMapTest tst;
		Task2(ThreadSafeMapTest tst){
			this.tst = tst;
		}
		
		@Override public void run(){
			tst.add();
			TestClass ts = tst.get("1");
			for(int i=0;i<5;i++)
				ts.decrement();
		}
	}
	
	public static void main(String[] args) {
		ThreadSafeMapTest tst = new ThreadSafeMapTest();
		Thread t1 = new Thread(new Task1(tst));
		t1.start();
		Thread t2 = new Thread(new Task2(tst));
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TestClass ts = tst.get("1");
		System.out.println(ts.getCount());
	}
}
