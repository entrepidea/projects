package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 *
 * Given an integer, return its base 7 string representation.
 * @Source: https://leetcode.com/problems/base-7/
 *
 * */
public class LE504Base7 {

    private String base7(int num, int base){
        boolean nagtive = false;
        StringBuilder sb = new StringBuilder();
        int temp = num;
        if(num<0){
            nagtive = true;
            temp = -num;
        }

        int remainder = 0;
        while(temp/base!=0){
            remainder = temp%base;
            sb.insert(0,remainder);
            temp = temp/base;
        }
        remainder = temp%base;
        sb.insert(0,remainder);

        if(nagtive){
            sb.insert(0,'-');
        }
        return sb.toString();

    }
    @Test
    public void test(){
        Assert.assertEquals("202", base7(100,7));
        Assert.assertEquals("-10", base7(-7,7));
    }
}
