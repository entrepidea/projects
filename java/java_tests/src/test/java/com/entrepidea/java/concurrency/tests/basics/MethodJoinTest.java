package com.entrepidea.java.concurrency.tests.basics;

public class MethodJoinTest {
	
	private static void test(){
		Thread t = new Thread(new Runnable(){
			
			public void run(){
				System.out.println("Thread street");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t.start();
		
		try {
			t.join();
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		
		System.out.println("end Thread street");
	}
	public static void main(String[] args){
		test();
		
		System.out.println("Main street");
		
	}
}
