package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: to determine if a given number can be perfectly squre-rooted
 * @Source: https://leetcode.com/problems/valid-perfect-square/discuss/577275/Simple-c%2B%2B-solution-100-fast-using-binary-search
 * @Date: 04/15/20
 * */
public class LE367PerfectSqure {
    private boolean isPerfectSqure(int num){
        if(num==0 || num==1){
            return true;
        }
        int i = 1;
        int j = num;
        int count = 0;
        while(i<=j){
            int mid = i+(j-i)/2;
            if(mid == num/mid){
                if(num%mid==0){
                    count = 1;
                }
                break;
            }
            else if(mid<num/mid){
                i = mid+1;
            }
            else{
                j = mid-1;
            }
        }
        return count==1;
    }

    @Test
    public void test(){
        Assert.assertTrue(isPerfectSqure(16));
        Assert.assertFalse(isPerfectSqure(19));
    }
}
