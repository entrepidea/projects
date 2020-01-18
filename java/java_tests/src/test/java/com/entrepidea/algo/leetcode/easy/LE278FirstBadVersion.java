package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc:
 *
 * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.
 *
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
 *
 * You are given an API bool isBadVersion(version) which will return whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.
 *
 * @Source: https://leetcode.com/problems/first-bad-version/
 *
 * @Date: 01/12/2020
 *
 * */
public class LE278FirstBadVersion {
    int[] arr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

    private boolean isBadVersion(int n){
        return n<15?false:true; //pretend 6 is the first bad version
    }

    private int firstBadVersion(int[] arr, int low, int high){
        if(high==low+1){
            return arr[high];
        }
        int mid = low + (high-low)/2;
        if(isBadVersion(arr[mid])){
            high = mid;
            //low = 0;
        }
        else{
            low = mid;
            //high = arr.length-1;
        }
        return firstBadVersion(arr, low, high);

    }

    @Test
    public void test(){
        int low = 0;
        int high = arr.length-1;
        int n = firstBadVersion(arr, low, high);
        Assert.assertEquals(15, n);
    }
}
