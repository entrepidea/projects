package com.entrepidea.java.concurrency.atomic;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class NumberRange{
	//INVARIANT: lower<upper
	private AtomicInteger lower = new AtomicInteger(0);
	private AtomicInteger upper = new AtomicInteger(0);
	
	public void setLower(int i){
		if(i>upper.get())
			throw new IllegalArgumentException("Can not set lower "+i+" upper");
		lower.set(i);
	}
	
	public void setUpper(int i){
		if(i<lower.get())
			throw new IllegalArgumentException("Can not set upper "+i+" lower");
		upper.set(i);		
	}
	
	public boolean isRuleHeld(){
		return lower.get()<=upper.get();
	}
}


class LowerSetter implements Runnable{
	private NumberRange nr;
	private Random rand = new Random(47);
	public LowerSetter(NumberRange nr){
		this.nr = nr;
	}
	
	public void run(){
		while(nr.isRuleHeld()){
			int i = rand.nextInt();
			//Thread.yield();
			nr.setLower(i);
		}
		System.out.println("Balance breaks! ");
	}
}

class UpperSetter implements Runnable{
	private NumberRange nr;
	private Random rand = new Random(47);
	public UpperSetter(NumberRange nr){
		this.nr = nr;
	}
	
	public void run(){
		while(nr.isRuleHeld()){
			int i = rand.nextInt();
			//Thread.yield();
			nr.setUpper(i);
		}
		System.out.println("Balance breaks! ");
	}
}

public class CollateralAtomics {
	
	
	public static void main(String[] args){
		NumberRange nr = new NumberRange();
		LowerSetter ls = new LowerSetter(nr);
		UpperSetter us = new UpperSetter(nr);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(ls);
		exec.execute(us);
		try{
			TimeUnit.SECONDS.sleep(5);
		}
		catch(InterruptedException ignoreMe){
		}
		exec.shutdownNow();
	}
}
