package com.entrepidea.algo.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LE349TwoArrayIntersec {

    //use Java 8's steam APIs, clean and clear code
    private int[] intersect(int[] input1, int[] input2){
        //remove dups in input1 and convert it to a set
        Set<Integer> s = Arrays.stream(input1).distinct().boxed().collect(Collectors.toSet());
        return Arrays.stream(input2).distinct().filter(i->s.contains(i)).toArray();
    }

    @Test
    public void test(){
        int[] rst = intersect(new int[]{1,2,2,1}, new int[]{2,2});
        Arrays.stream(rst).forEach(System.out::println);

        rst = intersect(new int[]{4,9,5}, new int[]{9,4,9,8,4});
        Arrays.stream(rst).forEach(System.out::println);
    }
}
