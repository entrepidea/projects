package com.entrepidea.java.concurrency.tests.basics;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Blocker{
	public synchronized void waitForCall(){
		while(!Thread.interrupted()){
			try{
				wait();
			}
			catch(InterruptedException e){}
		}
	}
	public synchronized void prod(){notify();}
	public synchronized void prodAll(){notifyAll();}
}

class TaskA implements Runnable{
	public static Blocker b = new Blocker();
	public void run(){b.waitForCall();}
}

class TaskB implements Runnable{
	public static Blocker b = new Blocker();
	public void run(){b.waitForCall();}
}

public class NotifyVSNotifyAll {

	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0;i<5;i++){
			exec.execute(new TaskA());
		}
		exec.execute(new TaskB());
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			boolean prod = true;
			public void run(){
				if(prod){
					System.out.println("notify() ");
					TaskA.b.prod();
					prod = false;
				}
				else{
					System.out.println("NotifyAll()");
					TaskA.b.prodAll();
					prod =true;
				}
			}
		},400,400);//run every .4 second
		
		TimeUnit.SECONDS.sleep(5);//let them run for a while
		timer.cancel();
		System.out.println("Timer canceled");
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("TaskB.b.prodAll()");
		TaskB.b.prodAll();
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("Shut down. ");
		exec.shutdownNow();
		
	}
}
