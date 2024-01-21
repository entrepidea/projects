package com.entrepidea.evolution.v21;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashSet;
import org.junit.Assert;

/*
* Sequenced collections offer a universal way for all collection APIs to access elements in both forward/backward direction.
* https://openjdk.org/jeps/431
*
* 01/20/2014
*
* last updated:
*
* */
public class SequencedCollectionTests {
    @Test
    public void testLinkedHashSet(){
        LinkedHashSet<String> s = new LinkedHashSet<>();
        s.add("apple");
        s.add("orange");
        s.add("banana");
        s.add("pineapple");
        s.add("strawberry");
        for(String fruit: s){
            System.out.println(fruit);
        }

        Assert.assertEquals(s.getFirst(),"apple");
        Assert.assertEquals(s.getLast(),"strawberry");

        System.out.println("\nNow reverse the order:\n");

        Iterator<String> iter = s.reversed().stream().iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }
}
