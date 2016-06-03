package com.entrepidea.java.collection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionBSearchTest {

	private static Logger log = LoggerFactory.getLogger(CollectionBSearchTest.class);
	
	public static void main(String[] args) {

		
		List<Integer> testList = new LinkedList<Integer>();
		Random rand = new Random();
		for(int i=0;i<10;i++){
			testList.add(rand.nextInt(10));
		}
		log.info("create a list of integers");
		StringBuilder sb = new StringBuilder();
		for(int i: testList){
			sb.append(i).append(", ");
		}
		log.info(sb.toString());
		
		System.out.println();
		
		int i = Collections.binarySearch(testList, 5);
		log.info("WE found 5 at index: {}", i);
	}

}
