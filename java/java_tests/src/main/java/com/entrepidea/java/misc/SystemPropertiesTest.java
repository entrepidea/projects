package com.entrepidea.java.misc;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class SystemPropertiesTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties pros = System.getProperties();
		Set<Entry<Object,Object>> set = pros.entrySet();
		Iterator<Entry<Object,Object>> iter = set.iterator();
		while(iter!=null&&iter.hasNext()){
			Entry<Object, Object> ele = iter.next();
			System.out.println(ele.getKey()+":="+ele.getValue());
		}
		
	}

}
