package com.entrepidea.java.basic;

import java.util.HashMap;
import java.util.Map;

class GenericClass{
	int i,j;
	String a,b;
	public GenericClass(){
		i=j=1;
		a=b="ab";
	}
}
public class MemoryLeak {
	
	private int curKey = 0;
	private Map hm = new HashMap();

	private void setPreference(GenericClass gc){
		hm.put(curKey, gc);
	}
	public void leakMemory(){
		try{
			GenericClass gc  = new GenericClass();
			setPreference(gc);
		}
		catch(Exception e){}
	}
	public static void main(String[] args) throws InterruptedException {
		MemoryLeak ml = new MemoryLeak();
		for(int i=0;i<1000;i++){
			for(int u=0;u<5000;u++){
				ml.leakMemory();
				ml.curKey++;
			}
			Thread.currentThread().sleep(2000);
		}

	}

}
