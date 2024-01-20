package com.entrepidea.algo.interview;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Desc: this is a Goldman interview question.
 * Give an array of integers and a target number, find out the length of the shortest sub array the sum of which is no less than the target number.
 *
 * Created by jonat on 10/25/2019.
 */
public class MinimumSubArray {

    private int subArr(int[] arr, int target){
        //my solution
        Arrays.sort(arr);
        int count = 0;
        int idx = arr.length-1;
        while(idx>=0){
            target -= arr[idx];
            if(target<=0){
                return count+1;
            }
            count++;
            idx--;
        }
        return 0;
    }
    @Test
    public void test(){
        int[] arr = {1,2,3,4};
        Assert.assertEquals(subArr(arr, 6),2);
        Assert.assertEquals(subArr(arr, 5),2);
        Assert.assertEquals(subArr(arr, 4),1);
        Assert.assertEquals(subArr(arr, 10),4);
        Assert.assertEquals(subArr(arr, 9),3);
        Assert.assertEquals(subArr(arr, 8),3);
    }


    //TODO rework needed.
    private int subArr2(int[] arr, int target){
        int minLen = arr.length+1;
        for(int i=0;i<arr.length;i++){
            int remain = target - arr[i];
            if(remain<=0){
                minLen = 1;
                break;
            }
            int sum = 0;
            int len = 1;
            for(int j=0;j<arr.length;j++){
                if(i==j){
                    continue;
                }
                sum += arr[j];
                len++;
                if(sum>=remain){
                    if(len<minLen){
                        minLen = len;
                    }
                    //break;
                }

            }
        }
        return minLen;
    }

    @Test
    public void test2(){
        int[] arr = {1,2,3,4};
        Assert.assertEquals(subArr2(arr, 6),2);
        Assert.assertEquals(subArr2(arr, 5),2);
        Assert.assertEquals(subArr2(arr, 4),1);
        Assert.assertEquals(subArr2(arr, 10),4);
        Assert.assertEquals(subArr2(arr, 9),3);
        Assert.assertEquals(subArr2(arr, 8),3);
    }

    //the below uses two loops. The outer loop start from index 0, the inner loop include all elements to the one in the outter loop.
    //keep adding them and stop when the condition is satisfied, then record the length, find out the smallest one
    //source: https://www.geeksforgeeks.org/stream-reduce-java-examples/
    //date: 12/05/19
    private int minWindow(Integer[] arr, int target){

        if(Arrays.asList(arr).stream().reduce(0, (x,y) -> (x+y))<target){ //all added up but still the sum is less than the target,
            return -1;
        };

        int minLen = arr.length;
        for(int i=0;i<arr.length-1;i++){
            int sum=arr[i];
            int k=1;
            int j=i+1;
            while(sum<=target && j<arr.length){
                sum += arr[j++];
                k++;
            }
            if(sum>target){
                if(k<minLen){
                    minLen = k;
                }
            }
        }
        return minLen;
    }

    @Test
    public void test3(){
        Integer[] arr = {1, 4, 45, 6, 0, 19};
        int target = 51;
        Assert.assertEquals(minWindow(arr,target),3);

        arr = new Integer[]{1, 10, 5, 2, 7};
        target = 9;
        Assert.assertEquals(minWindow(arr,target),1);

        arr = new Integer[]{1, 11, 100, 1, 0, 200, 3, 2, 1, 250};
        target = 280;
        Assert.assertEquals(minWindow(arr,target),4);

        arr = new Integer[]{1, 2, 4};
        target = 8;
        Assert.assertEquals(minWindow(arr,target),-1);

    }

}
