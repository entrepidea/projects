package com.entrepidea.java.features.v1_8;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.math.IntMath.factorial;
import static java.util.stream.Collectors.*;

/**
 * @Desc: stream is a sequence of elements that functional-style operations can be applied on.
 * Operations include:
 * map: transform an array; filter: set a filter; match, find matches; reduce; count, etc.
 * Java's collection APIs are retrofitted to include Stream operations since 1.8
 * Java Stream are either intermediate, in which case once a stream is worked on, another stream is returned for additional stream operations;
 * or terminal, in which case the stream operation produces a final result.
 *
 * References:
 * https://github.com/winterbe/java8-tutorial#streams
 * Also:
 * official package document https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
 * and Stream document: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
 *
 * */
public class StreamTests {
    /*

    Morgan Stanley phone interview, 05/14/18

    Heard of Stream? Tell me some about this API.
    answer:
        Stream is a sequence of elements that operation can be performed on. Stream can be intermediate, which return another stream; or
        terminal, which returns a result.
        Most (or all?) collections have been retrofitted to include Stream support. e.g Collections.stream().
        Stream can be normal or parallelStream
        operations on stream can be filter, map, match, reduce, just to name a few.

    Explain terminal vs intermediate in Java stream
    answer: if a Java Stream API is terminal, it produces a result; if it's intermediate, it produces another stream for another Java stream operation.

    */

    //map, transform an array
    @Test
    public void testStreamMap(){
        int[] expected = new int[]{0,1,4,9,16,25};
        Assert.assertArrayEquals(expected, IntStream.rangeClosed(0,5).map(i->i*i).toArray());
    }

    //flatmap: a combination of mapping and a flatting operations
    //
    @Test
    public void testFlatMap(){
        List<Integer> evenInts = IntStream.rangeClosed(0,10).filter(x->x%2==0).boxed().collect(Collectors.toList());
        List<Integer> oddInts = IntStream.rangeClosed(0,10).filter(x->x%2!=0).boxed().collect(Collectors.toList());
        List<Integer> primeInts = IntStream.rangeClosed(1,10).filter(x -> {
            for(int i=2;i<=x/2;i++){
                if(x%i==0){
                    return false;
                }
            }
            return true;
        }).boxed().collect(Collectors.toList());

        //before flatMap: 3 lists of integers
        Arrays.asList(evenInts,oddInts,primeInts).forEach(System.out::println);

        //after flatMap:
        Arrays.asList(evenInts,oddInts,primeInts).stream().flatMap(list -> list.stream()).collect(Collectors.toList()).forEach(System.out::println);
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


    /**
     * @Desc: Wells Fargo onsite interview question, in Nov/2019.
     * This is to convert a list of Persons into a map<String, List<Person> > with keys groupped by the companies they employed with.
     * @Note: Stream#Collectors' toMap has groupBy function.
     * @Date: 12/09/19
     * */

    //Collectors.groupingBy examples.
    static class Person {
        public String getName() {
            return name;
        }

        public String getEmployer() {
            return employer;
        }

        private String name;
        private String employer;
        public Person(String n, String e){name = n; employer = e;}

        @Override
        public String toString(){
            return "{name="+name+", employer="+employer+"}";
        }
    }


    private List<Person> constructList(){
        List<Person> l = new ArrayList<>();
        l.add(new Person("John", "Barclays"));
        l.add(new Person("Tom", "Barclays"));
        l.add(new Person("Cathy", "Exxon"));
        l.add(new Person("Jane", "Exxon"));

        return l;
    }

    @Test
    public void testCollectorsGroupingBy(){
        List<Person> l = constructList();


        Map<String, List<Person>> rst = l.stream().collect(groupingBy(Person::getEmployer));
        rst.forEach((k,v) -> {
            System.out.println(k+":"+v);
        });

        //Now we wanted to customize the value to show only names.
        l.stream().collect(groupingBy(Person::getEmployer, mapping(s->s.getName(), toList()))).forEach((k,v)->{System.out.println(k+":"+v);});
    }

}
