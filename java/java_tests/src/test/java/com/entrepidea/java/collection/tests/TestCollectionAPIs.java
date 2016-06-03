package com.entrepidea.java.collection.tests;

import org.junit.Test;

import junit.framework.Assert;

import com.entrepidea.java.collection.HashMapVSTable;

public class TestCollectionAPIs {

	//test HashMapVSTable
	@Test
	public void testNullable(){
		HashMapVSTable hmt = new HashMapVSTable();
		Object o = hmt.nullKey();
		Assert.assertNull(o);
		
	}
}
