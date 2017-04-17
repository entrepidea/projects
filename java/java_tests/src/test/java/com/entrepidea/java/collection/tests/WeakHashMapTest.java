package com.entrepidea.java.collection.tests;

/**
 * @description: the below test demo the WeakHashMap. WeakHashMap will be the first candidates for garbage collection. GC will 
 * clean up the keys and values in such a map when it detects a low memory,before it throws a OutOfMemory error. 
 * The below code take every 3rd elements out from the map and put them into a list, factually making them strongly reference,
 * so they won't be garbage collected.
 * 
 * @see: Think in Java, 4th edition, p892, "The WeakHashMap"
 *   
 * */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

public class WeakHashMapTest extends TestCase {

	private static Logger log = LoggerFactory.getLogger(WeakHashMapTest.class);
	class Element {
		
		private String ident;
		
		public Element(String id){
			this.ident = id;
		}
		@Override
		public String toString(){
			return ident;
		}
		@Override
		public boolean equals(Object o){
			return o instanceof Element &&
			ident.equals(((Element)o).ident);
		}
		@Override
		public int hashCode(){
			return ident.hashCode();
		}
		@Override
		protected void finalize(){
			log.info("Finalizing "+getClass().getSimpleName() + " "+ident);
		}
	}
	
	class Key extends Element{

		public Key(String id) {
			super(id);
		}
	}
	
	class Value extends Element{

		public Value(String id) {
			super(id);
		}
	}
	
	@Test
	public void testCleanWeakHashMap(){
		int size = 100;
		//Key[] keys = new Key[size];
		List<Key> keys = new ArrayList<Key>();
		
		WeakHashMap<Key, Value> map = new WeakHashMap<Key,Value>();
		
		for(int i=0;i<size;i++){
			Key key = new Key(new Integer(i).toString());
			Value value = new Value(new Integer(i).toString());
			
			if(i%3==0){
				keys.add(key);
			}
			map.put(key, value);
		}
		
		Set<Key> set = map.keySet();
		Iterator<Key> iter = set.iterator();
		int count=0;
		while(iter.hasNext()){
			Key k = iter.next();
			log.info(count+ " "+k +" : "+ map.get(k));
			count++;
		}
		log.info("start gc: ");
		long then = System.nanoTime();
		System.gc();
		log.info("gc done, it takes : {} nano seconds.",(System.nanoTime()-then));
		
		log.info("suvivor numbers: {} ",keys.size());
		
		for(Key k: keys){
			log.info("suvivors: {} ", k);
		}
	}	
}
