package com.entrepidea.java.collection.tests;


import java.util.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

public class HashMapTests {

	private static Logger log = LoggerFactory.getLogger(HashMapTests.class);


	/*
	*	HSBC interviews (by tan bin) 07/18/17
	*
	* 1. TODO Do you have to iterate all elements in a bucket of a hashmap to find out the dups?
		A: we stop checking more once we found the same key

	  2. TODO is it possible to insert two elements of the same keys to a hashmap
			yes, in a multi-threading env and w/o proper thread safety
	* */




	/**
	 * @description: the below test demo the WeakHashMap. WeakHashMap will be the first candidates for garbage collection. GC will
	 * clean up the keys and values in such a map when it detects a low memory,before it throws a OutOfMemory error.
	 * The below code take every 3rd elements out from the map and put them into a list, factually making them strongly reference,
	 * so they won't be garbage collected.
	 *
	 * @see: Think in Java, 4th edition, p892, "The WeakHashMap"
	 *
	 * */
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

	//can a HashMap have null key/value?
	//and this statement from ConcurrentHashMap confirms that Hashtable DOESN'T allow null keys or values, but HashMap does.
	//Like Hashtable but unlike HashMap, this class does not allow null to be used as a key or value.
	@Test
	public void testNulls(){
		Map<String, String> map = new HashMap<>();
		map.put("TEST_KEY", null); //passed, seems null value is allowed in HashMap
		map.put(null, "TEST_VAL"); //passed, see null key is allowed in HashMap, but what a null key is for?

		Hashtable<String, String> t = new Hashtable<>();
		t.put("TEST_KEY",null); //this is broken.
	}

	//TODO Explain diff of HashTable, HashMap; how to sync a Map; (Morgan Stanley Interview 05/17/17)

	//TODO 1. ConcurentHashMap and synchrnoized hashMap, which is preferred, why? (10/15/14, Markit on site)


	//TODO 17.
	//(10/15/14, Markit on site)
	//There is a class like below:
	//
	//class Person{
	//  Integer id;
	//  String name;
	//  Date dob;
	//
	//  //constructor
	//  public Person(id, name, date) ...
	//  //getter
	//}
	//
	//There is a Map<Person, String> map;
	//map.put(new Person(1,"abc",t), "value");
	//
	//How to make this class absolutely immutable so that mistake like: p.getDate().setTime(0) won't happen?


	//10/01/14, 5:30PM, BofA phone interview with Wilson
	//TODO 1. Explain how HashMap works
	//TODO 2. Compare HashTable and ConcurrentHashMap. Explain how CHM handle concurrency.

	//10/01/14, 4:30 PM, BofA phone interview
	//TODO ConcurrentHashMap: two threads read/write a segment, how is it handled?

	//Instinet corp, phone interview,
	//TODO ConcurrentHashMap, how does it achieve concurrency?
	//TODO How does HashMap differentiate a key from another? Can two keys have same hashcode?
}
