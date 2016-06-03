package com.entrepidea.java.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Pair{
	private int x, y;
	public Pair(int x, int y){
		this.x = x; this.y = y;
	}
	public Pair(){this(0,0);}
	public int getX(){return x;}
	public int getY(){return y;}
	public void incrementX(){x++;}
	public void incrementY(){y++;}
	public String toString(){
		return "X: "+x+", Y: "+y;
	}
	
	public class PairNotEqualException extends RuntimeException {
		public PairNotEqualException(){
			super("Pair values not equal: "+Pair.this);
		}
	}
	
	public void checkState(){
		if(x!=y){
			throw new PairNotEqualException();
		}
	}
}

//protect a pair inside a thread-safe class
abstract class PairManager{
	AtomicInteger checkCounter = new AtomicInteger(0);
	protected Pair p = new Pair();
	private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());
	public synchronized Pair getPair(){
		return new Pair(p.getX(), p.getY());
	}
	public void store(Pair p){
		storage.add(p);
		try{
			TimeUnit.MILLISECONDS.sleep(50);
		}catch(InterruptedException ignore){}
	}
	
	public abstract void increment();
}

class PairManager1 extends PairManager{
	public synchronized void increment(){
		p.incrementX();
		p.incrementY();
		store(getPair());
	}
}

class PairManager2 extends PairManager{
	public void increment(){
		Pair temp;
		synchronized(this){
			p.incrementX();
			p.incrementY();
			temp = getPair();
		}
		store(temp);
	}
}

class PairManupulator implements Runnable{
	private PairManager pm;
	PairManupulator(PairManager pm){
		this.pm = pm;
	}
	
	public void run(){
		while(true){
			pm.increment();
		}
	}
	
	public String toString(){
		return "Pair: "+pm.getPair() + "checkCount = " + pm.checkCounter.get();
	}
}


class PairChecker implements Runnable{
	private PairManager pm;
	PairChecker(PairManager pm){
		this.pm = pm;
	}
	public void run(){
		while(true){
			pm.checkCounter.incrementAndGet();
			pm.getPair().checkState();
		}
	}
}

public class CriticalSection {

	static void testApproaches(PairManager pman1, PairManager pman2){
		ExecutorService exec = Executors.newCachedThreadPool();
		PairManupulator pm1 = new PairManupulator(pman1),
		pm2 = new PairManupulator(pman2);
		
		PairChecker pcheck1 = new PairChecker(pman1),
		pcheck2 = new PairChecker(pman2);
		
		exec.execute(pm1);
		exec.execute(pm2);
		exec.execute(pcheck1);
		exec.execute(pcheck2);
		
		try{
			TimeUnit.MILLISECONDS.sleep(500);
		}
		catch(InterruptedException ignore){
			
		}
		
		System.out.println("pm1: "+ pm1 +"\npm2: "+pm2);
		System.exit(0);
	}
	
	
	/**
	 * @param args
	 */	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PairManager pman1 = new PairManager1();
		PairManager pman2 = new PairManager2();
		testApproaches(pman1, pman2);
	}	
}
