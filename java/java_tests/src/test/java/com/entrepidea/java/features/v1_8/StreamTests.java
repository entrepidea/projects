package com.entrepidea.java.features.v1_8;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.math.IntMath.factorial;

/**
 * Description: stream is a sequence of elements that operations can be applied on. Operations include:
 * map: transform an array; filter: set a filter; match, find matches; reduce; count, etc.
 * Java's collection APIs are retrofitted to include Stream operations since 1.8
 * Java Stream are either intermediate, in which case once a stream is worked on, another stream is returned for additional stream operations;
 * or terminal, in which case the stream operation produces a final result.
 *
 * References:
 * https://github.com/winterbe/java8-tutorial#streams
 *
 * */
public class StreamTests {
    /*Morgan Stanley phone interview, 05/14/18

    //4. Heard of Stream? Tell me some about this API.
    answer:
        Stream is a sequence of elements that operation can be performed on. Stream can be intermediate, which return another stream; or
        terminal, which returns a result.
        Most (or all?) collections have been retrofitted to include Stream support. e.g Collections.stream().
        Stream can be normal or parallelStream
        operations on stream can be filter, map, match, reduce, just to name a few.

    //5. Explain terminal vs intermediate in Java stream
    answer: if a Java Stream API is terminal, it produces a result; if it's intermediate, it produces another stream for another Java stream operation.

    */

    //map, transform an array
    @Test
    public void testStreamMap(){
        int[] expected = new int[]{0,1,4,9,16,25};
        Assert.assertArrayEquals(expected, IntStream.rangeClosed(0,5).map(i->i*i).toArray());
    }

    @Test
    public void testCreateAnIntStream(){
        IntStream.range(0,10).forEach(System.out::println);
    }

    @Test
    public void testFilterOnStream(){
        IntStream.range(0,10).filter((s)->s%2==0).forEach(System.out::println);
    }

    @Test
    public void testStreamMapAndFilter(){
        IntStream.rangeClosed(0,4).map(i->factorial(i)).filter(i->i%2==0).forEach(System.out::println);
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

    //stream and Parallel stream
    @Test
    public void testParallelStreamPerformance(){
        final int max = 10000000;
        List<String> values = new ArrayList<>(max);
        for (int i=0;i<max;i++){
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        long t0 = System.nanoTime();
        long count = values.stream().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        System.out.format("it's taken %d mil seconds to complete.\n", TimeUnit.NANOSECONDS.toMillis(t1-t0));

        t0 = System.nanoTime();
        count = values.parallelStream().count();
        t1 = System.nanoTime();
        System.out.println(count);
        System.out.format("it's taken %d mil seconds to complete", TimeUnit.NANOSECONDS.toMillis(t1-t0));
    }


}
