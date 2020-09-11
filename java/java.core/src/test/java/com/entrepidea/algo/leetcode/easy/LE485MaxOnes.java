package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: Given a binary array, find the maximum number of consecutive 1s in this array.
 * @Source: https://leetcode.com/problems/max-consecutive-ones/
 * @Date: 08/28/20
 * */
public class LE485MaxOnes {

    private int maxNumOfOnes(int[] arr){
        int max = 0;
        int idx =0;
        int count = 0;
        while(idx<arr.length){
            while(idx<arr.length && arr[idx]==1){
                count++;
                idx++;
            }
            if(count>max){
                max=count;
            }
            count = 0;
            idx++;
        }
        return max;
    }
    @Test
    public void test(){
        Assert.assertEquals(8, maxNumOfOnes(new int[]{1,1,1,1,0,0,1,1,1,0,0,0,1,1,1,1,1,1,1,1}));
    }
}
