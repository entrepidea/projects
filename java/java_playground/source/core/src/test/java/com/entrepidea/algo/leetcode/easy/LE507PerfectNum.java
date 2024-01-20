package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * A perfect number is a positive integer that is equal to the sum of its positive divisors, excluding the number itself. A divisor of an integer x is an integer that can divide x evenly.
 * @Source: https://leetcode.com/problems/perfect-number/
 * @Date: 11/01/20
 * */
import java.util.stream.IntStream;

public class LE507PerfectNum {
    private boolean isPerfect(int num){
        int sum = 0;
        for(int i=num-1; i>=1; i--){
            if(num%i==0){
                sum += i;
            }
        }
        return sum == num;
    }
    @Test
    public void test(){
        Assert.assertTrue(isPerfect(6));
        Assert.assertTrue(isPerfect(28));
    }

    @Test
    public void test2(){
        IntStream.range(0,10000).filter(this::isPerfect).forEach(System.out::println);
    }
}
