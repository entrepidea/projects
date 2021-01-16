package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibonacci sequence, such that each number is the sum of the two preceding ones, starting from 0 and 1.
 * @Source: https://leetcode.com/problems/fibonacci-number/
 * @Date: 11/01/2020
 * */

public class LE509Fibonacci {

    int fibonacci(int num){
        if(num==0 || num==1){
            return num;
        }
        else{
            return fibonacci(num-1)+fibonacci(num-2);
        }
    }
    @Test
    public void test(){
        Assert.assertEquals(2, fibonacci(3));
        Assert.assertEquals(8, fibonacci(6));

    }
}
