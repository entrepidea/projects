package com.entrepidea.java.collection;

import java.util.concurrent.ConcurrentSkipListSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentSkipListSetTest {
	
	private static Logger log = LoggerFactory.getLogger(ConcurrentSkipListSetTest.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConcurrentSkipListSet<String> s = new ConcurrentSkipListSet<String>();
		s.add("Hello");
		s.add("World");
		s.add("C");
		s.add("B");
		s.add("A");
		log.debug(s.toString());
	}

}
