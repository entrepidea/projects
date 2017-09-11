package com.entrepidea.java.concurrency.tests.basics;

public class WaitNotifyDemo {

	/**
	 * @param args
	 */
	class ThreadA extends Thread{
		public synchronized void run(){
			setName("A");
			System.out.print(Thread.currentThread().getName()+ ":) ");
			System.out.println(" yield the lock...");
			try{
				wait();
				System.out.print(Thread.currentThread().getName()+ ":) ");
				System.out.println(" regain the lock...");
			}
			catch(InterruptedException e){}
		}
	}
	
	class ThreadB extends Thread{
		Thread t = null;
		ThreadB(Thread theOther){
			t = theOther;
		}
		public void run(){
			synchronized(t){  
				setName("B");
				try{
					System.out.print(Thread.currentThread().getName()+ ":) ");
					System.out.println("I'll keep the lock for 5 sec then I will give it back to A.");
					Thread.sleep(5000);
					t.notify();
				}
				catch(InterruptedException e){}
			}
		}
	}
	
	WaitNotifyDemo(){
		ThreadA a = new ThreadA();
		a.start();
		ThreadB b = new ThreadB(a);
		b.start();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new WaitNotifyDemo();
	}

}
