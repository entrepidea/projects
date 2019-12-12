package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/factorial-trailing-zeroes/
 * @Description: Given an integer n, return the number of trailing zeroes in n!.
 * @Date: 10/31/19
 */

public class LE172FactorialTrailingZeros {
    private int foo(int n){
        int fac = 1;
        for(int i=n;i>1;i--){
            fac  *= i;
        }

        int count = 0;
        while(fac%10 == 0){
            count++;
            fac /=10;
        }
        return count;
    }

    @Test
    public void testFoo(){
        Assert.assertEquals(0, foo(3));
        Assert.assertEquals(1, foo(5));
        Assert.assertEquals(1, foo(6));
        Assert.assertEquals(2, foo(10));
    }

    private int foo2(long n){
        long fac = 1;
        int count=0;
        for(long i=n;i>1;i--){
            fac *= i;
            if(fac%(Math.pow(10,(count+1)))==0){
                count++;
            }
        }
        return count;
    }

    @Test
    public void testFoo2(){
        Assert.assertEquals(0, foo2(3));
        Assert.assertEquals(1, foo2(5));
        Assert.assertEquals(1, foo2(6));
        Assert.assertEquals(2, foo2(10));
        Assert.assertEquals(3, foo2(15));
    }
}
