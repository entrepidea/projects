package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 *  @Desc: Leetcode - Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
    @Source: https://leetcode.com/problems/majority-element/

    Created by jonat on 10/30/2019.

 */
public class LE169MajorityElement {

    private Integer foo(int[] input){

        int threshold = (input.length/2)+1;
        //Assert.assertEquals(4,threshold);
        Arrays.sort(input);

        int i =0;
        while(i<input.length){
            int num  = input[i];
            int j=i+1;
            int count=1;
            while(j<input.length && input[j]==num){
                count++;
                if(count==threshold){
                    return num;
                }
                j++;
            }
            if(j==input.length){
                return null;
            }
            i = j;
        }

        return null;
    }

    @Test
    public void testFoo(){
        int[] input = {2,2,1,1,1,2,2};
        Assert.assertEquals((int)foo(input),2);
        input = new int[]{3,2,3};
        Assert.assertEquals((int)foo(input),3);
        input = new int[]{1, 2, 3, 4, 5, 6, 2};
        Assert.assertEquals(foo(input),null);
        input = new int[]{1, 4, 4, 4, 5, 4, 2};
        Assert.assertEquals((int)foo(input),4);

    }

    private int foo2(Integer[] input){
        final Map<Integer, Integer> map = new HashMap<>();
        int threshold = input.length/2+1;
        Stream.of(input).forEach(x -> {
            map.putIfAbsent(x,0);
            map.put(x,map.get(x)+1);
        });
        final int[] num = new int[1];
        //map.forEach((k,v)->System.out.println(k+":"+v));
        map.forEach((k,v) -> {if (v>=threshold){
            num[0] = k;
        }});
        return num[0];
    }

    @Test
    public  void testFoo2(){
        Assert.assertEquals(foo2(new Integer[]{2,2,1,1,1,2,2}),2);
        Integer[] input = new Integer[]{3,2,3};
        Assert.assertEquals(foo2(input),3);
        //input = new Integer[]{1, 2, 3, 4, 5, 6, 2};
        //Assert.assertEquals(foo2(input),null);
        input = new Integer[]{1, 4, 4, 4, 5, 4, 2};
        Assert.assertEquals(foo2(input),4);

    }



}
