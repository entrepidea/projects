package com.entrepidea.java.collection;

import java.util.LinkedList;
import java.util.Iterator;

public class MockHashMap {
	// static/instance variables,inner class
	private LinkedList<Pair>[] bucklets;
	private int bucketNum;

	private static class Pair {
		public String key;
		public Object val;

		public Pair(String k, Object v) {
			key = k;
			val = v;
		}
	}

	// constructors
	public MockHashMap(int num) {
		bucketNum = num;
		bucklets = new LinkedList[bucketNum];// array can't be generic, check EJ
												// for details
	}

	public MockHashMap() {
		this(6);
	}

	// inner methods

	// public APIs
	// public boolean equals(Object other){}
	// public int hashCode(){}

	public Object get(String key) {
		for (LinkedList l : bucklets) {
			for (Iterator<Pair> iter = l.iterator(); iter.hasNext();) {
				Pair p = iter.next();
				if (key.equals(p.key)) {
					return p.val;
				}
			}
		}
		return null;
	}

	public void put(String key, Object val) {
		if (get(key) != null) {
			System.err.println("The value already exists!!!Nothing is done.");
			return;
		}
		int hc = val.hashCode();
		int bukIndex = hc % bucketNum;
		LinkedList<Pair> link = bucklets[bukIndex];
		if (link == null) {
			link = new LinkedList<Pair>();
			link.addFirst(new Pair(key, val));
		} else {
			link.add(new Pair(key, val));
		}
	}
}