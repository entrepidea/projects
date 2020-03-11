package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: detemine if a number is an ugly number (positive numbers whose prime factors only include 2, 3, 5.)
 * @Source: https://leetcode.com/problems/ugly-number/
 * @Date: 12/16/19
 * */
public class LE263UglyNumber {

    private boolean isUgly(int num){
        int remain=0;
        while(num%2==0 || num%3==0 || num%5==0){
            if(num%2==0){
                num = num/2;
                remain=num%2;
            }
            if(num%3==0){
                num = num/3;
                remain=num%3;
            }
            if(num%5==0){
                num = num/5;
                remain=num%5;
            }
        }
        if(num==1 && remain==0){
            return true;
        }
        return false;
    }

    @Test
    public void test1(){
        Assert.assertEquals(1,2/2);
        Assert.assertTrue(6%2==0);
    }
    @Test
    public void test(){
        Assert.assertTrue(isUgly(6));
        Assert.assertTrue(isUgly(8));
        Assert.assertTrue(isUgly(9));
        Assert.assertTrue(isUgly(30));
        Assert.assertTrue(isUgly(1000));
        Assert.assertFalse(isUgly(1001));
        Assert.assertFalse(isUgly(49));
        Assert.assertFalse(isUgly(35));
        Assert.assertFalse(isUgly(210));
    }
}
