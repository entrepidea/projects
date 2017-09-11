package com.entrepidea.java.concurrency.tests.basics;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VolatileRefTest {

	private static void delay(int d){
		try {
			Thread.sleep(d);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static class Foo{
		private int a = 1;
		private int b = 2;
		public void setA(int a) {
			this.a = a;
		}
		public int getA() {
			return a;
		}
		public void setB(int b) {
			this.b = b;
		}
		public int getB() {
			return b;
		}
	}
	
	private static Lock mutex = new ReentrantLock();
	
	static class Consumer implements Runnable{
		private Producer p;
		public Consumer(Producer p){
			this.p = p;
		}
		@Override
		public void run(){
			try{
				if(!mutex.tryLock()){;
					System.out.println("consumer fail to lock!");
				}
				System.out.println("consumer thread="+Thread.currentThread().getName()+",lock counting="+((ReentrantLock)mutex).getHoldCount());
				Foo f= p.getFoo();
				System.out.println("A="+f.getA());
				delay(2000);
				System.out.println("B="+f.getB());
			}
			finally{
				System.out.println("consumer thread="+Thread.currentThread().getName()+",is mutex locked: "+((ReentrantLock)mutex).isLocked());
				if(((ReentrantLock)mutex).isLocked()&&((ReentrantLock)mutex).getHoldCount()>0){
					mutex.unlock();
				}
			}
		}
	}
	
	static class Producer implements Runnable{
		private volatile Foo f;
		Producer(Foo f){
			this.f = f;
		}
		public Foo getFoo(){return f;}
		public void modifyFoo(){
//			Foo newf = new Foo();
//			newf.setA(100);
//			newf.setB(200);
//			f = newf;
			f.setA(100);
			f.setB(200);
		}
		
		@Override
		public void run(){
			//while(!Thread.interrupted()){
				try{
					mutex.tryLock();
					System.out.println("producer thread="+Thread.currentThread().getName()+",lock counting="+((ReentrantLock)mutex).getHoldCount());
					delay(400);
					modifyFoo();
				}
				finally{
					System.out.println("producer thread="+Thread.currentThread().getName()+",is mutex locked: "+((ReentrantLock)mutex).isLocked());
					mutex.unlock();
				}
			//}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Foo f = new Foo();
		Producer p;
		new Thread(p=new Producer(f)).start();
		new Thread(new Consumer(p)).start();
	}

}
