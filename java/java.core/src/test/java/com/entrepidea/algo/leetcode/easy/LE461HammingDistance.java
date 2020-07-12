package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: find out the hamming distance between two integers.
 * @Source: https://leetcode.com/problems/hamming-distance/
 * @Date: 07/12/20
 * */
public class LE461HammingDistance {

    private int[] toBinary(int n){
        int[] ret = new int[3];
        int temp = n;
        int k = ret.length-1;
        do{
            int bit = temp%2;
            temp = temp/2;
            ret[k--] = bit;
        }while(temp!=0);
        return ret;
    }

    @Test
    public void test(){
        Assert.assertArrayEquals(new int[]{1,0,0},toBinary(4));
        Assert.assertArrayEquals(new int[]{0,1,0},toBinary(2));
        int[] ret1 = toBinary(4);
        int[] ret2 = toBinary(2);
        int count = 0;
        for(int i=ret1.length-1;i>=0;i--){
            if(ret1[i]!=ret2[i]){
                count++;
            }
        }
        Assert.assertEquals(2, count);
    }
}
