package com.entrepidea.core.collection.map;


import java.util.*;
import org.junit.Test;

/**
 * @Desc:
 * Map, HashMap, HashTable, SortedMap, synchronized Map, ConcurrentHashMap and other maps are associated containers that feature key & value pairs data structure.
 * Details, caveats of each and their differences are explained and demoed as in the test cases below.
 *
 * A little more about HashMap. HashMap maintains a resizable array internally (known as buckets), each pigeonhole of the array is
 * a linked-list or red-black tree (since 1.8, for better performance when the list has a length greater than 8.) Which hole an entry
 * falls into dependents on its hashcode. Once the hole is located, further search is conducted using its equals() to check if the
 * entry's key is presented or not.
 *
 * */

public class HashMapTests {




	//indexFor() - this is a HashMap internal method prior to 1.8, it's inlined since 1.8.
	//See this post: https://stackoverflow.com/questions/55925837/why-indexfor-method-of-hashmap-is-removed-in-java-8
	//See hash in general: https://www.cs.cmu.edu/~adamchik/15-121/lectures/Hashing/hashing.html
	//However, indexFor() helps understand how an entry spreads to the buckets.
	//This post (https://stackoverflow.com/questions/10879302/hashmap-implementation-in-java-how-does-the-bucket-index-calculation-work)
	//also helps understand the designing idea behind, and why we choose bitwise AND over mod operation on hash.
	static final int indexFor(int h, int tabLength){
		return h & (tabLength - 1); //it's said this technique produce same results as "h%tabLength", only faster.
	}

	@Test
	public void testHash(){

		final int BUCKET_SIZE = 16;
		final int[] buckets = new int[BUCKET_SIZE];

		Integer[] keys = new Integer[100000];
		Random random = new Random();
		int low = 10;
		int high = 100000;
		for(int i=0;i<keys.length;i++){//populate the testing key array
			keys[i] =  random.nextInt(high-low) + low;
			int h = keys[i]; //for integer type, hash is the value itself.
			int index = indexFor(h,BUCKET_SIZE);
			buckets[index] += 1;
		}
		for(int i=0;i<buckets.length;i++){
			System.out.println("["+i+"]:"+buckets[i]);
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
		Set<Map.Entry<String,String>> entries = map.entrySet();

		for(Iterator<Map.Entry<String,String>> iter = entries.iterator();iter.hasNext();){
			Map.Entry<String,String> entry = iter.next();
			System.out.println("key="+entry.getKey()+", value="+entry.getValue());
		}

		Hashtable<String, String> t = new Hashtable<>();
		t.put("TEST_KEY",null); //this is broken - HashTables don't allow nullable values.
	}

	//lambda expression enhancement - sometime can avoid using trivial traditional iterating such as: Set<Map.Entry<>>/iterator...
	@Test
	public void testForEach(){
		Map<Integer,String> map = new HashMap<>();
		map.put(1,"Hello");
		map.put(2,"World");
		map.forEach((k,v) -> System.out.println(k+v));
	}

	@Test
	public void testCompute(){
		Map<Integer,String> map = new HashMap<>();
		map.put(1,"Hello");
		String msg = " world!";
		map.compute(1, (k,v)->v==null?msg:v.concat(msg)); //merge() is a better alternative here but we just demo how compute() is used.
		map.forEach((k,v)->System.out.println("key="+k+", value="+v));
	}



	/**
	* @Interview questions:
	*
	HSBC interviews (by tan bin) 07/18/17
		1. 	Do you have to iterate all elements in a bucket of a hashmap to find out the dups?
			A: we stop checking more once we found the same key. Note: the question needs more clarification

		2. is it possible to insert two elements of the same keys to a hashmap
 	 		A: yes, in a multi-threading env and w/o proper thread safety


	TODO Explain diff of HashTable, HashMap; how to sync a Map; (Morgan Stanley Interview 05/17/17)

	TODO 1. ConcurentHashMap and synchronized hashMap, which is preferred, why? (10/15/14, Markit on site)


	TODO 17.
	(10/15/14, Markit on site)
	There is a class like below:

	class Person{
	  Integer id;
	  String name;
	  Date dob;

	  //constructor
	  public Person(id, name, date) ...
	  //getter
	}

	//There is a Map<Person, String> map;
	//map.put(new Person(1,"abc",t), "value");
	//
	//How to make this class absolutely immutable so that mistake like: p.getDate().setTime(0) won't happen?


	10/01/14, 5:30PM, BofA phone interview with Wilson
	TODO 1. Explain how HashMap works
	TODO 2. Compare HashTable and ConcurrentHashMap. Explain how CHM handle concurrency.

	10/01/14, 4:30 PM, BofA phone interview
	TODO ConcurrentHashMap: two threads read/write a segment, how is it handled?

	//Instinet corp, phone interview,
	TODO ConcurrentHashMap, how does it achieve concurrency?
	TODO How does HashMap differentiate a key from another? Can two keys have same hashcode?

	//HSBC 12/17/13 interview (2nd round, video conf with London)
	TODO How does the hash bucket work?

	//Citi onsite (Jersey City) 08/20/13, from 09AM to 11:30AM.
	TODO Concurrent hashmap. What happens to two threads updating the data inside a segment of the underlying array. Thread safety was achieved through CAS

	*/

	/**
	 * BGC phone interview, 12/02/19
	 * What's LoadFactor?
	 */
	//LoadFactor is the factor used in HashMap to determine when the bucket numbers should be resized. Default value is 0.75.
	//HashMap has a default or set  bucket numbers, once too many elements are loaded in, it might be a time to resize (double)
	//the bucket number to reach  a balance. The load factor is such a factor to determine when the resize will take place.
}
