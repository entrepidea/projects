package com.entrepidea.algo.pattern.sliding_window;

import org.junit.Assert;
import org.junit.Test;
/**
 * Given an array of integers and a number k, find maximum sum of a subarray of size k.
 * https://www.geeksforgeeks.org/find-maximum-minimum-sum-subarray-size-k/
 * */
public class MaxSubArrayofK {

    //do a tailored bubble sorting, pick up the largest K
    public int sort(int[] arr, int k){
        for(int i=1;i<k+1;i++){
            boolean isSorted = true;
            int count=0;
            for (int j=0;j<arr.length-1-count;j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    isSorted = false;
                }
            }
            if(isSorted){
                break;
            }
            count++;
        }
        int rst=0;
        for(int i=0;i<k;i++){
            rst += arr[arr.length-1-i];
        }
        return rst;
    }
    @Test
    public void test(){
        int[] arr = {100, 200, 300, 400};
        Assert.assertEquals(700, sort(arr,2));
        arr = new int[]{1, 4, 2, 10, 23, 3, 1, 0, 20};
        Assert.assertEquals(57, sort(arr,4));
    }

}
