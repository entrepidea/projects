package com.entrepidea.java.collection.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class SetTests {


    @Test
    public void testTreeSetSorting(){
        //you can pass a comparator via TreeSet's constructor for customized sorting - in case it's required that
        // the sorting is done at the construction of an Set object.
        //in this example, we are going to sort a set of integers in string order as opposed to its natural order.
        Set<Integer> s = new TreeSet<>();
        s.add(1);
        s.add(4);
        s.add(7);
        s.add(77);
        s.add(0);
        s.add(10);
        s.add(2);
        s.add(20);

        s.stream().forEach(System.out::println); //this is a natural sorting.

        //sort by alphabetic order.
        Comparator<Integer> cmp = (x,y) -> x.toString().equals(y.toString())?0:1;
        s = new TreeSet<>(cmp);
        s.add(1);
        s.add(4);
        s.add(7);
        s.add(77);
        s.add(0);
        s.add(10);
        s.add(2);
        s.add(20);

        System.out.println("Sort by alphabetic order.");
        for(Integer i: s){
            System.out.println(i); //this is a natural sorting.
        }

        Assert.assertEquals(((TreeSet)s).last(), 20);

        //the TreeSet#comparator return a reference of the comparator it uses.
        Assert.assertTrue(((TreeSet) s).comparator() == cmp);

    }
}
