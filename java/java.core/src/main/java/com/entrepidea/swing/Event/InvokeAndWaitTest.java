package com.entrepidea.jersey.swing.Event;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.swing.SwingUtilities;



public class InvokeAndWaitTest {
	class SayHelloWorld implements Runnable{
		@Override
		public void run(){
			System.out.println("Hello world on "+Thread.currentThread()+" is this EDT? "+SwingUtilities.isEventDispatchThread());
			System.out.println("The time now is: "+System.nanoTime());
			try {
				Thread.sleep(2000);
				System.out.println("The time now is: "+System.nanoTime());				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@BeforeClass
	public void setUp(){}
	
	@AfterClass
	public void tearDown(){}
	
	
	@org.junit.Test
	public void testAppThread(){
		Thread t = new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					SwingUtilities.invokeAndWait(new SayHelloWorld());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("Finished on "+Thread.currentThread()+
						" is this EDT? "+SwingUtilities.isEventDispatchThread());
			}
		});
		t.start();
	}
	
	
	public static void main(String[] args){
		new InvokeAndWaitTest().testAppThread();
	}
	
}
