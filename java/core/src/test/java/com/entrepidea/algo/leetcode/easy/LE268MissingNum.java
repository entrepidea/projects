package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class LE268MissingNum {
    private int missingNum(int[] arr){
        int len = arr.length;
        int sum1 = IntStream.rangeClosed(0,len).sum();
        int sum2 = 0;
        for(int num: arr){
            sum2 += num;
        }
        return sum1 - sum2;
    }

    @Test
    public void test(){
        Assert.assertEquals(8, missingNum(new int[]{9,6,4,2,3,5,7,0,1}));
        Assert.assertEquals(2, missingNum(new int[]{3,0,1}));
    }
}
