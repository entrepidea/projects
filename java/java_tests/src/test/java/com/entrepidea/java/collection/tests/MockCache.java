package com.entrepidea.java.collection.tests;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 *
 * TODO
 *
 (BofA) white board a cache impl

 Create a high efficient cache solution for computation, it must be thread safe.

 public interface Computable <K, V> {
   public V compute(K k);
 }

 public class Cache <K,V> implements Computable<K,V>{
     //the Caculator below is a delegate to do the heavy lift, but the result can be cached so it's supposed to call only once
     private Computable<K,V> caculator = new Caculator<K,V>();
     private Map<K,V> cache = new HashMap<K,V>();

     public V compute(K k){
             if(cache.contains(k){
               return cache.get(k);
             }
             V val = caculator.compute(k);
              cache.put(k,val);
              return val; 
     };      
 }

 What's the problem with the above implementation? How to make it highly performant and thread safe?

 I wrote a good implementation with synchronization and he complimented "nearly perfect" but he continue to request to create a "lock-free" solution, I couldn't do it and he wrote one using FutureTask, I don't remember the details. And he also suggested a even better one using AKKA's actor.

 I didn't say it but IMHO the common perception that synchronization is a performance penalty is just a urban legend - with the nowaday's modern Java compiler the overhead of synchronization is just trivial.

 *
 * */











/**
 * @description: this program creates a Cache, which saves result of an expensive calculation. 
 * This is an interview question from BofA 
 * TODO how to make this cache thread-safe, some ideas are:
 * 1. Synchronized
 * 2. ThreadLocal 
 * 3. ConcurrentHashMap
 * 4. Initialize on Demand holder pattern or Double Check Lock pattern
 * 
 * */





interface Computable<K,V> {
	public V compute(K k);
}


public class MockCache<K, V> implements Computable<K,V> {

	class Caculator<K,V> implements Computable<K,V>{

		@Override
		public V compute(K k) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			V v = (V)k;
			return v;
		}
	}

	private Map<K,V> cache = new HashMap<>();
	private Caculator<K,V> calc = new Caculator<>();
	
	@Override
	public V compute(K k) {
		if(cache.containsKey(k)){
			return cache.get(k);
		}
		V val =  calc.compute(k);
		cache.put(k, val);
		return val;
	}

	@Test
	public void test(){
		MockCache<Double,Double> cache = new MockCache<>();
		System.out.println(cache.compute(100.00).doubleValue());//this returns after 5 seconds
		System.out.println(cache.compute(100.00).doubleValue());//this returns right away
	}
}
