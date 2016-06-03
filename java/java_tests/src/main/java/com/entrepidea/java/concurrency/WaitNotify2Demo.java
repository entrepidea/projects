package com.entrepidea.java.concurrency;

import java.util.ArrayList;
import java.util.List;

class SharedBuffer<T> {
	private List<T> buffer = new ArrayList<T>();
	private int capacity=0;
	
	SharedBuffer(int capacity){
		this.capacity = capacity;
	}
	public synchronized void produce(T o){
		if(buffer.size()>=capacity){
			try{
				wait();
			}
			catch(InterruptedException e){}
		}
		buffer.add(o);
		notifyAll();
	}
	
	public synchronized T consume(){
		if(buffer.size()==0){
			try{
				wait();
			}
			catch(InterruptedException e){}
		}
		T ret=buffer.get(0);
		buffer.remove(0);
		return ret;
	}
}

class Beer{
	private int index;
	Beer(int index){this.index = index;}
	public void printIndex(){
		System.out.println("Beer No. "+index);
	}
}

class Producer extends Thread{
	private SharedBuffer<Beer> sharedBuffer;
	Producer(SharedBuffer sb){
		sharedBuffer = sb;
	}
	public void run(){
		int count=0;
		while(true){
			sharedBuffer.produce(new Beer(++count));
			System.out.println("producing beer: "+(++count));
		}
	}
}

class Consumer extends Thread{
	private SharedBuffer<Beer> sharedBuffer;
	Consumer(SharedBuffer sb){
		sharedBuffer = sb;
	}
	public void run(){
		while(true){
			Beer b = sharedBuffer.consume();
			b.printIndex();
		}
	}
}

public class WaitNotify2Demo {

	
	public static void main(String[] args){
		SharedBuffer sb;
		Producer p = new Producer(sb = new SharedBuffer(10));
		p.start();
		//Consumer c = new Consumer(sb);
		//c.start();
	}
}
