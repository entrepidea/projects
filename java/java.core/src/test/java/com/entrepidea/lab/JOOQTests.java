package com.entrepidea.lab;

import static org.jooq.lambda.Seq.*;
import static org.jooq.lambda.tuple.Tuple.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.jooq.lambda.*;
import org.junit.Test;


/**
 * TODO complete the walk through of the article below
 * https://dzone.com/articles/common-sql-clauses-and-their-equivalents-in-java-8-1
 *
 * 01/13/21
 *
 * */

public class JOOQTests {
    @Test
    public void test(){
        Stream.of(
                tuple(1, 1),
                tuple(2, 2)
        ).forEach(System.out::println);
    }

    @Test
    public void test2(){
        Stream<Integer> s1 = Stream.of(1, 2);
        Supplier<Stream<String>> s2 = ()->Stream.of("A", "B");

        s1.flatMap(v1 -> s2.get()
                .map(v2 -> tuple(v1, v2)))
                .forEach(System.out::println);
    }

    //alternative to test2
    @Test
    public void test3(){
        List<Integer> s1 = Arrays.asList(1, 2);
        List<String> s2 = Arrays.asList("A", "B");

        s1.stream()
                .flatMap(v1 -> s2.stream()
                        .map(v2 -> tuple(v1, v2)))
                .forEach(System.out::println);
    }

    @Test
    public void testCrossJoin(){
        Seq<Integer> s1 = Seq.of(1, 2);
        Seq<String> s2 = Seq.of("A", "B");
        Seq<String> s3 = Seq.of("X", "Y");

        s1.crossJoin(s2)
                .crossJoin(s3)
                .map(t -> tuple(t.v1.v1, t.v1.v2, t.v2))
                .forEach(System.out::println);
    }

    @Test
    public void testInnerJoin(){
        Seq<Integer> s1 = Seq.of(1, 2);
        Seq<Integer> s2 = Seq.of(1, 3);

        s1.innerJoin(s2, Objects::equals)
                .forEach(System.out::println);
    }
}
