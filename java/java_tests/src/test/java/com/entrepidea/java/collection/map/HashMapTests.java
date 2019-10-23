package com.entrepidea.java.collection;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
/**
 * @Desc:
 * Map, HashMap, HashTable, SortedMap, synchronized Map, ConcurrentHashMap and other maps are associated containers that feature key & value pairs data structure.
 * Details, caveats of each and their differences are explained and demoed as in the test cases below.
 *
 * */

public class HashMapTests {



	//performance tests between synchronized map and concurrentHashMap
	//This could be done with a better test.
	//The idea is from this post: https://crunchify.com/hashmap-vs-concurrenthashmap-vs-synchronizedmap-how-a-hashmap-can-be-synchronized-in-java/
	private void perf(Map<String, Integer> map, int threadNum){

		long start = System.nanoTime();
		ExecutorService service = Executors.newFixedThreadPool(threadNum);
		for(int i=0;i<threadNum;i++) {
			service.execute(() -> {
				for(int j=0;j<5000000;j++){
					Integer value = (int)(Math.ceil(Math.random()));
					String key = String.valueOf(value);
					map.put(key,value);
				}
			});
		}
		service.shutdown();
		try {
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		long end = System.nanoTime();
		System.out.println("Total time takes: "+(end-start)/1000000L+" milliseconds for "+map.getClass());

	}
	@Test
	public void testMapPerformance(){
		final int THREAD_NUM = 5;

		Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String,Integer>());
		perf(map,THREAD_NUM);
		map = new ConcurrentHashMap<>();
		perf(map,THREAD_NUM);

	}

	/**
	*	HSBC interviews (by tan bin) 07/18/17
	*
	* 1. TODO Do you have to iterate all elements in a bucket of a hashmap to find out the dups?
		A: we stop checking more once we found the same key

	  2. TODO is it possible to insert two elements of the same keys to a hashmap
			yes, in a multi-threading env and w/o proper thread safety
	* */






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

	//TODO 1. ConcurentHashMap and synchronized hashMap, which is preferred, why? (10/15/14, Markit on site)



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

	//HSBC 12/17/13 interview (2nd round, video conf with London)
	//TODO How does the hash bucket work?

	//Citi onsite (Jersey City) 08/20/13, from 09AM to 11:30AM.
	//TODO Concurrent hashmap. What happens to two threads updating the data inside a segment of the underlying array. Thread safety was achieved through CAS

}
