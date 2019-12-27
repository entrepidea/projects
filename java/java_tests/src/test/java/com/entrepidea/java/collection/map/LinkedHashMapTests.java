package com.entrepidea.java.collection.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Desc:
 * the entries of a linkedlistMap is a double-linked list. This data structure maintains a insertion-order.
 * This is different from regular HashMap, which doesn't maintain any order; or TreeMap, which maintain an ascent order of the keys.
 *
 * @Interview:
 * LinkedHashMap question was asked in a Wells Fargo on-site interview in Nov, 2019, even though not too hard.
 *
 *
 * */
public class LinkedHashMapTests {

    @Test
    public void test(){
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(45,"John");
        map.put(40, "Cathy");
        map.put(5,"Aaron");
        map.put(3, "Nathan");

        map.forEach((k,v)-> System.out.println(k+":"+v));

        System.out.println();

        Map<Integer, String> map2 = new HashMap<>();
        map2.put(45,"John");
        map2.put(40, "Cathy");
        map2.put(5,"Aaron");
        map2.put(3, "Nathan");

        map2.forEach((k,v)-> System.out.println(k+":"+v));

        System.out.println();

        TreeMap<Integer, String> map3 = new TreeMap<>();
        map3.put(45,"John");
        map3.put(40, "Cathy");
        map3.put(5,"Aaron");
        map3.put(3, "Nathan");

        map3.forEach((k,v)-> System.out.println(k+":"+v));
    }
}
