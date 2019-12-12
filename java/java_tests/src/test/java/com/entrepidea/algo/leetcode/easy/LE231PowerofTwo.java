package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/power-of-two/
 * @Desc: Given an integer, write a function to determine if it is a power of two.

 * Created by jonat on 11/6/2019.
 */
public class LE231PowerofTwo {

    private boolean foo(int n){
        if(n==1 || n ==2 ){
            return true;
        }
        int prod=1;
        int count=2;
        while(prod<n){
            prod = (int)Math.pow(2, count++);
            if(prod==n){
                return true;
            }
        }

        return false;
    }

    @Test
    public void test(){
        Assert.assertTrue(foo(16));
        Assert.assertTrue(foo(64));
        Assert.assertFalse(foo(218));
    }
}
