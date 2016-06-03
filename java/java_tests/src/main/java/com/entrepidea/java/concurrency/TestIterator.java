package com.entrepidea.java.concurrency;

/**
 * Running the following program will throw a CME exception. This is because an iterator being activated followed
 * by adding elements to the list is consider a CME.
 * 
 * https://forums.oracle.com/forums/thread.jspa?threadID=1140657&tstart=150
 * */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIterator implements Runnable {

	private List<String> l = new ArrayList<String>();
	
	private void addList(){
		l.add("1");
		l.add("2");
		l.add("3");
	}
	
	@Override
	public void run() {
		Iterator<String> iter = l.iterator();
		addList();
		while(iter!=null && iter.hasNext()){
			System.out.println(iter.next());
			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		Thread t = new Thread(new TestIterator());
		t.start();
	}

}
