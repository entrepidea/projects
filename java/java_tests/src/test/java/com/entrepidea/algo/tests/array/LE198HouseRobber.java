package com.entrepidea.algo.tests.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Source: https://leetcode.com/problems/house-robber/
 * @Desc:
 *
 *
 * Created by jonat on 11/4/2019.
 */
public class LE198HouseRobber {

    private int foo(int[] arr){
        int sum1 = 0, sum2 = 0;
        for(int i=0; i<arr.length;i+=2){
            sum1+=arr[i];
            if(i+1<arr.length){
                sum2+=arr[i+1];
            }
        }
        return (sum1>sum2)?sum1:sum2;
    }

    @Test
    public void test(){
        Assert.assertEquals(foo(new int[]{1,2,3,1}),4);
        Assert.assertEquals(foo(new int[]{2,7,9,3,1}),12);
    }

    private int foo2(int[] arr){
        //how java stream work on the index of an array
        Optional<Integer> sum1 = IntStream.range(0,arr.length).filter(i->i%2==0).mapToObj(i->arr[i]).reduce((a,b)->a+b);
        Optional<Integer> sum2 = IntStream.range(0,arr.length).filter(i->i%2!=0).mapToObj(i->arr[i]).reduce((a,b)->a+b);

        return Math.max(sum1.orElse(0), sum2.orElse(0));
    }

    @Test
    public void test2(){
        Assert.assertEquals(4, foo2(new int[]{1,2,3,1}));
        Assert.assertEquals(12, foo2(new int[]{2,7,9,3,1}));
        Assert.assertEquals(2, foo2(new int[]{1,2}));
        Assert.assertEquals(1, foo2(new int[]{1}));
    }

}
