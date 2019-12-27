package com.entrepidea.java.collection.map;

import org.junit.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Desc:
 * TreeMap use a red-black tree as its internal placeholder structure, to maintain its order of the keys.
 * The keys's order can be defined using a comparator that is passed via one of TreeMap's constructor.
 *
 * @Interview:
 * The comparator passed by via a constructor was asked in a NYSE interview in Sept, 2019
 * */
public class TreeMapTests {

    @Test
    public void test(){
        TreeMap<Integer,String> map = new TreeMap<>();
        map.put(45, "John");
        map.put(40, "Cathy");
        map.put(5, "Aaron");
        map.put(3, "Nathan");

        map.forEach((k,v)->System.out.println(k+":"+v));

        Map.Entry<Integer, String> flrEntry = map.floorEntry(45);

        System.out.println(flrEntry.getKey()+":"+ flrEntry.getValue());

        TreeMap<Integer,String> map2 = new TreeMap<>(Comparator.comparingInt(o -> -o));
        map2.put(45, "John");
        map2.put(40, "Cathy");
        map2.put(5, "Aaron");
        map2.put(3, "Nathan");
        System.out.println();
        map2.forEach((k,v)->System.out.println(k+":"+v));

    }
}
