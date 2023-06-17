package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.
 * @Source: https://leetcode.com/problems/add-digits/
 * @Date: 12/15/19
 * */
public class LE258AddDigit {

    private  int add(int num){
        if(num/10==0){
            return num;
        }
        int sum = 0;
        while(num!=0){
            sum += num%10;
            num /= 10;
        }
        return  add(sum);
    }

    @Test
    public void test1(){
        int num = 38;
        int sum=0;
        while(num!=0){
            System.out.println(num%10);
            sum += num%10;
            num /=10;
        }
        System.out.println(sum);
    }

    @Test
    public void test(){
        int num = 38;
        Assert.assertEquals(2, add(38));
    }
}
