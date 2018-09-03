package com.entrepidea.java.features.v1_8;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamTests {
    //Morgan Stanley phone interview, 05/14/18
    //TODO 4. Heard of Stream? Tell me some about this API.
    //TODO 5. Explain terminal vs intermediate in Java stream


    //map
    @Test
    public void testStreamMap(){
        int[] expected = new int[]{0,1,4,9,16,25};
        Assert.assertArrayEquals(expected, IntStream.rangeClosed(0,5).map(i->i*i).toArray());
    }


    //filter
    @Test
    public void testStreamFilter(){
        int[] expected = new int[]{0,2,4,6,8};
        Assert.assertArrayEquals(expected,IntStream.range(0,10).filter(i->i%2==0).toArray());
    }

    //this demo how to convert int array to List of Integer
    @Test
    public void testIntArray2IntegerList(){
        int[] arr = ThreadLocalRandom.current().ints(0,100).limit(10).toArray();
        List<Integer> l = Arrays.stream(arr).boxed().collect(Collectors.toList());
        l.forEach(System.out::println);
    }


}
