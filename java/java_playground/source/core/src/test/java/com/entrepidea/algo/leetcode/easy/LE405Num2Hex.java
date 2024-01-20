package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: convert number to hex
 * @Source: https://leetcode.com/problems/convert-a-number-to-hexadecimal/
 * @Date: 05/15/20
 *
 * @Note: TODO, code below is wrong. redo it.
 * */
public class LE405Num2Hex {

    private char conv(int x){
        Assert.assertTrue(x>=10 && x <=15);
        return (char)('a' + (x-10));
    }

    private String num2Hex(int n){
        int base  = 16;
        String ret = n/base == 0? String.format("%c", conv(n%base)):String.format("%d%c", n/base, conv(n%base));
        return ret;
    }

    @Test
    public void test(){
        Assert.assertTrue("f".equals(num2Hex(15)));
        Assert.assertTrue("18c".equals(num2Hex(300)));
    }
}
