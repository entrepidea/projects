package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Desc: find out the sum of two integers, no +/- operator is allowed.
 * @Date: 04/16/20
 * */
public class LE371SumOfTwoIntegers {
    //my solution
    private int sum(int a, int b){
        return (int)Math.log10(Math.pow(10,a)*Math.pow(10,b));
    }

    @Test
    public void test(){
        Assert.assertEquals(sum(1,2),3);
        Assert.assertEquals(sum(-1,2),1);
    }

    //another solution from: https://leetcode.com/problems/sum-of-two-integers/discuss/578260/Java-code
    private int sum2(int a, int b){
        int carry;
        while(b!=0){
            carry = a&b; //generate carry
            a = a^b; //do the addition
            b = carry<<1;
        }
        return a;
    }

    @Test
    public void test2(){
        Assert.assertEquals(sum(1,2),3);
        Assert.assertEquals(sum(-1,2),1);
    }
}
