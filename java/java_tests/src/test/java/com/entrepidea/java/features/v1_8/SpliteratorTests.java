package com.entrepidea.java.features.v1_8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class SpliteratorTests {

    //https://www.codingeek.com/java/java8/spliterators-in-java8/
    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Mango");
        list.add("Banana");
        list.add("Pear");
        list.add("Grapes");

        Stream<String> stream = list.stream();
        Spliterator<String> spliterator1 = stream.spliterator();
        Spliterator<String> spliterator2 = spliterator1.trySplit();
        if(spliterator2!=null){
            System.out.println("Spliterator2 test:");
            spliterator2.forEachRemaining((s)->{System.out.println(s);});
        }
        if(spliterator1!=null){
            System.out.println("Spliterator1 test:");
            spliterator1.tryAdvance((s)->System.out.println(s));
        }

    }
}
