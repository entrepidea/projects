package com.entrepidea.core.features.v8;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
 * @References:
 * https://github.com/winterbe/java8-tutorial#streams
 * official package document https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
 * and Stream document: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
 *
 * @Date: unknown, 02/05/20
 *
 * */
public class StreamTests {
    /**
     *  Morgan Stanley phone interview, 05/14/18
     *   Heard of Stream? Tell me some about this API.
     *
     */
     //   Stream is a sequence of elements that operation can be performed on. Stream can be intermediate, which return another stream; or terminal, which returns a result.
     //   Most (or all?) collections have been retrofitted to include Stream support. e.g Collections.stream().
     //   Stream can be normal or parallelStream
     //   operations on stream can be filter, map, match, reduce, just to name a few.


    //TODO: supplier? Consumer? Collectors?

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
        IntStream.rangeClosed(0,10).forEach(System.out::println);
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
     * This is to convert a list of Persons into a map<String, List<Person> > with keys grouped by the companies they employed with.
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

        final private String name;
        final private String employer;
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

    @Test
    public void testIntStreamToMap(){
        int[] input = new int[]{1,2,3,4,2,5};
        //remove dups in a int stream and convert it to map
        //https://stackoverflow.com/questions/38318181/convert-int-stream-to-map
        Map<Integer, Integer> map = Arrays.stream(input).distinct().boxed().collect(Collectors.toMap(Function.identity(), i->i*i));
        map.forEach((k,v)->System.out.println(k+":"+v));

    }

    //LinkedHashMap sorts the keys basing on their insertion order. Below shows how to use Stream#toMap API for this purpose
    //https://stackoverflow.com/questions/29090277/how-do-i-keep-the-iteration-order-of-a-list-when-using-collections-tomap-on-a
    @Test
    public void testToLinkedHashMap(){
        Map<String,String> m = Stream.of(
                new String[][]{
                        {"C","0"},
                        {"B","1"},
                        {"A","2"},
                }).collect(Collectors.toMap(data -> data[0], data -> data[1], (u,v) -> {throw new IllegalStateException(String.format("Duplicated key %s",u));}, LinkedHashMap::new));

        m.forEach((u,v) -> System.out.println(String.format("%s:%s",u,v)));
    }

    //find out strings started with 'a' and single out the one with longest length.
    //source: https://zhuanlan.zhihu.com/p/425608079
    //阿里二面：java8的stream api是迭代一次还是迭代多次
    @Test
    public void testStringFiltering(){
        String[] str = new String[] {"abb","abcd","fegc", "efe", "adfes"};
        Stream<String> stringStream = Stream.of(str);
        int rlt = stringStream.filter(s->s.startsWith("a")).mapToInt(String::length).max().orElse(-1);
        Assert.assertEquals(rlt,5);
    }

}
